package image.loss;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImageMSE {
	public static float calculateLoss(BufferedImage target, BufferedImage prediction) {
		int size = target.getWidth() * target.getHeight();

		return getSquaredSumError(new FastRGB(target), new FastRGB(prediction)) / size;
	}

	private static float getSquaredSumError(FastRGB target, FastRGB prediction) {
		float squaredSumError = 0;

		for (int i = 0; i < target.getWidth(); i++) {
			for (int j = 0; j < target.getHeight(); j++) {
				squaredSumError += calculateRGBError(target, prediction, i, j);
			}
		}
		return squaredSumError;
	}

	private static int calculateRGBError(FastRGB target, FastRGB prediction, int i, int j) {
		Color targetColor = target.getRGB(i, j);
		Color predictionColor = prediction.getRGB(i, j);

		int aError = targetColor.getAlpha() - predictionColor.getAlpha();
		int rError = targetColor.getRed() - predictionColor.getRed();
		int gError = targetColor.getGreen() - predictionColor.getGreen();
		int bError = targetColor.getBlue() - predictionColor.getBlue();

		return aError * aError
				+ rError * rError + gError * gError + bError * bError;
	}
}
