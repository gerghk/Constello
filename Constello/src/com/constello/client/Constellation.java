package com.constello.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.contrib.gwtgraphics.client.DrawingArea;
import com.vaadin.contrib.gwtgraphics.client.animation.Animate;

public class Constellation extends DrawingArea {

	/* Default Constructor */
	public Constellation(int width, int height) {
		
		// Call parent's constructor
		super(width, height);
	}
	
	/* Add a Star to the Constellation */
	public void addStar(Star s) {
		
		add(s);
		_stars.add(s);
		s.parentIs(this);
		s.indexIs(_numStars++);
	}
	
	/* Create a link between two stars */
	public void linkStars(Star s1, Star s2) {
		
		Link lk = new Link(s1, s2);
		s1.addNeighbor(s2, lk);
		s2.addNeighbor(s1, lk);
		add(lk);
		_links.add(lk);
		lk.parentIs(this);
		++_numLinks;
	}
	
	/* Slowly dim the links at beginning of round*/
	public void dimLinks() {
		
		Iterator<Link> linkListIt = _links.iterator();
		while(linkListIt.hasNext()) {
			
			Link l = linkListIt.next();
			l.dim();
		}
	}
	
	/* Getter for _active */
	public Boolean active() {
		
		return _active;
	}
	
	/* Setter for _active */
	public void activeIs(Boolean active) {
		
		_active = active;
	}
	
	/* Execute nextMove on the Constellation */
	public Boolean makeMove() {
		
		// TODO
		return true;
	}
	
	/* Audit interface */
	public int auditErrors(int scope) {
		
		// Instantiate the error counter
		String auditName = "Constellation";
		AuditReport ar = new AuditReport(auditName);
		
		// Section 1 - Shallow Audit
		if(scope < 1) return ar.falseInvariants();
		Log.logMessage("--- Begin Shallow Audit [" + auditName + "] ---");
		// Invariant 1.1
		// - check that _stars.size() matches _numStars
		ar.verify(_stars.size() == _numStars, "Star list size matches star count");
		// Invariant 1.2
		// - check that _links.size() matches _numLinks
		ar.verify(_links.size() == _numLinks, "Link list size matches link count");
		// Invariant 1.3
		// - check that getVectorObjectCount matches _numStars + _numLinks
		ar.verify(getVectorObjectCount() == (_numStars + _numLinks), "Vector object count matches (star count + link count)");
		// Invariant 1.4
		// - check that nextMove is empty if _active is false
		ar.verify(_active || nextMove.empty(), "Constellation is either active or nextMove must be empty");
		Log.logMessage("--- End Shallow Audit [" + auditName + "] ---");
		
		// Section 2 - Deep Audit
		if(scope < 2) return ar.falseInvariants();
		Log.logMessage("--- Begin Deep Audit [" + auditName + "] ---"); 
		// Invariant 2.1
		// - every Star in _stars should have this Constellation as the parent
		Iterator<Star> starListIt = _stars.iterator();
		while(starListIt.hasNext()) {
			
			Star s = starListIt.next();
			ar.verify(s.parent() == this, "Star child points back to correct Constellation parent");
		}
		// Invariant 2.2
		// - every Link in _links should have this Constellation as the parent
		Iterator<Link> linkListIt = _links.iterator();
		while(linkListIt.hasNext()) {
			
			Link l = linkListIt.next();
			ar.verify(l.parent() == this, "Link child points back to correct Constellation parent");
		}
		Log.logMessage("--- End Deep Audit [" + auditName + "] ---");
		
		// Section 3 - Instantiation Audit
		if(scope < 3) return ar.falseInvariants();
		Log.logMessage("--- Begin Instantiation Audit [" + auditName + "] ---");
		// Invariant 3.1
		// - check that all Star children in _stars pass audit
		int childErrors = 0;
		starListIt = _stars.iterator();
		while(starListIt.hasNext()) {
			
			Star s = starListIt.next();
			childErrors += s.auditErrors(scope - 2);
		}
		ar.verify(childErrors == 0, "All star children passed audit");
		// Invariant 3.2
		// - check that all Link children in _links pass audit
		childErrors = 0;
		linkListIt = _links.iterator();
		while(linkListIt.hasNext()) {
			
			Link l = linkListIt.next();
			childErrors += l.auditErrors(scope - 2);
		}
		ar.verify(childErrors == 0, "All link children passed audit");
		// Invariant 3.3
		// - check that nextMove passes audit
		childErrors = nextMove.auditErrors(scope - 2);
		ar.verify(childErrors == 0, "nextMove passed audit");
		Log.logMessage("--- End Instantiation Audit [" + auditName + "] ---");
		
		
		return ar.falseInvariants();
	}

	/* Protected members */
	protected Move nextMove = new Move();
	
	/* Private members */
	private List<Star> _stars = new ArrayList<Star>();
	private List<Link> _links = new ArrayList<Link>();
	private int _numStars = 0; // Redundant: for auditing _stars
	private int _numLinks = 0; // Redundant: for auditing _links
	private Boolean _active = false;
}
