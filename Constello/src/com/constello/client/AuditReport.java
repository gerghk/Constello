package com.constello.client;

public class AuditReport {

	/* Default Constructor */
	public AuditReport() {
		
	}
	
	/* Getter for _falseInvariants */
	public int falseInvariants() {
		
		return _falseInvariants;
	}
	
	/* Setter for _falseInvariants */
	public void falseInvariantsIs(int errs) {
		
		_falseInvariants = errs;
	}
	
	/* Verify the expression is true, otherwise log errors */
	public Boolean verify(Boolean expr, String err_msg) {
		
		if(!expr) {
			
			++_falseInvariants;
		}
		
		return expr;
	}
	
	/* Private members */
	private int _falseInvariants = 0;
}