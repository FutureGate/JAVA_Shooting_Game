package com.game.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player {
	private double x;
	private double y;
	private double speed;
	
	private double velX = 0;
	private double velY = 0;
	
	private BufferedImage player;
	
	private boolean fowardToggle = false;
	private boolean backwardToggle = false;
	private boolean leftToggle = false;
	private boolean rightToggle = false;

	private Game game = null;
	
	public Player(double x, double y, double speed, Game game) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.game = game;
		
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		player = ss.grabImage(1, 1, 32, 32);
	}
	
	public void tick() {
		if(fowardToggle) {
			y -= velY;
		} else if (backwardToggle) {
			y += velY;
		}
		
		if(leftToggle) {
			x -= velX;
		} else if (rightToggle) {
			x += velX;
		}
		
		if(x <= 0) {
			x = 0;
		} 
		
		if( x >= game.REAL_WIDTH - 24) {
			x = game.REAL_WIDTH - 24;
		}
		
		if(y <= 0) {
			y = 0;
		}
		
		if( y >= game.REAL_HEIGHT - 24) {
			y = game.REAL_HEIGHT - 24;
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(player, (int)x, (int)y, null);
	}
	
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	

	public void setVelX(double velX) {
		this.velX = velX;
	}
	
	public void setVelY(double velY) {
		this.velY = velY;
	}
	
	
	
	public void foward() {
		backwardToggle = false;
		fowardToggle = true;
		
		velY = speed;
	}
	
	public void backard() {
		backwardToggle = true;
		fowardToggle = false;	
		
		velY = speed;
	}
	
	public void left() {
		leftToggle = true;
		rightToggle = false;
		
		velX = speed;
	}
	
	public void right() {
		leftToggle = false;
		rightToggle = true;
		
		velX = speed;
	}
	
	public void notFoward() {
		fowardToggle = false;
	}
	
	public void notBackward() {
		backwardToggle = false;
	}
	
	public void notLeft() {
		leftToggle = false;
	}
	
	public void notRight() {
		rightToggle = false;
	}
}
