package image.loss;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.ImageUtils;

import java.awt.image.BufferedImage;

class BufferedImageLossTest {
	@Test
	void givenTwoDifferentImages_whenCalculateLoss_thenLossNotEqualsZero() {
		BufferedImage existingImage = ImageUtils.loadImage("images\\mona-lisa-1.png");
		BufferedImage newImage = new BufferedImage(existingImage.getWidth(), existingImage.getHeight(), existingImage.getType());

		Assertions.assertNotEquals(0, BufferedImageMAE.calculateLoss(existingImage, newImage));
		Assertions.assertNotEquals(0, BufferedImageMSE.calculateLoss(existingImage, newImage));
	}

	@Test
	void givenSameImage_whenCalculateLoss_thenLossEqualsZero() {
		BufferedImage image = ImageUtils.loadImage("images\\mona-lisa-1.png");

		Assertions.assertEquals(0, BufferedImageMAE.calculateLoss(image, image));
		Assertions.assertEquals(0, BufferedImageMSE.calculateLoss(image, image));
	}
}
