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
import com.google.gwt.user.client.ui.HorizontalPanel;
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
	
	protected enum gameMode {
		SOLO, CPU, VS
	}

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
		cn = new Constellation(400, 400, gameMode.SOLO);
		cn.parentIs(this);
		RootPanel.get("boardContainer").add(cn);
		
		// Menu Bar
		HorizontalPanel menuBar = new HorizontalPanel();
		menuBar.setSpacing(5);
		RootPanel.get("menuBar").add(menuBar);
		
		// Mode select
		final ListBox modeSel = new ListBox();
		modeSel.addItem("Solo play");
		modeSel.addItem("Computer Opponent");
		modeSel.addItem("Human Opponent");
		modeSel.setVisibleItemCount(1);
		menuBar.add(new Label("Game Mode: "));
		menuBar.add(modeSel);
		
		// Level select
		final ListBox levelSel = new ListBox();
		levelSel.addItem("Orion");
		levelSel.setVisibleItemCount(1);
		menuBar.add(new Label("Level: "));
		menuBar.add(levelSel);
		
		// Load button
		final Button loadButton = new Button("Load");
		// Also need to forward declare startButton
		startButton = new Button("Start");
		// Click handler doesn't see this
		final Constello me = this;
		loadButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				String selectedMode = modeSel.getValue(modeSel.getSelectedIndex());
				gameMode mode;
				if(selectedMode.equals("Solo play")) {
					
					mode = gameMode.SOLO;
				}
				else if(selectedMode.equals("Computer Opponent")) {
					
					mode = gameMode.CPU;
				}
				else {
					
					mode = gameMode.VS;
				}
				
				String selectedLevel = levelSel.getValue(levelSel.getSelectedIndex());
				if(selectedLevel.equals("Orion")) {
					
					cn = new Orion(mode);
					cn.parentIs(me);
				}
				
				RootPanel.get("boardContainer").clear();
				RootPanel.get("boardContainer").add(cn);
				startButton.setText("Start");
				startButton.setEnabled(true);
			}
		});
		menuBar.add(loadButton);
		
		// Game control buttons
		startButton.setEnabled(false); // Initially disabled
		goButton = new Button("Make Move");
		startButton.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				
				startButton.setText("Start Over");
				activeIs(false); // wait until nextMove is populated
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
					
					activeIs(false);
				}
			}
		});
		goButton.setEnabled(false); // Initially disabled
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
	
	public void activeIs(Boolean active) {
		
		startButton.setEnabled(active);
		goButton.setEnabled(active);
	}

	private ConstelloServiceAsync _constelloSvc = GWT.create(ConstelloService.class);
	private Constellation cn = new Constellation(400, 400, gameMode.SOLO);
	private Button startButton;
	private Button goButton;
}
