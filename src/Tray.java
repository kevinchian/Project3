import java.util.*;

public class Tray {

	private static boolean iAmDebugging = true;
	
	private int trayHeight;
	private int trayWidth;
	private int[][] tray;
	private HashMap<Integer, Block> blocks;
	
	public Tray(int trayHeight, int trayWidth){
		this.trayHeight = trayHeight;
		this.trayWidth = trayWidth;
		tray = new int[trayHeight][trayWidth];
		blocks = new HashMap<Integer, Block>();
	}
	
	public void add(Block b){
		blocks.put(b.id(), b);
		for(int i=b.top(); i<b.bottom(); i++)
			for(int j=b.top(); j<b.bottom(); j++)
				tray[i][j] = b.id();
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
		 * 1. update block tests to reflect the new block class
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
		return null;
	}
	
	
	public String toString(){
		// josh
		
		String rtn="";
		
		
		for(int i = 0; i < tray.length; i++){
			for(int j = 0; j < tray[i].length; j++){
				if(tray[i][j]==-1){
					rtn+="|*|";
				}
				if(tray[i][j]==0){
					rtn+="|0|";
				}
				else{
					if(j==0 && j+1<tray[i].length && tray[i][j+1]>0) //first x and next is x
						rtn+="|2 ";
					else if(j!=0 && j+1<tray[i].length && tray[i][j+1]>0) //next is x
						rtn+="|2 ";
					else if(j!=0 && j+1<tray[i].length && tray[i][j-1]>0) //prev is x
						rtn+=" 2|";
					else
						rtn+="|2|";
						
				}
			}
			rtn+="\n";
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
	
	
	public static void main(String args[]){
		Tray t = new Tray(5, 5);
		Block b1 = new Block(0, 0, 2, 3);
		System.out.print(b1.toString());
		Block b2 = new Block(2, 2, 4, 4);
		System.out.print(b2.toString());
		System.out.print("\n");
		t.add(b1);
		t.add(b2);
		System.out.print(t.toString());
	
	}
	
	
	
}
