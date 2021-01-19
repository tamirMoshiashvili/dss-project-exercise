package image.recreation;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface DrawingFunction<T> {
	void draw(T object, BufferedImage image);
}
