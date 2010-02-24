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
	
	/* Function for executing a Move on the Constellation */
	public Boolean executeMove(Move move) {
		
		return true;
	}
	
	/* Audit interface */
	public int auditErrors(int scope) {
		
		// Instantiate the error counter
		AuditReport ar = new AuditReport();
		
		// Check invariants
		// ar.verify(_SomeExpression_, "_SomeExpression_ is true");
		
		return ar.falseInvariants();
	}

	/* Private members */
	private List<Star> _stars = new ArrayList<Star>();
}
