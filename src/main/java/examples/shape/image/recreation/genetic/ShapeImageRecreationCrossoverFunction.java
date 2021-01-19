package examples.shape.image.recreation.genetic;

import image.recreation.ImageRecreation;
import image.recreation.shape.ShapeSpecification;
import optimization.genetic.CrossoverFunction;
import optimization.genetic.selection.Selector;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ShapeImageRecreationCrossoverFunction implements CrossoverFunction<ImageRecreation<ShapeSpecification>> {
	private Function<List<ShapeSpecification>, ImageRecreation<ShapeSpecification>> factoryFunction;

	public ShapeImageRecreationCrossoverFunction(
			Function<List<ShapeSpecification>, ImageRecreation<ShapeSpecification>> factoryFunction) {
		this.factoryFunction = factoryFunction;
	}

	@Override
	public ImageRecreation<ShapeSpecification> createOffspringBy(Selector<ImageRecreation<ShapeSpecification>> selector) {
		ImageRecreation<ShapeSpecification> parent1 = selector.select();
		ImageRecreation<ShapeSpecification> parent2 = selector.select();

		List<ShapeSpecification> shapeSpecifications = isParent1ContainsLessShapeSpecifications(parent1, parent2)
				? createMixedShapeSpecifications(parent1.getItems(), parent2.getItems())
				: createMixedShapeSpecifications(parent2.getItems(), parent1.getItems());

		return factoryFunction.apply(shapeSpecifications);
	}

	private boolean isParent1ContainsLessShapeSpecifications(ImageRecreation<ShapeSpecification> parent1, ImageRecreation<ShapeSpecification> parent2) {
		return parent1.getItems().size() <= parent2.getItems().size();
	}

	private List<ShapeSpecification> createMixedShapeSpecifications(List<ShapeSpecification> shapeSpecifications1,
																	List<ShapeSpecification> shapeSpecifications2) {
		List<ShapeSpecification> shapeSpecifications = new ArrayList<>();
		for (int i = 0; i < shapeSpecifications1.size(); i++) {
			ShapeSpecification parent1 = shapeSpecifications1.get(i);
			ShapeSpecification parent2 = shapeSpecifications2.get(i);

			shapeSpecifications.add(createMixedShapeSpecification(parent1, parent2));
		}

		return shapeSpecifications;
	}

	private ShapeSpecification createMixedShapeSpecification(ShapeSpecification shapeSpecification1, ShapeSpecification shapeSpecification2) {
		Point2D.Float location = shapeSpecification1.getLocation();
		int orientation = shapeSpecification1.getOrientation();
		float scale = shapeSpecification2.getScale();
		Color color = shapeSpecification2.getColor();

		return new ShapeSpecification(location, orientation, scale, color);
	}
}
