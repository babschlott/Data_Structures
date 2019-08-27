/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.awt.Color;

// A colored point in the plane
public class ColoredPoint2D {
	public double x, y; // coordinates
	public Color color; // color
	
	ColoredPoint2D(double x, double y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
}
