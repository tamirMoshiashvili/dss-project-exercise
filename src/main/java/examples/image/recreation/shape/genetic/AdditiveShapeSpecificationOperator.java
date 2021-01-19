package examples.image.recreation.shape.genetic;

import examples.image.recreation.shape.utils.ShapeSpecificationGenerator;
import image.recreation.ImageRecreation;
import image.recreation.shape.ShapeImageRecreation;
import image.recreation.shape.ShapeSpecification;
import image.recreation.shape.conversion.ShapeConversionFunction;
import optimization.genetic.operator.GeneticOperator;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdditiveShapeSpecificationOperator<T extends Shape> implements GeneticOperator<ImageRecreation<ShapeSpecification>> {
	private static Random random = new Random();
	private int width;
	private int height;
	private int delta;
	private ShapeConversionFunction<T> shapeConversionFunction;

	public AdditiveShapeSpecificationOperator(int width, int height, int delta, ShapeConversionFunction<T> shapeConversionFunction) {
		this.width = width;
		this.height = height;
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
		if (shapeImageRecreation.getItems().size() <= 100 - delta) {
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
		shapeSpecification.setOrientation(shapeSpecification.getOrientation() + operator * 5);
		shapeSpecification.setColor(new Color(shapeSpecification.getColor().getRGB() + operator * 100, true));

		Point2D.Float location = shapeSpecification.getLocation();
		location.setLocation(location.getX() + operator * 5, location.getY() + operator * 5);

		return shapeSpecification;
	}
}
