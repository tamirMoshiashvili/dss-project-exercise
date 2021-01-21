package config.image.recreation;

import lombok.Data;

@Data
public class ShapeConfig {
	private ShapeSpecificationConfig specifications;
	private String conversionFunction;
}
