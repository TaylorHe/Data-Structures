//Taylor He
//I pledge my honor that I have abided by the Stevens Honor System.
package Maze;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Class that solves maze problems with backtracking.
 **/
public class Maze implements GridColors {

	/** The maze */
	private TwoDimGrid maze;

	public Maze(TwoDimGrid m) {
		maze = m;
	}

	/** Wrapper method. */
	public boolean findMazePath(){
		return findMazePath(0,0);
	}


	/**
	 * Attempts to find a path through point (x, y).
	 * @pre Possible path cells are in BACKGROUND color;
	 *      barrier cells are in ABNORMAL color.
	 * @post If a path is found, all cells on it are set to the
	 *       PATH color; all cells that were visited but are
	 *       not on the path are in the TEMPORARY color.
	 * @param x The x-coordinate of current point
	 * @param y The y-coordinate of current point
	 * @return If a path through (x, y) is found, true;
	 *         otherwise, false
	 */
	public boolean findMazePath(int x, int y) {
		//If outside made, return false
		if (x < 0 || x >= maze.getNCols()  || y < 0 || y >= maze.getNRows()) { 
			return false;
		}else if (x == maze.getNCols()-1 && y == maze.getNRows()-1){ // If at end, return true
			maze.recolor(x, y, PATH);
			return true;
		}else if (!maze.getColor(x, y).equals(ABNORMAL)) {      	// If not marked path, return false
			return false;
		} else { 
			maze.recolor(x, y, PATH);
			//recursion
			if(findMazePath(x+1, y) || findMazePath(x, y+1)|| findMazePath(x-1, y) || findMazePath(x, y-1) ){
				return true;
			}else{
				maze.recolor(x,  y, TEMPORARY);
				return false;
			}
		}
	}


	/**
	 * Attempts to find all paths
	 * @pre Possible path cells are in BACKGROUND color;
	 * 		Barrier cells are in ABNORMAL color.
	 * @post If a path is found, all cells on it are set to the PATH color;
	 * 		 All cells that were visited but are not on the path are in the
	 * 		 TEMPORARY color.
	 * @param x
	 * @param y
	 * @return ArrayList<ArrayList<PairInt>> of all possible paths,
	 * 		   If no path, returns empty list []
	 */
	public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y){

		if (x < 0 || x >= maze.getNCols() || y < 0 || y >= maze.getNRows()) { 	//Check out of bounds
			return new ArrayList<ArrayList<PairInt>>();
		}else if (x == maze.getNCols() - 1 && y == maze.getNRows() - 1){ 	//Check if end
			//
			ArrayList<ArrayList<PairInt>> allMazes = new ArrayList<ArrayList<PairInt>>();	
			ArrayList innerList = new ArrayList();
			innerList.add(0, new PairInt(x,y));
			allMazes.add(innerList);
			return allMazes;
		}else if (!maze.getColor(x, y).equals(ABNORMAL)) {	//Check if not red				
			return new ArrayList<ArrayList<PairInt>>();
		} else {  //recursive case

			ArrayList<ArrayList<PairInt>> allMazes = new ArrayList<ArrayList<PairInt>>();

			//set to black
			maze.recolor(x,y, TEMPORARY);

			//recursion
			ArrayList<ArrayList<PairInt>> up = findAllMazePaths(x, y-1);
			ArrayList<ArrayList<PairInt>> down = findAllMazePaths(x, y+1);
			ArrayList<ArrayList<PairInt>> left = findAllMazePaths(x-1, y);
			ArrayList<ArrayList<PairInt>> right = findAllMazePaths(x+1, y);

			//add to 
			for(int i = 0; i < up.size(); i++){
				up.get(i).add(0, new PairInt(x,y));
				allMazes.add(up.get(i));
			}
			for(int i = 0; i < down.size(); i++){
				down.get(i).add(0, new PairInt(x,y));
				allMazes.add(down.get(i));
			}
			for(int i = 0; i < left.size(); i++){
				left.get(i).add(0, new PairInt(x,y));
				allMazes.add(left.get(i));
			}
			for(int i = 0; i < right.size(); i++){
				right.get(i).add(0, new PairInt(x,y));
				allMazes.add(right.get(i));
			}

			//reset to red
			maze.recolor(x, y, NON_BACKGROUND);

			return allMazes;
		}

	}

	
	public ArrayList<PairInt> findMazePathMin(int x, int y) {

		if(!findMazePath()) return null;
		///clearPath necessary for findAllMazePaths() to work
		clearPath();
		
		ArrayList<ArrayList<PairInt>> all = new ArrayList<ArrayList<PairInt>>();
		all = this.findAllMazePaths(0, 0);
		ArrayList<PairInt> retVal;

		//assume first path is smallest
		retVal = all.get(0);

		/*Check through all ArrayLists to see if their size
		 * is smaller than the first. If so, then replace the first
		 * as the smallest with that index.
		 */
		for(int i = 0; i < all.size()-1; i++){
			if(retVal.size() < all.get(i).size()){
				retVal = all.get(i);
			}
		}
		return retVal;
	}
	
	// Resets all TEMPORARY and PATH colors back to BACKGROUND
	public void clearPath(){
		for(int i = 0; i < maze.getNCols(); i++){
			for(int j = 0; j < maze.getNRows(); j++){
				if(maze.getColor(i, j).equals(PATH) || maze.getColor(i, j).equals(TEMPORARY)){
					maze.recolor(i, j, NON_BACKGROUND);
				}
			}
		}
	}

	public void resetTemp() {
		maze.recolor(TEMPORARY, BACKGROUND);
	}

	public void restore() {
		resetTemp();
		maze.recolor(PATH, BACKGROUND);
		maze.recolor(NON_BACKGROUND, BACKGROUND);
	}
}
