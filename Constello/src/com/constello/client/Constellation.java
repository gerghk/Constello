package com.constello.client;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.contrib.gwtgraphics.client.DrawingArea;

public class Constellation extends DrawingArea {

	/* Default Constructor */
	public Constellation(int width, int height) {
		
		// Call parent's constructor
		super(width, height);
		
	}

	private List<Star> _stars = new ArrayList<Star>();
}
