package com.constello.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.constello.client.Constello.gameMode;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vaadin.contrib.gwtgraphics.client.DrawingArea;
import com.vaadin.contrib.gwtgraphics.client.animation.Animate;
import com.vaadin.contrib.gwtgraphics.client.shape.Rectangle;
import com.vaadin.contrib.gwtgraphics.client.shape.Text;

public class Constellation extends DrawingArea {

	/* Simple Computer Opponent */
	private void cpuMove() {

		Iterator<Star> starListIterator = _stars.iterator();
		Star startPoint = null;
		// Just find the first star that is not nimmed yet
		while(starListIterator.hasNext()) {

			startPoint = starListIterator.next();
			if(!startPoint.nimmed()) break;
		}

		// Nim startPoint
		startPoint.nimmedIs(true);
		nextMove.push(startPoint);
		
		// Nim up to a random number of stars between 0 and (_numStars - _numNimmed - 2)
		// (obviously need to stop when nextMove.peek() has no more unnimmed neighbors)
		int targetRemoved = Random.nextInt(_numStars - _numNimmed - 2);
		for(int i=0; i!=targetRemoved; ++i) {
			
			Iterator<Star> nbrs = nextMove.peek().neighbors();
			// Iterate through nextMove.peek()'s neighbors to find an unnimmed star
			Boolean victimFound = false;
			while(nbrs.hasNext()) {
				
				Star victim = nbrs.next();
				if(!victim.nimmed()) {
					
					// Nim the unnimmed neighbor
					victim.nimmedIs(true);
					nextMove.push(victim);
					victimFound = true;
					break;
				}
			}
			
			// Break if no more unnimmed neighbors can be found
			if(!victimFound) break;
		}
		
		// Dim the selected stars to indicate they have been nimmed
		while(!nextMove.empty()) {
			
			Star s = nextMove.pop();
			new Animate(s, "fillopacity", 1.0, 0.5, 1000).start();
			_numNimmed++;
		}
	}
	
	/* Default Constructor */
	public Constellation(int width, int height, gameMode mode) {
		
		// Call parent's constructor
		super(width, height);
		
		// Set the background
		Rectangle bg = new Rectangle(0, 0, 400, 400);
		bg.setFillColor("black");
		add(bg);
		
		// Set the game mode
		_mode = mode;
	}
	
	/* Add a Star to the Constellation */
	public void addStar(Star s) {
		
		add(s);
		_stars.add(s);
		s.parentIs(this);
		s.indexIs(_numStars++);
	}
	
	/* Create a link between two stars */
	public void linkStars(Star s1, Star s2) {
		
		Link lk = new Link(s1, s2);
		s1.addNeighbor(s2, lk);
		s2.addNeighbor(s1, lk);
		add(lk);
		_links.add(lk);
		lk.parentIs(this);
		++_numLinks;
	}
	
	/* Slowly dim the links at beginning of round*/
	public void dimLinks() {
		
		Iterator<Link> linkListIt = _links.iterator();
		while(linkListIt.hasNext()) {
			
			Link l = linkListIt.next();
			l.dim();
		}
	}
	
	/* Getter for _active */
	public Boolean active() {
		
		return _active;
	}
	
	/* Setter for _active */
	public void activeIs(Boolean active) {
		
		_active = active;
	}
	
