package com.game.main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH/12*9;
	public static final int SCALE = 3;
	
	public static final int REAL_WIDTH = WIDTH * SCALE;
	public static final int REAL_HEIGHT = HEIGHT * SCALE;
	
	public static final String TITLE = "Shooting Game";
	
	boolean running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	
	private Player player = null;
	private Controller controller = null;
	
	public void init() {
		requestFocus();
		
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try {
			spriteSheet = loader.loadImage("/spriteSheet.png");
		} catch ( IOException e) {
			e.printStackTrace();
		}
		
		addKeyListener(new KeyInput(this));
		
		player = new Player(200, 200, 5, this);
		controller = new Controller(this);
	}
	
	private synchronized void start() {
		if(running)
				return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop() {
		if(!running)
			return;
		
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.exit(1);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP) {
			player.foward();
		}
		
		if(key == KeyEvent.VK_DOWN) {
			player.backard();
		}
		
		if(key == KeyEvent.VK_LEFT) {
			player.left();
		}
		
		if(key == KeyEvent.VK_RIGHT) {
			player.right();
		}
		
		if(key == KeyEvent.VK_SPACE) {
			controller.shoot(player.getX(), player.getY());
			controller.addBullet(new Bullet(player.getX(), player.getY(), 10, this));
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP) {
			player.notFoward();
		}
		
		if(key == KeyEvent.VK_DOWN) {
			player.notBackward();
		}
		
		if(key == KeyEvent.VK_LEFT) {
			player.notLeft();
		}
		
		if(key == KeyEvent.VK_RIGHT) {
			player.notRight();
		}
		
		if(key == KeyEvent.VK_SPACE) {
			controller.notShoot();
		}
	}
	
	@Override
	public void run() {
		init();
		
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running) {
			long now = System.nanoTime();
			
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " Ticks, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		
		stop();
	}
	
	private void tick() {
		player.tick();
		controller.tick();
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		player.render(g);
		controller.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		JFrame frame = new JFrame(game.TITLE);
		
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}
	
	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}
}
