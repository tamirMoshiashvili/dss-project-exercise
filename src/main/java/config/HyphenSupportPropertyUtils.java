package config;

import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.util.Arrays;
import java.util.List;

public class HyphenSupportPropertyUtils extends PropertyUtils {
	@Override
	public Property getProperty(Class<?> type, String name) {
		if (name.contains("-")) {
			name = toCamelCase(name);
		}
		return super.getProperty(type, name);
	}

	private String toCamelCase(String name) {
		StringBuilder camelCaseBuilder = new StringBuilder();
		List<String> splitName = Arrays.asList(name.split("-"));
		camelCaseBuilder.append(splitName.get(0).toLowerCase());

		for (String part : splitName.subList(1, splitName.size())) {
			camelCaseBuilder.append(part.substring(0, 1).toUpperCase())
					.append(part.substring(1).toLowerCase());
		}

		return camelCaseBuilder.toString();
	}
}