	/* Execute nextMove on the Constellation */
	public Boolean makeMove() {
		
		dimLinks();
		enable(false);
		_numMoves++;
		
		// Dim the selected stars to indicate they have been nimmed
		while(!nextMove.empty()) {
			
			Star s = nextMove.pop();
			new Animate(s, "fillopacity", 1.0, 0.5, 1000).start();
			_numNimmed++;
		}
		
		if(_mode == gameMode.SOLO) {
			
			if(_numNimmed == _numStars) {

				activeIs(false);
				Text gameover = new Text(100, 300, "You finished in " + _numMoves + " moves");
				gameover.setStrokeColor("yellow");
				add(gameover);
				new Animate(gameover, "strokeopacity", 0.0, 1.0, 1000).start();
				return true;
			}
		}
		else if(_mode == gameMode.CPU){
		
			// If player left the last star for CPU, he wins
			if(_numNimmed == (_numStars - 1)) {
				
				activeIs(false);
				Text gameover = new Text(100, 300, "Congratulations, you won!");
				gameover.setStrokeColor("yellow");
				add(gameover);
				new Animate(gameover, "strokeopacity", 0.0, 1.0, 1000).start();
				return true;
			}
			// If stupid player removed all the stars, he loses
			else if(_numNimmed == _numStars) {
				
				activeIs(false);
				Text gameover = new Text(100, 300, "You lost by suicide!");
				gameover.setStrokeColor("red");
				add(gameover);
				new Animate(gameover, "strokeopacity", 0.0, 1.0, 1000).start();
				return true;
			}
			// Otherwise, let the CPU opponent make its move
			else {
				
				cpuMove();
				// If CPU left the last star for player, player loses
				if(_numNimmed == (_numStars - 1)) {
					
					activeIs(false);
					Text gameover = new Text(100, 300, "You lost to the computer");
					gameover.setStrokeColor("red");
					add(gameover);
					new Animate(gameover, "strokeopacity", 0.0, 1.0, 1000).start();
					return true;
				}
			}
		}
		else if(_mode == gameMode.VS){
			
			AsyncCallback<int[]> callback = new AsyncCallback<int[]>() {

				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}

				public void onSuccess(int[] counter) {
					// TODO Auto-generated method stub
					Log.logMessage("wtfbbq " + counter[0] + " " + counter[1]);
				}
			};
			int dumbArray[] = new int[3];
			dumbArray[0] = 2;
			dumbArray[1] = 3;
			dumbArray[2] = 4;
			_constelloSvc.sendMove(dumbArray, callback);
		}
		
