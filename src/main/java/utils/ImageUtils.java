package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class ImageUtils {
	public static BufferedImage loadImage(String path) {
		try {
			URI uri = Objects.requireNonNull(ImageUtils.class.getClassLoader().getResource(path)).toURI();
			return ImageIO.read(new File(uri));
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException("Failed loading image", e);
		}
	}
}
