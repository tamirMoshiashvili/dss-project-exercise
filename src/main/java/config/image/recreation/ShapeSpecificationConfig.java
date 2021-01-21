package config.image.recreation;

import lombok.Data;

@Data
public class ShapeSpecificationConfig {
	private int initialSize;
	private int delta;
	private int threshold;
}
