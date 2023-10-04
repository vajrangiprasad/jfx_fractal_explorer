package jfx.fractal.explorer.util;

import java.util.List;

import javafx.css.converter.StringConverter;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import jfx.fractal.explorer.turtle.TurtleShape;

public class FractalUtility {
	public static String getWebColor(Color color) {
		String strColor = color.toString();
		
		if(strColor.contains("0x")) {
			strColor = strColor.replace("0x", "#");
		}
		return strColor;
	}
	
	public static ImageView getImageView(String url) {
		Image image = new Image(FractalUtility.class.getResourceAsStream(url));
		
		return new ImageView(image);
	}
	
	public static BoundingBox getBoundinBox(TurtleShape shape) {
		double xmin = Double.MAX_VALUE;
		double xmax = Double.MIN_VALUE;
		double ymin = Double.MAX_VALUE;
		double ymax = Double.MIN_VALUE;
		
		if(shape == null || shape.getPoints() == 0) {
			return new BoundingBox(0, 0, 0, 0);
		}
		
		double[] x = shape.getX();
		double[] y = shape.getY();
		for(int i = 0; i < shape.getPoints();i++) {
			if(x[i] < xmin) {
				xmin = x[i];
			}
			if(x[i]  > xmax) {
				xmax = x[i];
			}
			if(y[i] < ymin) {
				ymin = y[i];
			}
			if(y[i]  > ymax) {
				ymax = y[i];
			}
		}
		
		BoundingBox bbox = new BoundingBox(xmin, ymin, xmax-xmin, ymax-ymin);
		
		return bbox;
	}
	
	public static double[] getDoubleArrray(List<Double> doubleList) {
		if(doubleList == null || doubleList.size() == 0) {
			return null;
		}
		double[] doubleArray = new double[doubleList.size()];
		
		for(int i = 0;i<doubleList.size();i++) {
			doubleArray[i] = doubleList.get(i);
		}
		return doubleArray;
	}
	
	public static Point2D rotate(Point2D point,Point2D origin,double angdeg) {
		double t = Math.toRadians(angdeg);
		double s = Math.sin(-t);
		double c = Math.cos(-t);
		double x = (point.getX()*c + point.getY()*s) + origin.getX();
		double y = (-point.getX()*s + point.getY()*c) + origin.getY();
		
		return new Point2D(x, y);
	}
}
