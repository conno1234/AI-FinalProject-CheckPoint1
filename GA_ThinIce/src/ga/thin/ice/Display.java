package ga.thin.ice;


import ga.thin.ice.controller.Controller;
import ga.thin.ice.level.Levels;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private Thread thread;
	private JFrame frame;
	private static String title = "Thin Ice";
	public static final int WIDTH = 850;
	public static final int HEIGHT = 600;
	public static boolean running = false;
	Random rand = new Random();
	
	public Display() {
		frame = new JFrame();
		
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
	}
	
	public static void main(String[]args) {
		Display game = new Display();
		game.frame.setResizable(false);
		game.frame.setTitle(title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 1000;
		double delta = 0;
		int frames = 0;
		@SuppressWarnings("unused")
		int updates = 0;
		requestFocus();
		
		Levels.init();
		Controller.init();
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Controller.render(g);
		g.dispose();
		bs.show();
	}

	public void update() {
		Controller.update();
	}

}
