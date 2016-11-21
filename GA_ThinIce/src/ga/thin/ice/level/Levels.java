package ga.thin.ice.level;

public class Levels {
	
	public static Level l1, l2, l3, l4, l5, l6;
	
	public static void init() {
		l1 = new Level(8, 5);
		l1.setWall(2, 1);
		l1.setWall(2, 2);
		l1.setWall(3, 1);
		l1.setWall(3, 2);
		
		l2 = new Level(40, 50);
		boolean left = false;
		for(int y = 1; y < 50; y+=2) {
			for(int x = (left ? 1 : 0); x < (left ? 40 : 39); x++) {
				l2.setWall(x, y);
			}
			left = !left;
		}
		
		l3 = new Level(8, 8);
		l3.setWall(4, 5);
		l3.setWall(4, 6);
		l3.setWall(4, 7);
		l3.setWall(1, 2);
		l3.setWall(2, 2);
		l3.setWall(3, 2);
		l3.setWall(0, 4);
		l3.setWall(1, 4);
		l3.setWall(0, 5);
		l3.setWall(1, 5);
		l3.setWall(3, 3);
		
		l4 = new Level(8, 8);

		l5 = new Level(8, 8);
		l5.setWall(3, 7);
		l5.setWall(3, 6);
		l5.setWall(3, 5);
		l5.setWall(2, 7);
		l5.setWall(0, 3);
		l5.setWall(0, 4);
		l5.setWall(2, 0);
		l5.setWall(2, 1);
		l5.setWall(5, 1);
		l5.setWall(5, 2);
		l5.setWall(6, 2);
		l5.setWall(7, 2);
		
		l6 = new Level(2, 30);
	}

}
