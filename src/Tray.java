import java.awt.Point;
import java.util.*;

public class Tray {

	private static boolean iAmDebugging = true;
	
	private int height;
	private int width;
	private int[][] tray;
	private HashMap<Integer, Block> blocks;
	// don't keep track of empty blocks since we will create a billion trays
	// and keeping all the empty blocks will be very memory-intensive
	
	public Tray(int trayHeight, int trayWidth){
		this.height = trayHeight;
		this.width = trayWidth;
		tray = new int[trayHeight][trayWidth];
		blocks = new HashMap<Integer, Block>();
	}
	
	// Sometimes the goal file can have out of bounds goals. We need to catch these in the Solver class and say no solution
	public void add(Block b){
		for(int i=b.top(); i<b.bottom(); i++)
			for(int j=b.left(); j<b.right(); j++) {
				if (i >= height || j >= width || i < 0 || j < 0 )
					throw new IllegalStateException("block out of bounds");
				if (tray[i][j]!=0)
					throw new IllegalStateException("conflicting block positions");
				tray[i][j] = b.id();
			}
		blocks.put(b.id(), b);
	}
	
	private void remove(Block b) {
		blocks.remove(b.id());
		for(int i=b.top(); i<b.bottom(); i++)
			for(int j=b.left(); j<b.right(); j++)
				tray[i][j] = 0;
	}
	
	public void isOK(){
		int[][] comparision = new int[height][width];
		for (Block b: blocks.values())
			for (int i=b.top();i<b.bottom();i++)
				for (int j=b.left();i<b.right();j++) {
					if (comparision[i][j] != 0)
						throw new IllegalStateException("conflicting block positions");
					else comparision[i][j] = b.id();
				}
		if (!comparision.equals(tray))
			throw new IllegalStateException("inconsistency between blocks and tray");
	}
	
	public int hashCode() {
		return tray.hashCode();
	}
	
	/* returns 1 if the block at [i,j] can move in direction
	 * direction is either u, d, l, or r
	 * returns 0 if the block cannot move in that direction
	 * returns -1 if no block found at [i,j]
	 * runs in O(1) time, provided block sizes aren't too big
	 */
	private int canMove(int i, int j, char direction) {
		if (i<0 || i >= height || j<0 || j >= width)
			return -1;
		if (tray[i][j] == 0)
			return -1;
		int id = tray[i][j];
		Block b = blocks.get(id);
		if (direction == 'u') {
			int i2 = i - 1;
			if (i2 < 0) return 0;
			for (int j2=b.left(); j2<b.right(); j2++)
				if (tray[i2][j2] != 0) return 0;
		} else if (direction == 'd') {
			int i2 = i + 1;
			if (i2 >= height) return 0;
			for (int j2=b.left(); j2<b.right(); j2++)
				if (tray[i2][j2] != 0) return 0;
		} else if (direction == 'l') {
			int j2 = j - 1;
			if (j2 < 0) return 0;
			for (int i2=b.top(); i2<b.bottom(); i2++)
				if (tray[i2][j2] != 0) return 0;
		} else if (direction == 'r') {
			int j2 = j + 1;
			if (j2 >= width) return 0;
			for (int i2=b.top(); i2<b.bottom(); i2++)
				if (tray[i2][j2] != 0) return 0;
		}
		return 1;
	}
	
	/* iterates through all of the blank spaces, finds blocks above,
	 * below, left or right, and invites the blocks to fill up the
	 * each blank space
	 * should run in O(n) time, where n is the number of blank spaces
	 */
	public HashSet<Tray> moves(){
		HashSet<Tray> possible = new HashSet<Tray>();
		for (int i=0; i<height; i++)
			for (int j=0; j<width; j++)
				if (tray[i][j] == 0) {
					possible.add(invite(i,j,'u'));
					possible.add(invite(i,j,'d'));
					possible.add(invite(i,j,'l'));
					possible.add(invite(i,j,'r'));
				}
		return possible;
	}
	
	/* returns Tray that's a result of inviting a block from direction d
	 * returns null if no block can move from that direction
	 * input: i, j is the empty space that is inviting
	 * d is the direction that the empty space will invite from
	 * runs in O(1) time, provided block sizes aren't too big
	 * may be bottle necked by the .clone method
	 */
	private Tray invite(int i, int j, char d) {
		// change direction to point in the direction that block will move
		// change i, j to point to the block in that direction
		switch (d) {
			case 'u': d = 'd'; i = i-1; break;
			case 'd': d = 'u'; i = i+1; break;
			case 'l': d = 'r'; j = j-1; break;
			case 'r': d = 'l'; j = j+1; break;
		}
		
		if (canMove(i, j, d) != 1) return null;
		Block b = blocks.get(tray[i][j]); 
		Tray t = this.clone();
		t.remove(b);
		t.add(b.move(d));
		return t;
	}
	
	public String toString(){
		String rtn="";
		for(int i = 0; i < tray.length; i++){
			for(int j = 0; j < tray[i].length; j++){
				if (tray[i][j]==-1)
						rtn+="|*|";
				else if (tray[i][j]==0)
					rtn+="|0|";
				else {
					if(j==0 && j+1<tray[i].length && tray[i][j+1]>0) //first x and next is x
						rtn+="|"+ tray[i][j]+" ";
					else if(j!=0 && j+1<tray[i].length && tray[i][j+1]>0 && tray[i][j-1]>0)
						rtn+=" "+ tray[i][j]+" ";
					else if(j!=0 && j+1<tray[i].length && tray[i][j+1]>0) //next is x
						rtn+="|"+ tray[i][j]+" ";
					else if(j!=0 && j+1<tray[i].length && tray[i][j-1]>0) //prev is x
						rtn+=" "+tray[i][j]+"|";
					else
						rtn+="|"+tray[i][j]+"|";
						
				}
			}
			rtn+="\n";
		}	
		return rtn;
	}
	
	// Iterates through the tray to see if the equals are the same.
	public boolean equals(Tray t){
		if(t.width != width || t.height != height)
			return false;
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				int thisID = tray[i][j];
				int otherID = t.tray[i][j];
				if(otherID == -1 || thisID == -1) 
					continue;
				else if (thisID == 0 && otherID != 0 || 
						otherID == 0 && thisID != 0)
					return false;
				else{
					Block b1 = blocks.get(thisID);
					Block b2 = t.blocks.get(otherID);
					if (!b1.equals(b2))
						return false;
				}
			}
		}
		return true;
	}
	
	public Tray clone(){
		Tray clone = new Tray(this.height, this.width);
		clone.blocks.putAll(blocks);
		clone.tray = tray.clone();
		return clone;
	}
	
	public void convertToGoalTray(){
		for(int i = 0; i < tray.length; i++)
			for(int j = 0; j < tray[i].length; j++)
				if(tray[i][j]==0)
					tray[i][j] = -1;
	}
	

	public static void main(String args[]){
		Tray t = new Tray(5, 5);
		Block b1 = new Block(2, 2, 4, 4);
		System.out.print(b1.toString());
		Block b2 = new Block(0, 0, 2, 3);
		System.out.print(b2.toString());
		System.out.print("\n");
		
		t.add(b1);
		t.add(b2);
		System.out.print(t.toString());
		System.out.println();
		t.convertToGoalTray();
		System.out.print(t.toString());
	
	}
	
	
	
	
}
