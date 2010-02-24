package com.constello.client;

import com.vaadin.contrib.gwtgraphics.client.shape.Circle;

public class Star extends Circle {

	/* Default Constructor */
	public Star(int x, int y, int radius) {
		super(x, y, radius);
		// TODO Auto-generated constructor stub
	}

	/* Audit interface */
	public int auditErrors(int scope) {
		
		// Instantiate the error counter
		AuditReport ar = new AuditReport();
		
		// Check invariants
		// ar.verify(_SomeExpression_, "_SomeExpression_ is true");
		
		return ar.falseInvariants();
	}

}
