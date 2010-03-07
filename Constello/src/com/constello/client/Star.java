package com.constello.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.vaadin.contrib.gwtgraphics.client.animation.Animate;
import com.vaadin.contrib.gwtgraphics.client.shape.Circle;

public class Star extends Circle {

	/* Default Constructor */
	public Star(int x, int y, int radius) {
		
		// Call circle constructor
		super(x, y, radius);
		
		// Set graphical properties
		setFillColor("yellow");
		setStrokeColor("red");
		setStrokeOpacity(0);
		setStrokeWidth(5);
		
		// Define the click handler
		addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				// Need victim because 'this' refers to the ClickHandler
				Star victim = (Star) event.getSource();
				
				if(!_parent.active() || nimmed()) return;
				
				if (!_parent.nextMove.empty()) {

					// See if it is nextMove.peek()'s neighbor
					if (!_neighbors.contains(_parent.nextMove.peek())) {

						// If not, start a new Move
						_parent.nextMove.clear();
						_parent.dimLinks();
					}
					else {

						// Otherwise, glow the path from nextMove.peek() to this star
						_links.get(_parent.nextMove.peek()).glow();
					}
				}

				victim.nimmedIs(true);
				_parent.nextMove.push(victim);
			}
		});
	}
	
	/* Getter for _index */
	public int index() {
		
		return _index;
	}
	
	/* Setter for _index */
	public void indexIs(int i) {
		
		_index = i;
	}
	
	/* Getter for _parent */
	public Constellation parent() {
		
		return _parent;
	}
	
	/* Setter for _parent */
	public void parentIs(Constellation c) {
		
		_parent = c;
	}
	
	/* Getter for _nimmed */
	public Boolean nimmed() {
		
		return _nimmed;
	}
	
	/* Setter for _nimmed */
	public void nimmedIs(Boolean n) {
		
		if(n) {
		
			// Glow the red outline for newly nimmed stars
			new Animate(this, "strokeopacity", 0.0, 1.0, 500).start();
		}
		else {
			
			// Dim the red outline for newly unnimmed stars
			new Animate(this, "strokeopacity", 1.0, 0.0, 500).start();
		}
		
		_nimmed = n;
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
	private int _index; // Used as unique id for server communication, corresponds to index in parent's _stars
	private List<Star> _neighbors = new ArrayList<Star>();
	private Map<Star, Link> _links = new HashMap<Star, Link>();
	private Constellation _parent = null;
	private Boolean _nimmed = false; // True if removed by player
	private int _numNeighbors = 0; // redundant: for auditing _neighbors
	private int _numLinks = 0; // redundant: for auditing _links
}
