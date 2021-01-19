package image.recreation;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageRecreation<T> {
	ImageInitializationFunction getImageInitializationFunction();

	List<T> getItems();

	DrawingFunction<T> getDrawingFunction();

	default BufferedImage recreateImage(int width, int height) {
		BufferedImage image = getImageInitializationFunction().createImage(width, height);
		getItems().forEach(item -> getDrawingFunction().draw(item, image));

		return image;
	}
}
