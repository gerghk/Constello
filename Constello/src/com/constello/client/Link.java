package com.constello.client;

import com.vaadin.contrib.gwtgraphics.client.Line;
import com.vaadin.contrib.gwtgraphics.client.animation.Animate;

public class Link extends Line {

	/* Default Constructor */
	public Link(Star s1, Star s2) {
		
		// Call Line constructor with s1 and s2's coordinates
		super(s1.getX(), s1.getY(), s2.getX(), s2.getY());
		
		// Store pointers to the two stars
		_s1 = s1;
		_s2 = s2;
		
		// Set graphical properties
		setStrokeColor("yellow");
		setStrokeWidth(3);
	}
	
	/* Getter for _parent */
	public Constellation parent() {
		
		return _parent;
	}
	
	/* Setter for _parent */
	public void parentIs(Constellation c) {
		
		_parent = c;
	}
	
	/* Check if this link connects to the specified star */
	Boolean hasStar(Star s) {
		
		return _s1 == s || _s2 == s;
	}
	
	/* Getter for _s1 */
	Star s1() {
		
		return _s1;
	}
	
	/* Getter for _s2 */
	Star s2() {
		
		return _s2;
	}
	
	/* Dim the link */
	public void dim() {
		
		new Animate(this, "strokeopacity", getStrokeOpacity(), 0.0, 500).start();
	}
	
	/* Glow the link */
	public void glow() {
		
		new Animate(this, "strokeopacity", getStrokeOpacity(), 0.5, 500).start();
	}

	/* Audit interface */
	public int auditErrors(int scope) {
		
		// Instantiate the error counter
		String auditName = "Link (" + getX1() + ", " + getY1() + "), (" + getX2() + ", " + getY2() + ")";
		AuditReport ar = new AuditReport(auditName);
		
		// Section 1 - Shallow Audit
		if(scope < 1) return ar.falseInvariants();
		Log.logMessage("--- Begin Shallow Audit [" + auditName + "] ---");
		// Invariant 1.1
		// - check that _parent is not null
		ar.verify(_parent != null, "Parent is not null");
		// Invariant 1.2
		// - check that _s1 and _s2 are not null
		ar.verify(_s1 != null && _s2 != null, "Linked stars are not null");
		// Invariant 1.3
		// _s1 != _s2
		ar.verify(_s1 != _s2, "Linked stars are not the same star");
		Log.logMessage("--- End Shallow Audit [" + auditName + "] ---");
		
		// Section 2 - Deep Audit
		if(scope < 2) return ar.falseInvariants();
		Log.logMessage("--- Begin Deep Audit [" + auditName + "] ---");
		// Invariant 2.1
		// - check that _s1 and _s2 contain this link
		ar.verify(_s1.hasLink(this) && _s2.hasLink(this), "Linked stars contain this link");
		// Invariant 2.2
		// - check that _s1 and _s2 are neighbors
		ar.verify(_s1.hasNeighbor(_s2) && _s2.hasNeighbor(_s1), "Linked stars are neighbors");
		Log.logMessage("--- End Deep Audit [" + auditName + "] ---");
		
		// Section 3 - Instantiation Audit
		// This class does not instantiate any objects
		
		return ar.falseInvariants();
	}
	
	/* Private members */
	private Star _s1, _s2;  // The two stars connected by this link
	private Constellation _parent = null;
}
