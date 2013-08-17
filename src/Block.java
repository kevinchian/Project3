public class Block {
		private int top, bottom, left, right;
		public int id;
		
		//Block class constructor
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
	
	public Block clone(){
		Block b = new Block(top, left, bottom, right);
		b.id = this.id;
		return b;
	}

	public boolean equals(Object ref) {
		Block other = (Block) ref;
		return other.top == top && other.bottom == bottom
			&& other.left == left && other.right == right;
	}
	
	public int hashCode() {
		return top*256*256*256 + bottom*256*256 + left*256 + right;
	}
	
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
	
	public boolean sameSize(Block b) {
		return width() == b.width() &&
				height() == b.height();
	}
	
	public int manhattanDistance(Block b) {
		return Math.abs(left() - b.left()) +
				Math.abs(top() - b.top());
	}
}