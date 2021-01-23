package examples.shape.image.recreation.genetic;

import examples.shape.image.recreation.utils.ShapeSpecificationGenerator;
import image.recreation.ImageRecreation;
import image.recreation.shape.ShapeImageRecreation;
import image.recreation.shape.ShapeSpecification;
import image.recreation.shape.conversion.ShapeConversionFunction;
import optimization.algorithm.genetic.operator.GeneticOperator;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdditiveShapeSpecificationOperator<T extends Shape> implements GeneticOperator<ImageRecreation<ShapeSpecification>> {
	private static final Random random = new Random();
	private final int width;
	private final int height;
	private final int threshold;
	private final int delta;
	private final ShapeConversionFunction<T> shapeConversionFunction;

	public AdditiveShapeSpecificationOperator(int width, int height, int threshold, int delta,
											  ShapeConversionFunction<T> shapeConversionFunction) {
		this.width = width;
		this.height = height;
		this.threshold = threshold;
		this.delta = delta;
		this.shapeConversionFunction = shapeConversionFunction;
	}

	@Override
	public ImageRecreation<ShapeSpecification> apply(ImageRecreation<ShapeSpecification> shapeImageRecreation) {
		List<ShapeSpecification> shapeSpecifications = createNewShapeSpecifications(shapeImageRecreation);
		return new ShapeImageRecreation<>(shapeSpecifications, shapeConversionFunction);
	}

	private List<ShapeSpecification> createNewShapeSpecifications(ImageRecreation<ShapeSpecification> shapeImageRecreation) {
		List<ShapeSpecification> shapeSpecifications = new ArrayList<>(shapeImageRecreation.getItems());
		if (shapeImageRecreation.getItems().size() <= threshold - delta) {
			shapeSpecifications.addAll(ShapeSpecificationGenerator.createRandomShapeSpecifications(width, height, delta));
		} else {
			int index = random.nextInt(shapeSpecifications.size());
			ShapeSpecification shapeSpecification = shapeSpecifications.get(index);
			shapeSpecifications.remove(index);
			shapeSpecifications.add(getMutatedShapeSpecification(shapeSpecification.clone()));
		}
		return shapeSpecifications;
	}

	private ShapeSpecification getMutatedShapeSpecification(ShapeSpecification shapeSpecification) {
		int operator = Math.random() < 0.5 ? -1 : 1;

		shapeSpecification.setScale(shapeSpecification.getScale() + operator * 0.01f);
		shapeSpecification.setOrientation(shapeSpecification.getOrientation() + operator * 3);
		shapeSpecification.setColor(new Color(shapeSpecification.getColor().getRGB() + operator * 10, true));

		Point2D.Float location = shapeSpecification.getLocation();
		location.setLocation(location.getX() + operator * 3, location.getY() + operator * 3);

		return shapeSpecification;
	}
}
