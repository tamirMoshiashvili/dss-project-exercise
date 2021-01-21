package config.image.recreation;

import lombok.Data;

@Data
public class ImageRecreationConfig {
	private String targetImagePath;
	private ShapeConfig shape;
}
