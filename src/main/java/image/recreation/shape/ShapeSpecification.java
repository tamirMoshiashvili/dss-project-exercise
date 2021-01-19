package image.recreation.shape;

import java.awt.*;
import java.awt.geom.Point2D;

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

	public Point2D.Float getLocation() {
		return location;
	}

	public void setLocation(Point2D.Float location) {
		this.location = location;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ShapeSpecification clone() {
		return new ShapeSpecification((Point2D.Float) location.clone(), orientation,
				scale, new Color(color.getRGB(), true));
	}
}
