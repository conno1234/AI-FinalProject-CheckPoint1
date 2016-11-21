package ga.thin.ice.level;

import java.awt.Point;
import java.util.ArrayList;

import ga.thin.ice.robot.Move;

public class Level {
	
//	private static int WIDTH, HEIGHT;
	
	private int width, height;
	public Block[][] grid;
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new Block[width][height];
		fillGrid();
	}
	
	public int getMaxFitness() {
		int fitness = 0;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(grid[x][y] != Block.WALL) fitness++;
			}
		}
		return fitness;
	}
	
	public ArrayList<Move> getValidMoves(int x, int y) {
		ArrayList<Move> moves = new ArrayList<Move>();
		if(!(x >= 0 && x < width && y >= 0 && y < height)) return moves;
		if(x-1 >= 0 && grid[x-1][y] != Block.WALL) moves.add(Move.LEFT);
		if(x+1 < width && grid[x+1][y] != Block.WALL) moves.add(Move.RIGHT);
		if(y-1 >= 0 && grid[x][y-1] != Block.WALL) moves.add(Move.UP);
		if(y+1 < height && grid[x][y+1] != Block.WALL) moves.add(Move.DOWN);
		return moves;
	}
	
	public void crack(int x, int y) {
		//First, check that the (x, y) point is in our grid
		if(!(x >= 0 && x < width && y >= 0 && y < height)) return;
		
		grid[x][y] = Block.CRACKED;
	}
	
	public boolean validMove(int x, int y, Move m) {
		//First, check that the (x, y) point is in our grid
		if(!(x >= 0 && x < width && y >= 0 && y < height)) return false;
		if(m == Move.LEFT) {
			if(x-1 >= 0) {
				if(grid[x-1][y] == Block.WALL) return false;
			}
			else return false;
		}
		else if(m == Move.RIGHT) {
			if(x+1 < width) {
				if(grid[x+1][y] == Block.WALL) return false;
			}
			else return false;
		}
		else if(m == Move.UP) {
			if(y-1 >= 0) {
				if(grid[x][y-1] == Block.WALL) return false;
			}
			else return false;
		}
		else if(m == Move.DOWN) {
			if(y+1 < height) {
				if(grid[x][y+1] == Block.WALL) return false;
			}
			else return false;
		}
		return true;	
	}
	
	public int getScore() {
		int score = 0;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(grid[x][y] == Block.CRACKED) score++;
			}
		}
		return score;
	}
	
	public int getFitness() {
		ArrayList<ArrayList<Point>> chunks = new ArrayList<ArrayList<Point>>();
		int fitness = 0;
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(grid[x][y] == Block.CRACKED) fitness++;
				else if(grid[x][y] == Block.EMPTY) {
					boolean used = false;
					for(ArrayList<Point> points : chunks) {
						for(Point p : points) {
							if(p.x == x && p.y == y) used = true;
						}
					}
					if(!used) {
//						ArrayList<Point> points = new ArrayList<Point>();
//						points.add(new Point(x, y));
//						for(int i = 0; i < points.size(); i++) {
//							Point p = points.get(i);
//							if(p.x+1 < width && grid[p.x+1][p.y] == Block.EMPTY && !inUse(points, new Point(p.x+1, y))) points.add(new Point(p.x+1, p.y));
//							if(p.x-1 >= 0 && grid[p.x-1][p.y] == Block.EMPTY && !inUse(points, new Point(p.x-1, y))) points.add(new Point(p.x-1, p.y));
//							if(p.y+1 < height && grid[p.x][p.y+1] == Block.EMPTY && !inUse(points, new Point(p.x, y+1))) points.add(new Point(p.x, p.y+1));
//							if(p.y-1 >= 0 && grid[p.x][p.y-1] == Block.EMPTY && !inUse(points, new Point(p.x, y-1))) points.add(new Point(p.x, p.y-1));
//						}
//						chunks.add(points);
					}
				}
			}
		}
		return fitness - chunks.size() * 10;
	}
	
	@SuppressWarnings("unused")
	private boolean inUse(ArrayList<Point> points, Point point) {
		for(Point p : points) {
			if(p.x == point.x && p.y == point.y) return true;
		}
		return false;
	}
	
	private void fillGrid() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(x == 0 && y == 0) {
					grid[x][y] = Block.CRACKED;
				}
				else {
					grid[x][y] = Block.EMPTY;
				}
			}			
		}
	}
	
	public Block getBlock(int x, int y) {
		if(x >=0 && x < width) {
			if(y >= 0 && y < height) {
				return grid[x][y];
			}
		}
		return null;
	}
	
	public void reset() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				Block block = grid[x][y];
				if(block == Block.CRACKED && !(x == 0 && y == 0)) {
					grid[x][y] = Block.EMPTY;
				}
			}			
		}
	}
	
	public void setWall(int x, int y) {
		if(x >=0 && x < width) {
			if(y >= 0 && y < height) {
				grid[x][y] = Block.WALL;
			}
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}
