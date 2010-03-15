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
		final Constellation cn = new Orion();
		RootPanel.get("boardContainer").add(cn);
		
		// Game control buttons
		final Button startButton = new Button("Start");
		final Button goButton = new Button("Make Move");
		startButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				startButton.setText("Start Over");
				goButton.setEnabled(true);
				cn.dimLinks();
				cn.activeIs(true);
				cn.nextMove.clear();
			}
		});
		RootPanel.get("gameControl").add(startButton);
		goButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				Boolean result = cn.makeMove();
				if(result) {
					
					startButton.setEnabled(false);
					goButton.setEnabled(false);
				}
			}
		});
		goButton.setEnabled(false);
		RootPanel.get("gameControl").add(goButton);
		
		// Initialize Log Buffer
		Log.Initialize();
		
		// Drop-down select for audit scope
		final ListBox scopeSel = new ListBox();
		scopeSel.addItem("1");
		scopeSel.addItem("2");
		scopeSel.addItem("3");
		scopeSel.addItem("4");
		scopeSel.setVisibleItemCount(1);
		RootPanel.get("auditControl").add(new Label("Scope: "));
		RootPanel.get("auditControl").add(scopeSel);
		
		// Audit button
		final Button auditButton = new Button("Audit");
		auditButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				int auditScope = Integer.parseInt(scopeSel.getValue(scopeSel.getSelectedIndex()));
				cn.auditErrors(auditScope);
			}
		});
		RootPanel.get("auditControl").add(auditButton);
		
		// Clear button
		final Button clearButton = new Button("Clear");
		clearButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				Log.clear();
			}
		});
		RootPanel.get("auditControl").add(clearButton);
	}

	private ConstelloServiceAsync _constelloSvc = GWT.create(ConstelloService.class);
}
