package examples.shape.image.recreation.utils;

import image.recreation.shape.ShapeSpecification;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShapeSpecificationGenerator {
	private static final Random random = new Random();

	public static List<ShapeSpecification> createRandomShapeSpecifications(int width, int height, int initialSize) {
		return Stream.generate(() -> createRandomShapeSpecification(width, height))
				.limit(initialSize)
				.collect(Collectors.toList());
	}

	private static ShapeSpecification createRandomShapeSpecification(int width, int height) {
		Point2D.Float location = new Point2D.Float(20 + random.nextInt(width - 20),
				20 + random.nextInt(height - 20));
		int orientation = random.nextInt(360);
		float scale = 0.1f + random.nextFloat() / 2;
		Color color = new Color(random.nextInt(), true);

		return new ShapeSpecification(location, orientation, scale, color);
	}
}
