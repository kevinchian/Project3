import java.util.*;

public class Tray {

	private int height;
	private int width;
	private int[][] tray;
	private HashMap<Integer, Block> blocks;
	private Stack<String> moves;
	public static Tray goal;
	// a double [0,1] that determines how close you are to goal state. lower is better
	private double myHeuristic = 2;
	private int hash = 0;
	
	public Tray(int trayHeight, int trayWidth){
		this.height = trayHeight;
		this.width = trayWidth;
		tray = new int[trayHeight][trayWidth];
		blocks = new HashMap<Integer, Block>();
		moves = new Stack<String>();			
	}
	
	public void isOK() {
		int[][] comparison = new int[height][width];
		for (Integer id: blocks.keySet()) {
			Block b = blocks.get(id);
			for (int i=b.top();i<=b.bottom();i++)
				for (int j=b.left();j<=b.right();j++) {
					if (i >= height || j >= width)
						throw new IllegalStateException("invalid coordinates in blocks");
					if (comparison[i][j] != 0)
						throw new IllegalStateException("conflicting block positinos");
					comparison[i][j] = id;
				}
		}
		for (int i=0; i<height; i++)
			for (int j=0; j<width; j++)
				if (tray[i][j] != comparison[i][j])
					throw new IllegalStateException("descrepency between tray and blocks");
	}
	
	public void add(Block b){
		for(int i=b.top(); i<=b.bottom(); i++)
			for(int j=b.left(); j<=b.right(); j++) {
				if (tray[i][j]!=0)
					throw new IllegalStateException("adding block where one already exists");
				tray[i][j] = b.id();
			}
		blocks.put(b.id(), b);
		isOK();
	}
	
	private void remove(Block b) {
		this.blocks.remove(b.id());
		for(int i=b.top(); i<=b.bottom(); i++)
			for(int j=b.left(); j<=b.right(); j++)
				this.tray[i][j] = 0;
	}
	
	public boolean equals(Object o){
		return equals((Tray)o);
	}
	public boolean equals(Tray t) {
		for (int i=0; i<height; i++)
			for (int j=0; j<width; j++)
				if (tray[i][j] != t.tray[i][j])
					return false;
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public Tray clone(){
		Tray clone = new Tray(this.height, this.width);
		clone.blocks = (HashMap<Integer,Block>) blocks.clone();
		for(int i = 0; i< tray.length; i++){
			for(int j = 0; j< tray[i].length; j++){
				clone.tray[i][j] = tray[i][j];
			}
		}
		return clone;
	}
	
	public Double heuristic() {
		if (myHeuristic <= 1) return myHeuristic;
		if (goal.height != height || goal.width != width) {
			System.out.print("warning: inconsistent sizes");
			myHeuristic = 1.0;
			return 1.0;
		}
		double finalscore = 0;
		for (Block b: goal.blocks.values()) {
			double score = 1;
			for (Block b2: blocks.values()) {
				if (!b.sameSize(b2)) continue;
				double bscore = (double)b.manhattanDistance(b2)/(width+height);
				if (bscore < score)
					score = bscore;
			}
			finalscore += score;
		}
		myHeuristic = finalscore / goal.blocks.size();
		return myHeuristic;
	}
	public int compareTo(Object o) { 
		return compareTo((Tray)o); 
	}
	public int compareTo(Tray t) {
		return heuristic().compareTo(t.heuristic());
	}

	public String toString() {
		String res = new String();
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				res += tray[i][j] + " ";
				if (tray[i][j] < 10) res += " ";
			}
			res += "\n";
		}
		return res;
	}
	
	/* returns 1 if the block at [i,j] can move in direction
	 * direction is either u, d, l, or r
	 * returns 0 if the block cannot move in that direction
	 * returns -1 if no block found at [i,j]
	 * runs in O(1) time, provided block sizes aren't too big
	 */
	private int canMove(int i, int j, char direction) {
		if (i<0 || i>=height || j<0 || j>=width) // out of bounds
			return -1;
		int id = tray[i][j]; 
		if (id == 0) // empty space
			return -1;
		Block b = blocks.get(id);
		if (direction == 'u') {
			int i2 = i - 1;
			if (i2 < 0) return 0;
			for (int j2=b.left(); j2<=b.right(); j2++)
				if (tray[i2][j2] != 0) return 0;
		} else if (direction == 'd') {
			int i2 = i + 1;
			if (i2 >= height) return 0;
			for (int j2=b.left(); j2<=b.right(); j2++)
				if (tray[i2][j2] != 0) return 0;
		} else if (direction == 'l') {
			int j2 = j - 1;
			if (j2 < 0) return 0;
			for (int i2=b.top(); i2<=b.bottom(); i2++)
				if (tray[i2][j2] != 0) return 0;
		} else if (direction == 'r') {
			int j2 = j + 1;
			if (j2 >= width) return 0;
			for (int i2=b.top(); i2<=b.bottom(); i2++)
				if (tray[i2][j2] != 0) return 0;
		}
		return 1;
	}
	
	public int hashCode() {
		if (this.hash != 0) return this.hash;
		int pow = 0;
		for (int i=0; i<height; i++)
			for (int j=0; j<height; j++) {
				hash += Math.pow(65536, pow)*tray[i][j] % Integer.MAX_VALUE;
				pow++;
			}
		return hash;
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
	@SuppressWarnings("unchecked")
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
		t.moves = (Stack<String>)this.moves.clone();
		t.moves.push(getMoveString(b, d));
		t.remove(b);
		t.add(b.move(d));
		return t;
	}

	private static String getMoveString(Block b, char d){
		String res = b.top() + " " + b.left() + " ";
		switch(d) {
			case 'u': res += (b.top()-1) + " " + b.left(); break;
			case 'd': res += (b.top()+1) + " " + b.left(); break;
			case 'l': res += b.top() + " " + (b.left()-1); break;
			case 'r': res += b.top() + " " + (b.left()+1); break;
		}
		return res;
	}
	
	public Stack<String> getMoves(){
		return moves;
	}
	
}