package com.constello.client;

import com.constello.client.Constello.gameMode;
import com.google.gwt.junit.client.GWTTestCase;

public class ConstellationTest extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.constello.Constello";
	}

	public void testAddStar() {
		
		Constellation cn = new Constellation(400, 400, gameMode.SOLO);
		
		// Try adding a star to the Constellation
		Star s1 = new Star(20, 20, 20);
		s1.parentIs(cn);
		cn.addStar(s1);
		
		// Check that it passes audit
		int errors = cn.auditErrors(4);
		assert(errors == 0);
	}
	
	public void testLinkStars() {
		
		Constellation cn = new Constellation(400, 400, gameMode.SOLO);
		
		// Create two stars
		Star s1 = new Star(40, 40, 20);
		s1.parentIs(cn);
		Star s2 = new Star(80, 120, 15);
		s2.parentIs(cn);
		cn.addStar(s1);
		cn.addStar(s2);
		
		// Link the stars
		cn.linkStars(s1, s2);
		
		// Check that it passes audit
		int errors = cn.auditErrors(4);
		assert(errors == 0);
	}
	
	public void testMakeMove() {
		
		Constellation cn = new Constellation(400, 400, gameMode.SOLO);
		
		// Create some stars
		Star s1 = new Star(20, 30, 15);
		s1.parentIs(cn);
		Star s2 = new Star(40, 40, 10);
		s2.parentIs(cn);
		Star s3 = new Star(30, 30, 10);
		s3.parentIs(cn);
		cn.addStar(s1);
		cn.addStar(s2);
		cn.addStar(s3);
		
		// Link the stars
		cn.linkStars(s1, s2);
		cn.linkStars(s2, s3);
		
		// Add the stars to nextMove
		s1.nimmedIs(true);
		s2.nimmedIs(true);
		cn.nextMove.push(s1);
		cn.nextMove.push(s2);
		
		// Try to make the move
		Boolean result = cn.makeMove();
		
		// Check that it passes audit
		int errors = cn.auditErrors(4);
		assert(errors == 0 && result == false);
	}
	
	public void testHercules() {
		
		// Create the Hercules level
		Constellation cn = new Hercules(gameMode.SOLO);
		
		// Check that it passes audit
		int errors = cn.auditErrors(4);
		assert(errors == 0);
	}
	
	public void testOrion() {
		
		// Create the Orion level
		Constellation cn = new Orion(gameMode.SOLO);
		
		// Check that it passes audit
		int errors = cn.auditErrors(4);
		assert(errors == 0);
	}
	
	public void testSagittarius() {
		
		// Create the Sagittarius level
		Constellation cn = new Sagittarius(gameMode.SOLO);
		
		// Check that it passes audit
		int errors = cn.auditErrors(4);
		assert(errors == 0);
	}
	
	public void testUrsaMajor() {
		
		// Create the Ursa Major level
		Constellation cn = new Ursamajor(gameMode.SOLO);
		
		// Check that it passes audit
		int errors = cn.auditErrors(4);
		assert(errors == 0);
	}
}
