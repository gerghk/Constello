package com.constello.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.contrib.gwtgraphics.client.shape.Circle;

public class Star extends Circle {

	/* Default Constructor */
	public Star(int x, int y, int radius) {
		super(x, y, radius);
		// TODO Auto-generated constructor stub
	}
	
	/* Getter for _parent */
	public Constellation parent() {
		
		return _parent;
	}
	
	/* Setter for _parent */
	public void parentIs(Constellation c) {
		
		_parent = c;
	}
	
	/* Add a neighbor and its corresponding link */
	public void addNeighbor(Star nbr, Link lnk) {
		
		_neighbors.add(nbr);
		_links.put(nbr, lnk);
	}

	/* Audit interface */
	public int auditErrors(int scope) {
		
		// Instantiate the error counter
		AuditReport ar = new AuditReport("Star (" + getX() + ", " + getY() + ")");
		
		// Check invariants
		// ar.verify(_SomeExpression_, "_SomeExpression_ is true");
		
		return ar.falseInvariants();
	}

	/* Private members */
	private List<Star> _neighbors = new ArrayList<Star>();
	private Map<Star, Link> _links = new HashMap<Star, Link>();
	private Constellation _parent = null;
	private int _numNeighbors = 0; // redundant: for auditing _neighbors
	private int _numLinks = 0; // redundant: for auditing _links
}
