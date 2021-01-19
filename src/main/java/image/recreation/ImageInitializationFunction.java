package image.recreation;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface ImageInitializationFunction {
	BufferedImage createImage(int width, int height);
}
