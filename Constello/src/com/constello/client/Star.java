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
		setStrokeColor("white");
		setStrokeOpacity(0);
		setStrokeWidth(5);
		
		// Define the ClickHandler
		addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				// Need victim because 'this' refers to the ClickHandler
				Star victim = (Star) event.getSource();
				
				if(!_parent.active()) return; // Return if parent is not active
				else if(nimmed()) {
					
					// Flash a green outline and return if this star is already nimmed
					setStrokeColor("green");
					new Animate(victim, "strokeopacity", 0.0, 1.0, 1000).start();
					return;
				}
				
				// Check if there is a move already
				if (!_parent.nextMove.empty()) {

					// See if this star is nextMove.peek()'s neighbor
					if (!_neighbors.contains(_parent.nextMove.peek())) {

						// If not, flash a red outline and return
						setStrokeColor("red");
						new Animate(victim, "strokeopacity", 1.0, 0.0, 1000).start();
						return;
					}
					else {

						// Otherwise, glow the path from nextMove.peek() to this star
						_links.get(_parent.nextMove.peek()).glow();
					}
				}

				victim.nimmedIs(true);
				_parent.nextMove.push(victim);
			}
		}); // End ClickHandler definition
		
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
		
			// Glow a green outline for newly nimmed stars
			setStrokeColor("green");
			new Animate(this, "strokeopacity", 0.0, 1.0, 500).start();
		}
		else {
			
			// Dim the outline for newly unnimmed stars
			new Animate(this, "strokeopacity", 1.0, 0.0, 500).start();
		}
		
		_nimmed = n;
	}
	
	/* Add a neighbor and its corresponding link */
	public void addNeighbor(Star nbr, Link lnk) {
		
		_neighbors.add(nbr);
		_links.put(nbr, lnk);
	}
	
	/* Check if the specified star is a neighbor */
	public Boolean hasNeighbor(Star s) {
		
		return _neighbors.contains(s);
	}
	
	/* Check if the specified link exists for this star */
	public Boolean hasLink(Link l) {
		
		return _links.containsValue(l);
	}
	
	/* Audit interface */
	public int auditErrors(int scope) {
		
		// Instantiate the error counter
		String auditName = "Star (" + getX() + ", " + getY() + ")";
		AuditReport ar = new AuditReport(auditName);
		
		// Section 1 - Shallow Audit
		if(scope < 1) return ar.falseInvariants();
		Log.logMessage("--- Begin Shallow Audit [" + auditName + "] ---");
		Log.logMessage("--- End Shallow Audit [" + auditName + "] ---");
		
		// Section 2 - Deep Audit
		if(scope < 2) return ar.falseInvariants();
		Log.logMessage("--- Begin Deep Audit [" + auditName + "] ---");
		Log.logMessage("--- End Deep Audit [" + auditName + "] ---");
		
		// Section 3 - Instantiation Audit
		if(scope < 3) return ar.falseInvariants();
		Log.logMessage("--- Begin Instantiation Audit [" + auditName + "] ---");
		Log.logMessage("--- End Instantiation Audit [" + auditName + "] ---");
		
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
