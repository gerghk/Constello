package com.constello.client;

import com.constello.client.Constello.gameMode;

class Ursamajor extends Constellation {

	public Ursamajor(gameMode mode) {
		
		// Call parent's constructor
		super(400, 400, mode);
		
		// Add stars
		Star s1 = new Star(40, 120, 6);
		Star s2 = new Star(80, 155, 6);
		Star s3 = new Star(105, 185, 7);
		Star s4 = new Star(100, 230, 6);
		Star s5 = new Star(75, 265, 6);
		Star s6 = new Star(20, 250, 7);
		Star s7 = new Star(155, 280, 9);
		Star s8 = new Star(170, 230, 8);
		Star s9 = new Star(230, 253, 7);
		Star s10 = new Star(230, 296, 9);
		Star s11 = new Star(256, 203, 8);
		Star s12 = new Star(229, 137, 9);
		Star s13 = new Star(169, 71, 6);
		Star s14 = new Star(295, 43, 8);
		Star s15 = new Star(306, 29, 6);
		Star s16 = new Star(263, 328, 9);
		Star s17 = new Star(285, 358, 8);
		Star s18 = new Star(333, 371, 9);
		
		addStar(s1);
		addStar(s2);
		addStar(s3);
		addStar(s4);
		addStar(s5);
		addStar(s6);
		addStar(s7);
		addStar(s8);
		addStar(s9);
		addStar(s10);
		addStar(s11);
		addStar(s12);
		addStar(s13);
		addStar(s14);
		addStar(s15);
		addStar(s16);
		addStar(s17);
		addStar(s18);
		
		linkStars(s1, s2);
		linkStars(s2, s3);
		linkStars(s3, s4);
		linkStars(s4, s5);
		linkStars(s5, s6);
		linkStars(s5, s7);
		linkStars(s7, s8);
		linkStars(s8, s9);
		linkStars(s9, s10);
		linkStars(s10, s7);
		linkStars(s9, s11);
		linkStars(s11, s12);
		linkStars(s12, s13);
		linkStars(s12, s14);
		linkStars(s14, s15);
		linkStars(s10, s16);
		linkStars(s16, s17);
		linkStars(s17, s18);
	}

}