		return false;
	}
	
	/* Setter and Getter for _parent */
	public Constello parent() {
		
		return _parent;
	}
	public void parentIs(Constello p) {
		
		_parent = p;
	}
	public void enable(Boolean foo) {
		
		_parent.activeIs(foo);
	}
	
	/* Audit interface */
	public int auditErrors(int scope) {
		
		// Instantiate the error counter
		String auditName = "Constellation";
		AuditReport ar = new AuditReport(auditName);
		
		// Section 1 - Shallow Audit
		if(scope < 1) return ar.falseInvariants();
		Log.logMessage("--- Begin Shallow Audit [" + auditName + "] ---");
		// Invariant 1.1
		// - check that _stars.size() matches _numStars
		ar.verify(_stars.size() == _numStars, "Star list size matches star count");
		// Invariant 1.2
		// - check that _links.size() matches _numLinks
		ar.verify(_links.size() == _numLinks, "Link list size matches link count");
		// Invariant 1.3
		// - check that getVectorObjectCount matches _numStars + _numLinks + 1
		ar.verify(!_active || getVectorObjectCount() == (_numStars + _numLinks + 1), "Vector object count matches (star count + link count + 1)");
		// Invariant 1.4
		// - check that nextMove is empty if _active is false
		ar.verify(_active || nextMove.empty(), "Constellation is either active or nextMove must be empty");
		Log.logMessage("--- End Shallow Audit [" + auditName + "] ---");
		
		// Section 2 - Deep Audit
		if(scope < 2) return ar.falseInvariants();
		Log.logMessage("--- Begin Deep Audit [" + auditName + "] ---");
		// Invariants for _stars
		Iterator<Star> starListIt = _stars.iterator();
		int nimmedStars = 0;
		while(starListIt.hasNext()) {
			
			Star s = starListIt.next();
			
			// Invariant 2.1.a
			// - every Star in _stars should have this Constellation as the parent
			ar.verify(s.parent() == this, "Star child points back to correct Constellation parent");
			// Invariant 2.1.b
			// - each Star must not have more than _stars.size()-1 neighbors
			ar.verify(s.numNeighbors() < _stars.size(), "Star child has less neighbors than the total number of stars");
			// Invariant 2.1.c
			// - each Star's neighbors must be contained in _stars
			Iterator<Star> sIt = s.neighbors();
			while(sIt.hasNext()) {
				
				Star nbr = sIt.next();
				ar.verify(_stars.contains(nbr), "Star child's neighbors are also part of this Constellation");
			}
			// Invariant 2.1.d
			// - each Star's links must be contained in _links
			Iterator<Link> lIt = s.links();
			while(lIt.hasNext()) {
				
				Link l = lIt.next();
				ar.verify(_links.contains(l), "Star child's links are also part of this Constellation");
			}
			
			if(s.nimmed()) nimmedStars++;
		}
		// Invariant 2.1.e
		// - check that _numNimmed + nextMove.size() matches the number of nimmed stars
		ar.verify((_numNimmed + nextMove.size()) == nimmedStars, "Number of nimmed stars matches count");
		// Invariants for _links
		Iterator<Link> linkListIt = _links.iterator();
		while(linkListIt.hasNext()) {
			
			Link l = linkListIt.next();
			
			// Invariant 2.2.a
			// - every Link in _links should have this Constellation as the parent
			ar.verify(l.parent() == this, "Link child points back to correct Constellation parent");
			// Invariant 2.2.b
			// - each Link's stars must be contained in _stars
			ar.verify(_stars.contains(l.s1()) && _stars.contains(l.s2()), "Link child's stars are also part of this Constellation");
		}
		// Invariant 2.3
		// - Stars in nextMove must be contained in _stars
		Iterator<Star> starStackIt = nextMove.iterator();
		while(starStackIt.hasNext()) {
			
			Star s = starStackIt.next();
			ar.verify(_stars.contains(s), "Stars in Move must also be part of this Constellation");
		}
		Log.logMessage("--- End Deep Audit [" + auditName + "] ---");
		
		// Section 3 - Instantiation Audit
		if(scope < 3) return ar.falseInvariants();
		Log.logMessage("--- Begin Instantiation Audit [" + auditName + "] ---");
		// Invariant 3.1
		// - check that all Star children in _stars pass audit
		int childErrors = 0;
		starListIt = _stars.iterator();
		while(starListIt.hasNext()) {
			
			Star s = starListIt.next();
			childErrors += s.auditErrors(scope - 2);
		}
		ar.verify(childErrors == 0, "All star children passed audit");
		// Invariant 3.2
		// - check that all Link children in _links pass audit
		childErrors = 0;
		linkListIt = _links.iterator();
		while(linkListIt.hasNext()) {
			
			Link l = linkListIt.next();
			childErrors += l.auditErrors(scope - 2);
		}
		ar.verify(childErrors == 0, "All link children passed audit");
		// Invariant 3.3
		// - check that nextMove passes audit
		childErrors = nextMove.auditErrors(scope - 2);
		ar.verify(childErrors == 0, "nextMove passed audit");
		Log.logMessage("--- End Instantiation Audit [" + auditName + "] ---");
		
		
		return ar.falseInvariants();
	}

	/* Protected members */
	protected Move nextMove = new Move();
	
	/* Private members */
	private List<Star> _stars = new ArrayList<Star>();
	private List<Link> _links = new ArrayList<Link>();
	private int _numStars = 0; // Redundant: for auditing _stars
	private int _numLinks = 0; // Redundant: for auditing _links
	private int _numNimmed = 0;
	private Boolean _active = false;
	private gameMode _mode;
	private ConstelloServiceAsync _constelloSvc = GWT.create(ConstelloService.class);
	private Constello _parent = null;
	private int _numMoves = 0;
}
