package com.constello.client;

public class AuditReport {

	/* Default Constructor */
	public AuditReport(String duaName) {
		
		_duaName = duaName;
	}
	
	/* Getter for _falseInvariants */
	public int falseInvariants() {
		
		return _falseInvariants;
	}
	
	/* Verify the expression is true, otherwise log errors */
	public Boolean verify(Boolean expr, String err_msg) {
		
		if(!expr) {
			
			Log.logMessage("FAIL [" + _duaName + "] : ! " + err_msg);
			++_falseInvariants;
		}
		else {
			
			Log.logMessage("PASS [" + _duaName + "] : " + err_msg);
		}
		
		return expr;
	}
	
	/* Private members */
	private String _duaName;
	private int _falseInvariants = 0;
}
