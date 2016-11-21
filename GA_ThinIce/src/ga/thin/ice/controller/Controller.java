package ga.thin.ice.controller;

import ga.thin.ice.Display;
import ga.thin.ice.GA.GA;
import ga.thin.ice.GA.Population;
import ga.thin.ice.level.Block;
import ga.thin.ice.level.Level;
import ga.thin.ice.level.Levels;
import ga.thin.ice.robot.Move;
import ga.thin.ice.robot.Robot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class Controller {
	
	public static Level level;
	private static Robot robot;
	private static int time, delay;
	private static Population pop;
	private static int generation, robotNum, popSize, bestFitness;
	private static ArrayList<ArrayList<Integer>> mutMethods;
	public static int mut = 5;
	private static int bestScore = 0;
	
	public static void init() {
		level = Levels.l1;
		time = 0;
		delay = 1;
		generation = 1;
		robotNum = 0;
		popSize = 100;
		pop = new Population(popSize, true, level);
		robot = new Robot(pop.getPath(robotNum).moves);
		mutMethods = new ArrayList<ArrayList<Integer>>();
		mutMethods.add(new ArrayList<Integer>());
		mutMethods.add(new ArrayList<Integer>());
		mutMethods.add(new ArrayList<Integer>());
		mutMethods.add(new ArrayList<Integer>());
	}
	
	public static void update() {
		time++;
		if(!(time % delay == 0)) return;
		
		Move m = robot.getAction();
		if(level.validMove(robot.getX(), robot.getY(), m)) {
			robot.move();
			if(level.getBlock(robot.getX(), robot.getY()) == Block.CRACKED) {
				robot.kill();
			}
			level.crack(robot.getX(), robot.getY());
		}
		else {
			robot.kill();
		}
		robot.advance();
		if(robot.getDone()) {
			pop.getPath(robotNum).fitness = level.getFitness();
			int score = level.getScore();
			if(bestScore < score) bestScore = score;
			robotNum++;
			bestFitness = pop.getFittest().fitness;
			if(bestFitness == level.getMaxFitness()) {
				delay = 150;
				robotNum--;
			}
			if(robotNum >= popSize) {
				pop = GA.evolvePopulation(pop);
				generation++;
				robotNum = 0;
//				if(generation == 1500) {
//					mutMethods.get(mut).add(bestFitness);
//					pop = new Population(popSize, true);
//					generation = 1;
//					bestFitness = 0;
//					time = 0;
//					mut++;
//					if(mut == mutMethods.size()) mut = 0;
//				}
			}
			robot = new Robot(pop.getPath(robotNum).moves);
			level.reset();
		}
	}
	
	public static void render(Graphics g) {
		// Draw a black background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Display.WIDTH, Display.HEIGHT);
		
		// Draw the grid that the robot will be playing on
		int border = 1;
		int squareSize = 70;
		int xOffset = 20;
		int yOffset = 30;
		int w = level.getWidth();
		int h = level.getHeight();
		squareSize = (int) (500.0 / Math.max(w, h) - border);
		Color c;
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				// Calculate what type of block we are drawing
				Block block = level.getBlock(x, y);
				if(block == Block.WALL) c = new Color(0, 0, 175);
				else if(block == Block.CRACKED) c = Color.GRAY;
				else c = Color.WHITE;
				// Draw the block
				g.setColor(c);
				g.fillRect(xOffset + x * (squareSize+border), yOffset + y * (squareSize+border), squareSize, squareSize);
			}
		}
		
		// Now rendering robot
		g.setColor(Color.GREEN);
		g.fillRect(xOffset + robot.getX() * (squareSize+border) + squareSize / 4,
				yOffset + robot.getY() * (squareSize+border) + squareSize / 4, 
				squareSize / 2, squareSize / 2);
		
		// Display helpful information
		int xData = 550;
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", 0, 22));
		g.drawString("Generation: " + generation, xData, 70);
		g.drawString("Robot: " + (robotNum+1) + "/" + popSize, xData, 110);
		g.drawString("Current Score: " + level.getScore(), xData, 150);
		g.drawString("Best Score: " + bestScore, xData, 190);
		g.drawString("Current Fitness: " + level.getFitness(), xData, 230);
		g.drawString("Best Fitness: " + bestFitness, xData, 270);
		g.drawString("Max Fitness: " + level.getMaxFitness(), xData, 310);
		for(int j = 0; j < mutMethods.size(); j++) {
			ArrayList<Integer> list = mutMethods.get(j);
			double sum = 0;
			for(Integer i : list) {
				sum += i;
			}
			g.drawString("Mut" + (j+1) + " Size: " + list.size() + " Ave: " + sum / list.size(), xData, 350 + 40 * j);
		}
	}

}
