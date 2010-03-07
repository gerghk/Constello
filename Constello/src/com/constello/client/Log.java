/**
 * 
 */
package com.constello.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author gerghk
 *
 */
public class Log extends ScrollPanel {

	/* Default Constructor */
	public Log() {
		// TODO Auto-generated constructor stub
	}

	/* Constructor which takes an initial child */
	public Log(Widget child) {
		super(child);
		// TODO Auto-generated constructor stub
	}
	
	/* Initialize log and attach to RootPanel::logContainer */
	public static void Initialize() {
		
		RootPanel.get("logContainer").clear();
		Buffer = new VerticalPanel();
		Scroller = new ScrollPanel(Buffer);
		Scroller.setSize("400", "400");
		RootPanel.get("logContainer").add(Scroller);
	}
	
	/* Append line to log buffer */
	public static void logMessage(String msg) {
		
		Label logmsg = new Label(msg + "\n");
		Buffer.add(logmsg);
		Scroller.ensureVisible(logmsg);
	}

	private static VerticalPanel Buffer;
	private static ScrollPanel Scroller;
}
