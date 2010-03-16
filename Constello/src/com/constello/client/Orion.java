package com.constello.client;

import com.constello.client.Constello.gameMode;

class Orion extends Constellation {

	public Orion(gameMode mode) {
		
		// Call parent's constructor
		super(400, 400, mode);
		
		// Add stars
		Star s1 = new Star(110, 30, 10);
		Star s2 = new Star(155, 75, 8);
		Star s3 = new Star(220, 80, 10);
		Star s4 = new Star(240, 70, 8);
		Star s5 = new Star(280, 80, 7);
		Star s6 = new Star(70, 85, 10);
		Star s7 = new Star(150, 110, 10);
		Star s8 = new Star(275, 90, 7);
		Star s9 = new Star(290, 130, 7);
		Star s10 = new Star(300, 115, 7);
		Star s11 = new Star(60, 125, 15);
		Star s12 = new Star(100, 130, 10);
		Star s13 = new Star(145, 135, 9);
		Star s14 = new Star(180, 160, 10);
		Star s15 = new Star(230, 130, 10);
		Star s16 = new Star(140, 200, 8);
		Star s17 = new Star(145, 220, 7);
		Star s18 = new Star(170, 240, 7);
		Star s19 = new Star(185, 245, 7);
		Star s20 = new Star(200, 250, 6);
		Star s21 = new Star(235, 250, 6);
		
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
		addStar(s21);
		
		linkStars(s1, s2);
		linkStars(s2, s3);
		linkStars(s3, s4);
		linkStars(s4, s5);
		linkStars(s6, s1);
		linkStars(s7, s2);
		linkStars(s15, s3);
		linkStars(s8, s4);
		linkStars(s8, s9);
		linkStars(s10, s5);
		linkStars(s11, s6);
		linkStars(s11, s12);
		linkStars(s12, s13);
		linkStars(s13, s7);
		linkStars(s13, s14);
		linkStars(s14, s15);
		linkStars(s14, s16);
		linkStars(s16, s17);
		linkStars(s17, s18);
		linkStars(s18, s19);
		linkStars(s19, s20);
		linkStars(s20, s21);
	}

}
