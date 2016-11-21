package ga.thin.ice.GA;

import ga.thin.ice.level.Level;
import ga.thin.ice.robot.Path;

import java.util.Random;

public class GA {
	
//	private static final double MUTATION_RATE = 0.15;
	private static final int TOURNAMENT_SIZE = 5;
	private static final boolean ELITISM = true; // Keep the fittest

	public static Population evolvePopulation(Population pop) {
		Population newPop = new Population(pop.populationSize(), false, pop.level);
		
		int elitismOffset = 0;
		if(ELITISM) {
			elitismOffset = 1;
			newPop.savePath(0, pop.getFittest());
		}
		
		for(int i = elitismOffset; i < newPop.populationSize(); i++) {
			Path parent1 = tournamentSelection(pop);
			Path parent2 = tournamentSelection(pop);
			Path child = crossover(parent1, parent2);
			newPop.savePath(i, child);
		}
		
		for(int i = elitismOffset; i < newPop.populationSize(); i++) {
			mutate(newPop.getPath(i), pop.level);
		}
		
		return newPop;
	}
	
	private static Path crossover(Path parent1, Path parent2) {
		Path child = new Path();
		int startPos = (int) (Math.random() * parent1.length);
		int endPos = (int) (Math.random() * parent1.length);
		
		for(int i = 0; i < child.length; i++) {
			if(startPos < endPos && i > startPos && i < endPos) {
				child.moves[i] = parent1.moves[i];
			}
			else if(startPos > endPos) {
				if(i > startPos || i < endPos) {
					child.moves[i] = parent1.moves[i];
				}
			}
		}
		
		for(int i = 0; i < parent2.length; i++) {
			if(child.moves[i] == null) {
				child.moves[i] = parent2.moves[i];
			}
		}
		for(int i = 0; i < child.length; i++) {
			child.moves[i] = parent1.moves[i];
		}
		return child;
	}

	static Random rand = new Random();
	private static void mutate(Path path, Level level) {
//		if(Math.random() < MUTATION_RATE) {
			int index = rand.nextInt(path.length);
			path.moves = Path.newPathFrom(index, path.moves, path.length, level);
//		}
		
		
		
////		if(Controller.mut == 0) {
//			for(int i = 0; i < path.length; i++) {
//				if(Math.random() < MUTATION_RATE) {
//					int index = rand.nextInt(path.length);
//					path.moves[index] = Move.getMove(rand.nextInt(4));
//				}
//			}
////		}
////		else if(Controller.mut == 1) {
//			for(int i = 0; i < path.length; i++) {
//				if(Math.random() < MUTATION_RATE) {
//					int index = rand.nextInt(path.length);
//					int index2 = rand.nextInt(path.length);
//					Move temp = path.moves[index];
//					path.moves[index] = path.moves[index2];
//					path.moves[index2] = temp;
//				}
//			}
////		}
////		else if(Controller.mut == 2){
//			for(int i = 0; i < path.length; i++) {
//				if(Math.random() < MUTATION_RATE) {
//					int index = rand.nextInt(path.length);
//					Move temp = path.moves[index];
//					for(int j = index; j < path.length - 1; j++) {
//						path.moves[j] = path.moves[j+1];
//					}
//					path.moves[path.length-1] = temp;
//				}
//			}
////		}
////		else if(Controller.mut == 3){
//			for(int i = 0; i < path.length; i++) {
//				if(Math.random() < MUTATION_RATE) {
//					int index = rand.nextInt(path.length-1);
//					Move temp = path.moves[index];
//					path.moves[index] = path.moves[index+1];
//					path.moves[index+1] = temp;
//				}
//			}
////		}
////		else {
//			if(Math.random() < MUTATION_RATE) {
//				Move lastM = path.moves[0];
//				for(int i = 1; i < path.length; i++) {
//					Move currentM = path.moves[i];
//					if(Move.opposites(lastM, currentM)) {
//						Move newM = currentM;
//						while(newM == currentM) {
//							newM = Move.getMove(rand.nextInt(4));
//						}
//						currentM = newM;
//					}
//					lastM = currentM;
//				}
//			}
////		}
	}
	
	private static Path tournamentSelection(Population pop) {
		Population tournament = new Population(TOURNAMENT_SIZE, false);
		
		for(int i = 0; i < TOURNAMENT_SIZE; i++) {
			int randomIndex = (int) (Math.random() * pop.populationSize());
			tournament.savePath(i, pop.getPath(randomIndex));
		}
		
		return tournament.getFittest();
	}
	
}
