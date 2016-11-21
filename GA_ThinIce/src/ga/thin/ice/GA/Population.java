package ga.thin.ice.GA;

import ga.thin.ice.level.Level;
import ga.thin.ice.robot.Path;

public class Population {
	
	Path[] paths;
	public Level level;
	
	public Population(int populationSize, boolean initialise, Level level) {
		this.level = level;
		paths = new Path[populationSize];
		if(initialise) {
			for(int i = 0; i < populationSize; i++) {
				Path path = new Path();
				path.generateIndividual(level);
				savePath(i, path);
			}
		}
	}
	
	public Population(int populationSize, boolean initialise) {
		level = null;
		paths = new Path[populationSize];
		if(initialise) {
			for(int i = 0; i < populationSize; i++) {
				Path path = new Path();
				path.generateIndividual(level);
				savePath(i, path);
			}
		}
	}
	
	public void savePath(int index, Path path) {
		paths[index] = path;
	}
	
	public Path getPath(int index) {
		return paths[index];
	}
	
	public Path getFittest() {
		Path fittest = paths[0];
		for(int i = 1; i < populationSize(); i++) {
			if(fittest.fitness < getPath(i).fitness) {
				fittest = getPath(i);
			}
		}
		return fittest;
	}

	public int populationSize() {
		return paths.length;
	}
	
}
