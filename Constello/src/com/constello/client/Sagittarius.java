package com.constello.client;

import com.constello.client.Constello.gameMode;

class Sagittarius extends Constellation {

	public Sagittarius(gameMode mode) {
		
		// Call parent's constructor
		super(400, 400, mode);
		
		// Add stars
		Star s1 = new Star(128, 29, 6);
		Star s2 = new Star(128, 100, 9);
		Star s3 = new Star(130, 142, 8);
		Star s4 = new Star(169, 62, 6);
		Star s5 = new Star(143, 98, 6);
		Star s6 = new Star(142, 135, 7);
		Star s7 = new Star(148, 176, 9);
		Star s8 = new Star(100, 239, 8);
		Star s9 = new Star(177, 293, 8);
		Star s10 = new Star(203, 222, 9);
		Star s11 = new Star(228, 222, 8);
		Star s12 = new Star(265, 215, 6);
		Star s13 = new Star(213, 248, 6);
		Star s14 = new Star(216, 260, 6);
		Star s15 = new Star(259, 261, 7);
		Star s16 = new Star(284, 263, 6);
		Star s17 = new Star(191, 320, 7);
		Star s18 = new Star(199, 335, 9);
		Star s19 = new Star(234, 375, 6);
		Star s20 = new Star(275, 358, 8);
		
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
		addStar(s19);
		addStar(s20);
		
		linkStars(s1, s2);
		linkStars(s2, s3);
		linkStars(s3, s7);
		linkStars(s4, s5);
		linkStars(s5, s6);
		linkStars(s6, s7);
		linkStars(s7, s8);
		linkStars(s8, s9);
		linkStars(s9, s10);
		linkStars(s10, s7);
		linkStars(s10, s11);
		linkStars(s11, s12);
		linkStars(s10, s13);
		linkStars(s13, s14);
		linkStars(s14, s15);
		linkStars(s15, s16);
		linkStars(s9, s17);
		linkStars(s17, s18);
		linkStars(s18, s19);
		linkStars(s19, s20);
	}

}
