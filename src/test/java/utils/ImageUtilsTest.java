package utils;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

class ImageUtilsTest {
	@Test
	void givenImageFromClasspath_whenLoad_thenNoExceptions() {
		BufferedImage image = ImageUtils.loadImage("images\\mona-lisa-1.png");
		System.out.println(image.getWidth());
	}
}