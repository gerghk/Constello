package com.constello.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.contrib.gwtgraphics.client.DrawingArea;

public class Constellation extends DrawingArea {

	/* Default Constructor */
	public Constellation(int width, int height) {
		
		// Call parent's constructor
		super(width, height);
		
	}
	
	/* Add a Star to the Constellation */
	public void addStar(Star s) {
		
		add(s); // add to drawing board
		_stars.add(s); // add to _stars
		s.parentIs(this); // set parent to this
		++_numStars; // increment _numStars
	}
	
	/* Execute _nextMove on the Constellation */
	public Boolean nextMove() {
		
		return true;
	}
	
	/* Audit interface */
	public int auditErrors(int scope) {
		
		// Instantiate the error counter
		AuditReport ar = new AuditReport("Constellation");
		
		// Check invariants
		// ar.verify(_SomeExpression_, "_SomeExpression_ is true");
		
		// Invariant 1.1
		// - check that _stars.size() matches _numStars
		ar.verify(_stars.size() == _numStars, "Star list size matches star count");
		
		// Invariant 1.2
		// - check that getVectorObjectCount matches _numStars + _numLinks
		
		// Invariant 2.1
		// - every Star in _stars should have this Constellation
		//   as the parent
		Iterator<Star> listIt = _stars.iterator();
		while(listIt.hasNext()) {
			
			Star s = listIt.next();
			ar.verify(s.parent() == this, "Star child points back to correct Constellation parent");
		}
		
		return ar.falseInvariants();
	}

	/* Private members */
	private List<Star> _stars = new ArrayList<Star>();
	private Move _nextMove = new Move();
	private int _numStars = 0; // Redundant: for auditing _stars
}
