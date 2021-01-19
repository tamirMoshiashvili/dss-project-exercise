package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLoader {
	public static Properties load(String propertiesFileName) {
		try {
			return tryLoadingApplicationProperties(propertiesFileName);
		} catch (IOException e) {
			throw new RuntimeException("Failed loading properties", e);
		}
	}

	private static Properties tryLoadingApplicationProperties(String propertiesFileName) throws IOException {
		InputStream propertiesInputStream = getPropertiesFileInputStream(propertiesFileName);

		return loadApplicationProperties(propertiesInputStream);
	}

	private static InputStream getPropertiesFileInputStream(String propertiesFileName) throws FileNotFoundException {
		InputStream propertiesInputStream = ConfigurationLoader.class.getClassLoader().getResourceAsStream(propertiesFileName);
		validatePropertiesFileExists(propertiesFileName, propertiesInputStream);

		return propertiesInputStream;
	}

	private static void validatePropertiesFileExists(String propertiesFileName, InputStream propertiesInputStream) throws FileNotFoundException {
		if (propertiesInputStream == null) {
			throw new FileNotFoundException("Properties file not found: " + propertiesFileName);
		}
	}

	private static Properties loadApplicationProperties(InputStream propertiesInputStream) throws IOException {
		Properties properties = new Properties();
		properties.load(propertiesInputStream);
		return properties;
	}
}
