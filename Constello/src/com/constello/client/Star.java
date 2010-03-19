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
					
					// Flash its green outline and return if this star is already nimmed
					new Animate(victim, "strokeopacity", 0.0, 1.0, 500).start();
					return;
				}
				
				// Check if there is a move already
				if (!_parent.nextMove.empty()) {

					// See if this star is nextMove.peek()'s neighbor
					if (!_neighbors.contains(_parent.nextMove.peek())) {

						// If not, flash a red outline and return
						setStrokeColor("red");
						new Animate(victim, "strokeopacity", 1.0, 0.0, 500).start();
						return;
					}
					else {

						// Otherwise, glow the path from nextMove.peek() to this star
						_links.get(_parent.nextMove.peek()).glow();
					}
				}

				victim.nimmedIs(true);
				_parent.nextMove.push(victim);
				_parent.enable(true);
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
		_numNeighbors++;
		_links.put(nbr, lnk);
		_numLinks++;
	}
	
	/* Check if the specified star is a neighbor */
	public Boolean hasNeighbor(Star s) {
		
		return _neighbors.contains(s);
	}
	
	/* Getter for _neighbors */
	public Iterator<Star> neighbors() {
		
		return _neighbors.iterator();
	}
	
	/* Getter for _numNeighbors */
	public int numNeighbors() {
		
		return _numNeighbors;
	}
	
	/* Check if the specified link exists for this star */
	public Boolean hasLink(Link l) {
		
		return _links.containsValue(l);
	}
	
	/* Getter for _links */
	public Iterator<Link> links() {
		
		return _links.values().iterator();
	}
	
	/* Getter for _numLinks */
	public int numLinks() {
		
		return _numLinks;
	}
	
	/* Audit interface */
	public int auditErrors(int scope) {
		
		// Instantiate the error counter
		String auditName = "Star (" + getX() + ", " + getY() + ")";
		AuditReport ar = new AuditReport(auditName);
		
		// Section 1 - Shallow Audit
		if(scope < 1) return ar.falseInvariants();
		Log.logMessage("--- Begin Shallow Audit [" + auditName + "] ---");
		// Invariant 1.1
		// - check that _parent is not null
		ar.verify(_parent != null, "Parent is not null");
		// Invariant 1.2
		// - check that _index is set (and not negative)
		ar.verify(_index > -1, "Index is set and not negative");
		// Invariant 1.3
		// - check that this star has neighbors
		ar.verify(!_neighbors.isEmpty(), "This star has neighbors");
		// Invariant 1.4
		// - check that this star has links
		ar.verify(!_links.isEmpty(), "This star has links");
		// Invariant 1.5
		// - check that _neighbors.size() matches _numNeighbors
		ar.verify(_neighbors.size() == _numNeighbors, "Neighbor list size matches neighbor count");
		// Invariant 1.6
		// - check that _links.size() matches _numLinks
		ar.verify(_links.size() == _numLinks, "Link map size matches link count");
		// Invariant 1.7
		// - check that _numNeighbors equals _numLinks
		ar.verify(_numNeighbors == _numLinks, "Number of neighbors equals number of links");
		Log.logMessage("--- End Shallow Audit [" + auditName + "] ---");
		
		// Section 2 - Deep Audit
		if(scope < 2) return ar.falseInvariants();
		Log.logMessage("--- Begin Deep Audit [" + auditName + "] ---");
		// Invariant 2.1
		// - check that each neighbor has this star as a neighbor and has the same link
		Iterator<Star> neighborListIt = neighbors();
		while(neighborListIt.hasNext()) {
			
			Star s = neighborListIt.next();
			ar.verify(s.hasNeighbor(this), "Neighbor also has this star as neighbor");
			ar.verify(s.hasLink(_links.get(s)), "Neighbor shares link with this star");
		}
		// Invariant 2.2
		// - check that each link contains this star
		Iterator<Link> linkMapIt = links();
		while(linkMapIt.hasNext()) {
			
			Link l = linkMapIt.next();
			ar.verify(l.hasStar(this), "Link contains this star");
		}
		Log.logMessage("--- End Deep Audit [" + auditName + "] ---");
		
		// Section 3 - Instantiation Audit
		// This class does not instantiate any objects
		
		return ar.falseInvariants();
	}

	/* Private members */
	private int _index = -1; // Used as unique id for server communication, corresponds to index in parent's _stars
	private List<Star> _neighbors = new ArrayList<Star>();
	private Map<Star, Link> _links = new HashMap<Star, Link>();
	private Constellation _parent = null;
	private Boolean _nimmed = false; // True if removed by player
	private int _numNeighbors = 0; // redundant: for auditing _neighbors
	private int _numLinks = 0; // redundant: for auditing _links
}
