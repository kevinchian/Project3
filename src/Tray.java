import java.awt.Point;
import java.util.*;

public class Tray {

	private static boolean iAmDebugging = true;
	
	private int trayHeight;
	private int trayWidth;
	private int[][] tray;
	private HashMap<Integer, Block> blocks;
	private ArrayList<Point> emptySpace; // maybe we need this to keep track of empty space? tbd.
	
	public Tray(int trayHeight, int trayWidth){
		this.trayHeight = trayHeight;
		this.trayWidth = trayWidth;
		tray = new int[trayHeight][trayWidth];
		blocks = new HashMap<Integer, Block>();
	}
	
	// Sometimes the goal file can have out of bounds goals. We need to catch these in the Solver class and say no solution
	public void add(Block b){
		for(int i=b.top(); i<b.bottom(); i++)
			for(int j=b.left(); j<b.right(); j++)
				tray[i][j] = b.id();
		blocks.put(b.id(), b);
	}
	
	public void isOK(){
		int[][] comparision = new int[trayHeight][trayWidth];
		for (Block b: blocks.values())
			for (int i=b.top();i<b.bottom();i++)
				for (int j=b.left();i<b.right();j++) {
					if (comparision[i][j] != 0)
						throw new IllegalStateException("conflicting block positions");
					else comparision[i][j] = b.id();
				}
		if (!comparision.equals(tray))
			throw new IllegalStateException("inconsistency between blocks and tray");
		/* todos:
		 * 1. update block tests to reflect the new block class (done)
		 * make sure to test block.intersect(b) thoroughly
		 * 2. write a tray.clone() method for tray
		 * 3. write 2 different version of tray.moves(), one that finds
		 * next moves from a list of empty spaces, and one that finds
		 * next moves from a list of non-empty spaces. then benchmark them.
		 * you'll probably need to use the tray.clone() method. make sure to
		 * update both tray.tray and tray.blocks. make sure to also run the isOK
		 * method to validate the state of your future trays after creating them
		 * 4. benchmark the different tray.moves() methods and see which one
		 * is better
		 * 5. 
		 */
	}
	
	/*
	 * input:
	 * 5x5 tray, 2 blocks
	 * Tray.tray:
	 * 2 2 2 0 0
	 * 2 2 2 0 0
	 * 0 0 1 1 0
	 * 0 0 1 1 0
	 * -1 0 0 0 -1
	 * 
	 * block id 0 is empty space
	 * block id -1 is wildcard character (ask kevin)
	 * 
	 * output:
	 * +-----------+---+---+
	 * | x   x   x |   |   |
	 * |           +---+---+
	 * | x   x   x |   |   |
	 * +---+---+---+---+---+
	 * |   |   | x   x |   |
	 * +---+---+       +---+
	 * |   |   | x   x |   |
	 * +---+---+---+---+---+
	 * | * |   |   |   | * |
	 * +---+---+---+---+---+
	 */
	
	public ArrayList<Tray> moves(){
		Tray copy = this.clone();
		
	}
	
	
	public String toString(){
		// josh
		String rtn = "";
		
		while(blocks.iterator().hasNext()){
			rtn+= "BlockID: " + blocks.iterator().next() + " ";
		}
		for(int i = 0; i < tray.length; i++){
			for(int j = 0; j < tray[i].length; j++){
				System.out.print(tray[i][j] + " ");
			}
			System.out.println("");
		}	
		
		return rtn;
	}
	
	// Iterates through the tray to see if the equals are the same.
	public boolean equals(Tray compare){
		if(this.tray.length != compare.tray.length){
			return false;
		}
		if(this.tray[0].length != compare.tray[0].length){
			return false;
		}
		for(int x = 0; x < tray.length; x++){
			for(int y = 0; y<tray[x].length; y++){
				int thisID = this.tray[x][y];
				int otherID = compare.tray[x][y];
				if(otherID == -1 || thisID == -1){ 
					//skip to next iteration: wild card
				}
				else if(thisID == 0 && otherID != 0 || otherID == 0 && thisID != 0){
					return false;
				}
				else{
					Block b1 = blocks.get(thisID);
					Block b2 = compare.blocks.get(otherID);
					if(b1.top() != b2.top() || b1.bottom() != b2.bottom() || b1.left() != b2.left() 
							|| b1.right() != b2.right()){ // Maybe write a block equals method testing its coordinates but not hashcode.
						return false;
					}
				}
			}
		}
		return true;
	}
	
	// Potentially faster equals method
	// Work in progress
	public boolean equals2(Tray compare){
		if(this.tray.length != compare.tray.length){
			return false;
		}
		if(this.tray[0].length != compare.tray[0].length){
			return false;
		}
		for(int x = 0; x < tray.length; x++){
			while(true){
				
			}
		}
		return false;
	}
	
	public Tray clone(){
		Tray clone = new Tray(this.trayHeight, this.trayWidth);
		clone.blocks.putAll(blocks);
		clone.tray = tray.clone();
		return clone;
	}
}
