package config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ConfigurationLoader {
	public static AppConfig load(String configFileName) {
		try {
			return tryLoadingConfigFile(configFileName);
		} catch (IOException e) {
			throw new RuntimeException("Failed loading properties", e);
		}
	}

	private static AppConfig tryLoadingConfigFile(String propertiesFileName) throws IOException {
		InputStream configInputStream = getConfigFileInputStream(propertiesFileName);

		return loadApplicationConfigurations(configInputStream);
	}

	private static InputStream getConfigFileInputStream(String configFileName) throws FileNotFoundException {
		InputStream configInputStream = ConfigurationLoader.class.getClassLoader().getResourceAsStream(configFileName);
		validateConfigFileExists(configFileName, configInputStream);

		return configInputStream;
	}

	private static void validateConfigFileExists(String configFileName, InputStream configInputStream) throws FileNotFoundException {
		if (configInputStream == null) {
			throw new FileNotFoundException("Config file not found: " + configFileName);
		}
	}

	private static AppConfig loadApplicationConfigurations(InputStream configInputStream) {
		Yaml config = new Yaml(createYamlConstructor());

		return config.loadAs(configInputStream, AppConfig.class);
	}

	private static Constructor createYamlConstructor() {
		Constructor constructor = new Constructor();
		constructor.setPropertyUtils(new HyphenSupportPropertyUtils());
		constructor.getPropertyUtils().setSkipMissingProperties(true);
		return constructor;
	}
}
