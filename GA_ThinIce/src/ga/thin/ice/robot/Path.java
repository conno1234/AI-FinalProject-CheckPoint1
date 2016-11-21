package ga.thin.ice.robot;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import ga.thin.ice.controller.Controller;
import ga.thin.ice.level.Level;

public class Path {
	
	private static Random rand = new Random();
	
	public Move[] moves;
	public int fitness;
	public int length;
	
	public Path() {
		moves = new Move[Controller.level.getHeight() * Controller.level.getWidth()];
		for(int i = 0; i < moves.length; i++) {
			moves[i] = null;
		}
		length = moves.length;
		fitness = 0;
	}

	public void generateIndividual(Level level) {
		moves = newPathFrom(0, moves, length, level);
	}
	
	public static Move[] newPathFrom(int index, Move[] moves, int length, Level level) {
		int x = 0;
		int y = 0;
		ArrayList<Point> visited = new ArrayList<Point>();
		visited.add(new Point(x, y));
		for(int i = 0; i < index; i++) {
			Move m = moves[i];
			Point p = applyMove(x, y, m);
			x = p.x;
			y = p.y;
			visited.add(new Point(x, y));
		}
		for(int i = index; i < moves.length; i++) {
			ArrayList<Move>  possibleMoves = level.getValidMoves(x, y);
			ArrayList<Move> validMoves = new ArrayList<Move>();
			for(Move m : possibleMoves) {
				Point p = applyMove(x, y, m);
				if(!visited(visited, p.x, p.y)) validMoves.add(m);
			}
			Move m;
			if(validMoves.size() == 0) {
				for(int j = i; j < length; j++) {
					moves[j] = Move.getMove(rand.nextInt(4));
				}
				i = length;
				continue;
			}
			m = validMoves.get(rand.nextInt(validMoves.size()));
			moves[i] = m;
			Point nextPoint = applyMove(x, y, m);
			x = nextPoint.x;
			y = nextPoint.y;
			visited.add(nextPoint);
		}
		return moves;
	}
	
	public static Point applyMove(int x, int y, Move m) {
		if(m == Move.UP) return new Point(x, y-1);
		else if(m == Move.DOWN) return new Point(x, y+1);
		else if(m == Move.LEFT) return new Point(x-1, y);
		else return new Point(x+1, y);
	}
	
	public static boolean visited(ArrayList<Point> visited, int x, int y) {
		for(Point p : visited) {
			if(p.x == x && p.y == y) return true;
		}
		return false;
	}
	
	
	
}
