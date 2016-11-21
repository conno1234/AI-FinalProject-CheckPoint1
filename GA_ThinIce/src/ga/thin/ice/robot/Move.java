package ga.thin.ice.robot;

public enum Move {
	
	LEFT,
	RIGHT,
	UP,
	DOWN;

	public static Move getMove(int moveIndex) {
		switch(moveIndex) {
		case 0:
			return Move.LEFT;
		case 1:
			return Move.RIGHT;
		case 2:
			return Move.UP;
		default:
			return Move.DOWN;
		}
	}

	public static boolean opposites(Move m1, Move m2) {
		if(m1 == Move.UP && m2 == Move.DOWN) return true;
		if(m1 == Move.DOWN && m2 == Move.UP) return true;
		if(m1 == Move.LEFT && m2 == Move.RIGHT) return true;
		if(m1 == Move.RIGHT && m2 == Move.LEFT) return true;
		return false;
	}

}
