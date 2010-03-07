package com.constello.client;

import java.util.Stack;

public class Move extends Stack<Star> {

	/* Default constructor */
	public Move() {
		
		super();
	}
	
	@Override
	public void clear() {
		
		while(!empty()) {
			
			Star s = pop();
			s.nimmedIs(false);
		}
	}
	
	/* Audit interface */
	public int auditErrors(int scope) {
		
		// Instantiate the error counter
		AuditReport ar = new AuditReport("Move");
		
		// Check invariants
		// ar.verify(_SomeExpression_, "_SomeExpression_ is true");
		
		return ar.falseInvariants();
	}

}
