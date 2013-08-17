public class Block {
	private int top, bottom, left, right;
	public int id;
		
	/**
	 * creates a block with the line of input from file
	 * @param i1 top coordinate
	 * @param j1 left coordinate
	 * @param i2 bottom coordinate - 1
	 * @param j2 right coordinate -1
	 */
	public Block(int i1, int j1, int i2, int j2) {
		this.top = i1;
		this.left = j1;
		this.bottom = i2;
		this.right = j2;
		if (top > bottom || left > right)
			throw new IllegalArgumentException("invalid coordinates");
	}
	
	public String toString(){
		return "Block<("+height()+"x"+width()+")@["+top+","+left+"]>";
	}

	public int height() {
		return bottom+1 - top;
	}

	public int width() {
		return right+1 - left;
	}
	
	public int id() {
		return id;
	}
	public int top() {
		return top;
	}
	public int bottom() {
		return bottom;
	}
	public int left() {
		return left;
	}
	public int right() {
		return right;
	}
	
	/** 
	 * creates a clone of this block for moving purposes
	 * keep the id the same so that tray's 2d array will be consistent
	 */
	public Block clone(){
		Block b = new Block(top, left, bottom, right);
		b.id = this.id;
		return b;
	}

	/**
	 * returns if this block is at the same location and has
	 * the same dimensions as the reference
	 */
	public boolean equals(Object ref) {
		Block other = (Block) ref;
		return other.top == top && other.bottom == bottom
			&& other.left == left && other.right == right;
	}
	
	/**
	 * calculates a unique hashcode based on current location
	 * and dimensions. assuming top/bottom/left/right < 256
	 * converts the coordinates into a 4-digit base256 number 
	 */
	public int hashCode() {
		return top*256*256*256 + bottom*256*256 + left*256 + right;
	}
	
	/**
	 * returns a new block with updated coordinates as a result of 
	 * moving in a direction. does not modify current state.
	 * @param direction the direction to move in
	 * @return a new block with the updated coordinates
	 */
	public Block move(char direction) {
		Block b = this.clone();
		switch(direction) {
			case 'u': b.top--; b.bottom--; break;
			case 'd': b.top++; b.bottom++; break;
			case 'l': b.left--; b.right--; break;
			case 'r': b.left++; b.right++; break;
		}
		return b;
	}
	
	/**
	 * returns true if same size
	 * @param b block to compare to
	 * @return true if same size as b
	 */
	public boolean sameSize(Block b) {
		return width() == b.width() &&
				height() == b.height();
	}
	
	/**
	 * returns the manhattan distance between self and b
	 * which is the difference in top-left corners
	 * @param b block to compare against
	 * @return integer that is manhattan distance
	 */
	public int manhattanDistance(Block b) {
		return Math.abs(left() - b.left()) +
				Math.abs(top() - b.top());
	}
}