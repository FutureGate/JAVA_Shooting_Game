package com.game.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Bullet {
	private double x;
	private double y;
	private double speed;
	
	private Game game;
	
	private BufferedImage image = null;
	
	public Bullet(double x, double y, double speed, Game game) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.game = game;
	
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		
		image = ss.grabImage(2, 1, 32, 32);
	}

	public void tick() {
		y -= speed;
	}

	public void render(Graphics g) {
		g.drawImage(image, (int)x, (int)y, null);
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
}
