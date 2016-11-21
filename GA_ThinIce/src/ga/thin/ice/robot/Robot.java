package ga.thin.ice.robot;

public class Robot {
	
	private int x, y;
	private boolean done;
	public Move[] actions;
	private int currentAction;
	
	public Robot(Move[] actions) {
		x = y = 0;
		done = false;
		currentAction = 0;
		this.actions = actions;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Move getAction() {
		return actions[currentAction];
	}
	
	public void move() {
		Move m = getAction();
		if(m == Move.RIGHT) x++; 
		else if(m == Move.LEFT) x--; 
		else if(m == Move.UP) y--; 
		else if(m == Move.DOWN) y++; 
	}
	
	public void kill() {
		done = true;
	}
	
	public void advance() {
		currentAction++;
		if(currentAction >= actions.length) {
			done = true;
		}
	}
	
	public boolean getDone() {
		return done;
	}

}
