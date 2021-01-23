package image.loss;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

class FastRGB {
	private static final int PIXEL_LEN = 4;
	private static final int MASK = 0xff;

	private final int width;
	private final int height;
	private final byte[] pixels;

	FastRGB(BufferedImage image) {
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	Color getRGB(int x, int y) {
		int position = PIXEL_LEN * (y * width + x);
		int abgr = ((int) pixels[position++] & MASK) << 24;
		abgr += ((int) pixels[position++] & MASK) << 16;
		abgr += ((int) pixels[position++] & MASK) << 8;
		abgr += (int) pixels[position] & MASK;

		return new Color(abgr, true);
	}
}
