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
		String auditName = "Move";
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
		// This class does not instantiate any objects
		
		return ar.falseInvariants();
	}

}
