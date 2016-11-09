package com.game.main;

import java.awt.Graphics;
import java.util.ArrayList;

public class Controller {
	private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	private Game game = null;
	
	private Bullet bullet = null;
	private boolean isShoot = false;
	
	private double x;
	private double y;
	
	public Controller(Game game) {
		this.game = game;
	}
	
	public void tick() {
		if(isShoot) {
			
		}
		
		for(int i=0; i<bulletList.size(); i++) {
			bullet = bulletList.get(i);
			bullet.tick();
			
			if(bullet.getY() <= 0) {
				removeBullet(bullet);
			}
		}
	}
	
	public void render(Graphics g) {
		for(int i=0; i<bulletList.size(); i++) {
			bullet = bulletList.get(i);
			
			bullet.render(g);
		}
	}
	
	public void addBullet(Bullet bullet) {
		bulletList.add(bullet);
	}
	
	public void removeBullet(Bullet bullet) {
		bulletList.remove(bullet);
	}
	
	public void shoot(double x, double y) {
		this.x = x;
		this.y = y;
		
		isShoot = true;
	}
	
	public void notShoot() {
		isShoot = false;
	}
}
