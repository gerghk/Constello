package com.constello.client;

import com.constello.client.Constello.gameMode;

class Hercules extends Constellation {

	public Hercules(gameMode mode) {
		
		// Call parent's constructor
		super(400, 400, mode);
		
		// Add stars
		Star s1 = new Star(143, 38, 6);
		Star s2 = new Star(99, 72, 8);
		Star s3 = new Star(122, 107, 7);
		Star s4 = new Star(137, 136, 6);
		Star s5 = new Star(127, 200, 9);
		Star s6 = new Star(48, 181, 7);
		Star s7 = new Star(81, 257, 6);
		Star s8 = new Star(182, 205, 7);
		Star s9 = new Star(190, 166, 8);
		Star s10 = new Star(285, 193, 9);
		Star s11 = new Star(318, 183, 6);
		Star s12 = new Star(210, 270, 9);
		Star s13 = new Star(182, 292, 6);
		Star s14 = new Star(150, 307, 7);
		Star s15 = new Star(126, 317, 6);
		Star s16 = new Star(116, 335, 9);
		Star s17 = new Star(290, 342, 8);
		
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
		
		linkStars(s1, s2);
		linkStars(s2, s3);
		linkStars(s3, s4);
		linkStars(s4, s5);
		linkStars(s5, s6);
		linkStars(s5, s7);
		linkStars(s5, s8);
		linkStars(s8, s9);
		linkStars(s9, s4);
		linkStars(s9, s10);
		linkStars(s10, s11);
		linkStars(s8, s12);
		linkStars(s12, s13);
		linkStars(s13, s14);
		linkStars(s14, s15);
		linkStars(s15, s16);
		linkStars(s12, s17);
	}

}
