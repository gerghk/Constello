package com.constello.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.vaadin.contrib.gwtgraphics.client.DrawingArea;
import com.vaadin.contrib.gwtgraphics.client.Line;
import com.vaadin.contrib.gwtgraphics.client.shape.Circle;
import com.vaadin.contrib.gwtgraphics.client.shape.Rectangle;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Constello implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		// Create Constellation
		final Constellation cn = new Constellation(400, 400);
		RootPanel.get("boardContainer").add(cn);
		
		// Create background
		// TODO - Move this to Level constructors
		Rectangle bg = new Rectangle(0, 0, 400, 400);
		bg.setFillColor("black");
		cn.add(bg);
		
		// Add stars to Constellation
		// TODO - Move this to Level constructors
		Star s1 = new Star(200, 200, 15);
		Star s2 = new Star(300, 200, 20);
		Star s3 = new Star(100, 100, 25);
		cn.addStar(s1);
		cn.addStar(s2);
		cn.addStar(s3);
		cn.linkStars(s1, s2);
		cn.linkStars(s2, s3);
		
		final Button startButton = new Button("Start");
		startButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				startButton.setText("Start Over");
				cn.dimLinks();
				cn.activeIs(true);
				cn.nextMove.clear();
			}
		});
		RootPanel.get("boardContainer").add(startButton);
		
		final Button goButton = new Button("Go");
		goButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				cn.makeMove();
			}
		});
		RootPanel.get("boardContainer").add(goButton);
		
		// Initialize Log Buffer
		Log.Initialize();
		
		final ListBox scopeSel = new ListBox();
		scopeSel.addItem("1");
		scopeSel.addItem("2");
		scopeSel.addItem("3");
		scopeSel.addItem("4");
		scopeSel.setVisibleItemCount(1);
		RootPanel.get("logContainer").add(new Label("Scope: "));
		RootPanel.get("logContainer").add(scopeSel);
		
		final Button auditButton = new Button("Audit");
		auditButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				int auditScope = Integer.parseInt(scopeSel.getValue(scopeSel.getSelectedIndex()));
				cn.auditErrors(auditScope);
			}
		});
		RootPanel.get("logContainer").add(auditButton);
		
		// Audit Constellation
		//int errs = cn.auditErrors(4);
	}

	private ConstelloServiceAsync _constelloSvc = GWT.create(ConstelloService.class);
}
