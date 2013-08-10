import java.util.*;

public class Tray {

	private static boolean iAmDebugging = true;
	
	private int trayHeight;
	private int trayWidth;
	private int[][] tray;
	private HashSet<Block> blocks;
	
	public Tray(int height, int width){
		this.trayHeight = height;
		this.trayWidth = width;
		tray = new int[height][width];
		blocks = new HashSet<Block>();
	}
	
	public void add(Block b){
		blocks.add(b);
		for(int i=b.top(); i<b.bottom(); i++)
			for(int j=b.top(); j<b.bottom(); j++)
				tray[i][j] = b.id();
	}
	
	public void isOK(){
		int[][] comparision = new int[trayHeight][trayWidth];
		for (Block b: blocks)
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
	
	public boolean equals(Object o){
		// kevin
		// make sure to account for wildcards
		return false;
	}
}
