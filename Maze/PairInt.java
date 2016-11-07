package Maze;
//Taylor He
//I pledge my honor that I have abided by the Stevens Honor System.
public class PairInt {
	int x, y;
	
	public PairInt(int x, int y){
		this.x = x;
		this.y = y;
	}
	/**
	 * Gets x value of PairInt
	 * @return x
	 */
	public int getX(){
		return this.x;
	}
	/**
	 * Gets y value of PairInt
	 * @return y
	 */
	public int getY(){
		return this.y;
	}
	/**
	 * Sets x value of PairInt
	 * @return PairInt(x, y)
	 */
	public PairInt setX(int x){
		this.x = x;
		return this;
	}
	/**
	 * Sets y value of PairInt
	 * @return PairInt(x,y)
	 */
	public PairInt setY(int y){
		this.y = y;
		return this;
	}
	/**
	 * Checks if PairInts are equal
	 * @return boolean
	 */
	public boolean equals(Object p){
		try{
			PairInt compare = (PairInt) p;
			return this.x == compare.x && this.y == compare.y;
			}
		catch(ClassCastException s){
			System.out.println("Can't cast argument to PairInt");
			return false;
		}
		
	}
	/**
	 * Returns a string of a PairInt
	 * @return String
	 */
	public String toString(){
		return "(" + this.x + "," + this.y + ")";
	}
	/**
	 * Copies a PairInt
	 * @return PairInt
	 */
	public PairInt copy(){
		return new PairInt(this.x, this.y);
	}
}
