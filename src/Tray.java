import java.util.*;

public class Tray {

	private static boolean iAmDebugging = true;
	
	private int height;
	private int width;
	private int[][] tray;
	private HashMap<Integer, Block> blocks;
	private int blockID;
	// don't keep track of empty blocks since we will create a billion trays
	// and keeping all the empty blocks will be very memory-intensive
	
	private String move;
	
	public Tray(int trayHeight, int trayWidth){
		this.height = trayHeight;
		this.width = trayWidth;
		tray = new int[trayHeight][trayWidth];
		blocks = new HashMap<Integer, Block>();
		blockID = 0;
		move = null;
	}
	
	public void add(Block b){
		if(b.id() == 0){
			blockID++;
			b.setID(blockID);
		}
		for(int i=b.top(); i<=b.bottom(); i++)
			for(int j=b.left(); j<=b.right(); j++) {
				if (tray[i][j]!=0)
					throw new IllegalStateException("conflicting block positions");
				tray[i][j] = b.id();
			}
		blocks.put(b.id(), b);
	}
	
	private void remove(Block b) {
		this.blocks.remove(b.id());
		for(int i=b.top(); i<=b.bottom(); i++)
			for(int j=b.left(); j<=b.right(); j++)
				this.tray[i][j] = 0;
	}
	
	
	public Tray clone(){
		Tray clone = new Tray(this.height, this.width);
		clone.blocks.putAll(this.blocks);
		for(int i = 0; i< tray.length; i++){
			for(int j = 0; j< tray[i].length; j++){
				clone.tray[i][j] = tray[i][j];
			}
		}
		return clone;
	}
	
	public void convertToGoalTray(){
		for(int i = 0; i < tray.length; i++)
			for(int j = 0; j < tray[i].length; j++)
				if(tray[i][j]==0)
					tray[i][j] = -1;
	}
	
	// Iterates through the tray to see if the equals are the same.
	@Override
	public boolean equals(Object t){
		
		Tray k = (Tray) t;
		if(k.width != width || k.height != height)
			return false;
		for(int i=0; i<height; i++){
			for(int j=0; j<width; j++){
				int thisID = tray[i][j];
				int otherID = k.tray[i][j];
				if(otherID == -1 || thisID == -1) 
					continue;
				else if (thisID == 0 && otherID != 0 || 
						otherID == 0 && thisID != 0)
					return false;
				else if (thisID == 0 && otherID == 0)
					continue;
				else{              
					Block b1 = blocks.get(thisID);
					Block b2 = k.blocks.get(otherID);
					if (!b1.equals(b2))
						return false;
				}
			}
		}
		return true;
	}
	
	public String toString() {
		String boardRep = new String();
		for (int i = 0; i < tray.length; i++) {
			for (int j = 0; j < tray[i].length; j++) {
				boardRep += tray[i][j] + " | ";
			}
			boardRep += "\n";
		}
		for(Block b : blocks.values()){
			boardRep += b.toString() +"\n";
		}
		return boardRep;
	}
	
	public int hashCode(){
		int sum = 0;
		for(Block b : blocks.values()){
			sum += b.hashCode();
		}
		return sum;
	}
	
	
//	public int hashCode(){
//		return this.toString().hashCode();
//	}
	
	public static void main(String args[]){
		Tray t = new Tray(5, 5);
		Block b1 = new Block(2, 2, 3, 3);
		System.out.println(b1.toString());
		Block b2 = new Block(0, 0, 2, 1);
		System.out.println(b2.toString());
		
		t.add(b1);
		t.add(b2);
		System.out.print(t.toString());
		System.out.println();
		t.convertToGoalTray();
		System.out.print(t.toString());
	
	}
	
	/* returns 1 if the block at [i,j] can move in direction
	 * direction is either u, d, l, or r
	 * returns 0 if the block cannot move in that direction
	 * returns -1 if no block found at [i,j]
	 * runs in O(1) time, provided block sizes aren't too big
	 */
	private int canMove(int x, int y, char direction) {
		if (x<0 || x >= height || y<0 || y >= width) // out of bounds
			return -1;
		if (tray[x][y] == 0) // empty space
			return -1;
		int id = tray[x][y]; 
		Block b = blocks.get(id);
		if (direction == 'u') {
			int x2 = x - 1;
			if (x2 < 0) return 0;
			for (int j2=b.left(); j2<=b.right(); j2++)
				if (tray[x2][j2] != 0) return 0;
		} else if (direction == 'd') {
			int i2 = x + 1;
			if (i2 >= height) return 0;
			for (int j2=b.left(); j2<=b.right(); j2++)
				if (tray[i2][j2] != 0) return 0;
		} else if (direction == 'l') {
			int j2 = y - 1;
			if (j2 < 0) return 0;
			for (int i2=b.top(); i2<=b.bottom(); i2++)
				if (tray[i2][j2] != 0) return 0;
		} else if (direction == 'r') {
			int j2 = y + 1;
			if (j2 >= width) return 0;
			for (int i2=b.top(); i2<=b.bottom(); i2++)
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
		possible.remove(null);
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
		t.move = generateMove(b, d);
		//this adds all possible moves, not the correct move path.
		//i need to delete off unwanted moves
		t.add(b.move(d));
		return t;
	}
	
	private static String generateMove(Block a, Character direction){
		
		String s = a.top() + " " + a.left() + " ";
		
		switch(direction) {
		case 'u': int topUp= a.top()-1; s+= topUp + " " + a.left(); break;
		case 'd': int topDown = a.top()+1; s+= topDown + " " + a.left(); break;
		case 'l': int topLeftL= a.left()-1; s+= a.top() + " " + topLeftL; break;
		case 'r': int topLeftR= a.left()+1; s+= a.top() + " " + topLeftR; break;
	}
		//System.out.println(s);
		return s;
	}
	
	public String getMove(){
		return move;
	}
}