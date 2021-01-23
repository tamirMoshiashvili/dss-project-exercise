package image.loss;

import java.awt.*;
import java.awt.image.BufferedImage;

class BufferedImageMAE {
	static float calculateLoss(BufferedImage target, BufferedImage prediction) {
		float absoluteSumError = getAbsoluteSumError(new FastRGB(target), new FastRGB(prediction));
		int size = target.getWidth() * target.getHeight();

		return absoluteSumError / size;
	}

	private static float getAbsoluteSumError(FastRGB target, FastRGB prediction) {
		float absoluteSumError = 0;

		for (int i = 0; i < target.getWidth(); i++) {
			for (int j = 0; j < target.getHeight(); j++) {
				absoluteSumError += calculateRGBError(target, prediction, i, j);
			}
		}
		return absoluteSumError;
	}

	private static int calculateRGBError(FastRGB target, FastRGB prediction, int i, int j) {
		Color targetColor = target.getRGB(i, j);
		Color predictionColor = prediction.getRGB(i, j);

		int aError = Math.abs(targetColor.getAlpha() - predictionColor.getAlpha());
		int rError = Math.abs(targetColor.getRed() - predictionColor.getRed());
		int gError = Math.abs(targetColor.getGreen() - predictionColor.getGreen());
		int bError = Math.abs(targetColor.getBlue() - predictionColor.getBlue());

		return aError + rError + gError + bError;
	}
}
