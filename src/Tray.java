import java.util.*;

public class Tray implements Comparable<Tray>{

	// dimensions of the tray
	private int height;
	private int width;
	// redundant representations of blocks, each for a specific enhancement
	private int[][] tray;
	private HashMap<Integer, Block> blocks;
	// the moves it took to get to this tray
	private Stack<String> moves;
	// goal tray to compare to. set externally in solver.java
	public static Tray goal;
	// unmemoized initial values, see description in the functions that set them
	private double myHeuristic = 2;
	private int hash = 0;
	
	/**
	 * creates a tray of specified dimensions
	 * @param trayHeight height of tray
	 * @param trayWidth width of tray
	 */
	public Tray(int trayHeight, int trayWidth){
		this.height = trayHeight;
		this.width = trayWidth;
		tray = new int[trayHeight][trayWidth];
		blocks = new HashMap<Integer, Block>();
		moves = new Stack<String>();			
	}
	
	/**
	 * checks that the internal states of the tray
	 * are internally consistent. builds a new int[][]
	 * from the blocks and compares it to tray
	 */
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
	
	/**
	 * adds a block to this tray and sets its id so that it has a unique
	 * identifier in the 2d array. throws exception if cannot add block
	 * @param b block to add
	 */
	private int idCounter = 1;
	public void add(Block b){
		b.id = idCounter;
		idCounter++;
		for(int i=b.top(); i<=b.bottom(); i++)
			for(int j=b.left(); j<=b.right(); j++) {
				if (tray[i][j]!=0)
					throw new IllegalStateException("adding block where one already exists");
				tray[i][j] = b.id();
			}
		blocks.put(b.id(), b);
		isOK();
	}
	
	/**
	 * removes the tray from the list of trays and updates
	 * tray and blocks accordingly 
	 * @param b block to remove
	 */
	private void remove(Block b) {
		this.blocks.remove(b.id());
		for(int i=b.top(); i<=b.bottom(); i++)
			for(int j=b.left(); j<=b.right(); j++)
				this.tray[i][j] = 0;
		isOK();
	}
	
	/**
	 * returns true if tray is equivalent to the comparison tray
	 * does this by comparing the block equivalency instead of the id array
	 * because each block might have a differnt id on the board
	 */
	public boolean equals(Object o){
		return equals((Tray)o);
	}
	public boolean equals(Tray t) {
		if (t == null) return false;
		for (Block b: blocks.values()) {
			boolean good = false;
			for (Block b2: t.blocks.values())
				if (b.equals(b2))
					good = true;
			if (!good) return false;
		}
		return true;
	}
	
	/**
	 * returns the hashcode associated with the tray by calculating it
	 * from the 2d array of ids. equivalent trays should have the same hashcode
	 */
	public int hashCode() {
		if (hash != 0) return hash;
		HashMap<Integer, Integer> mapping = new HashMap<Integer, Integer>();
		int pow = 0;
		int nextInt = 1;
		for (int i=0; i<height; i++)
			for (int j=0; j<width; j++) {
				int id = tray[i][j];
				if (!mapping.containsKey(id)) {
					mapping.put(id, nextInt);
					nextInt++;
				}
				else {
					int map = mapping.get(id).intValue();
					hash += map * (Math.pow(256, pow) % Integer.MAX_VALUE);
					pow++;
				}
			}
		return hash;
	}
	
	/**
	 * creates a clone of the tray, does not clone internal blocks
	 */
	@SuppressWarnings("unchecked")
	public Tray clone(){
		Tray clone = new Tray(this.height, this.width);
		clone.blocks = (HashMap<Integer,Block>) blocks.clone();
		clone.idCounter = idCounter;
		for(int i = 0; i< tray.length; i++){
			for(int j = 0; j< tray[i].length; j++){
				clone.tray[i][j] = tray[i][j];
			}
		}
		return clone;
	}
	
	/**
	 * returns a number between 0 and 1 that measures how close the
	 * tray is to the goal tray. 0 is a perfect match, 1 is impossible
	 * to reach goal tray. lower numbers mean that you are closer to the
	 * goal state. calculated by the manhattan distance between
	 * goal blocks and self blocks.
	 * @return heuristic
	 */
	public Double heuristic() {
		if (myHeuristic <= 1) return myHeuristic;
		if (goal.height != height || goal.width != width) {
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
	public int compareTo(Tray t) {
		return heuristic().compareTo(t.heuristic());
	}

	public String toString() {
		return "Tray<("+height+"x"+width+") "+blocks.values().size()+" blocks>"; 
	}
	
	/**
	 * prints a grpahical representation of the board to stdout
	 */
	public void printBoard() {
		String res = new String();
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				res += tray[i][j] + " ";
				if (tray[i][j] < 10) res += " ";
			}
			res += "\n";
		}
		System.out.println(res);
	}
	
	public int height() { return height; }
	public int width() { return width; }
	
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

	/**
	 * returns the string associated with moving block in directino
	 * i.e. "1 1 1 2" for moving block at 1,1 to 1,2
	 * @param b block to move
	 * @param d direction to move
	 * @return string associate with movement
	 */
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
	
	/**
	 * return the list of moves (in string form)
	 * @return
	 */
	public Stack<String> getMoves(){
		return moves;
	}
	
}