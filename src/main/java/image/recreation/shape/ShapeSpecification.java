package image.recreation.shape;

import lombok.Data;

import java.awt.*;
import java.awt.geom.Point2D;

@Data
public class ShapeSpecification {
	private Point2D.Float location;
	private int orientation;
	private float scale;
	private Color color;

	public ShapeSpecification(Point2D.Float location, int orientation, float scale, Color color) {
		this.location = location;
		this.orientation = orientation;
		this.scale = scale;
		this.color = color;
	}

	@SuppressWarnings("MethodDoesntCallSuperMethod")
	public ShapeSpecification clone() {
		return new ShapeSpecification((Point2D.Float) location.clone(), orientation,
				scale, new Color(color.getRGB(), true));
	}
}
