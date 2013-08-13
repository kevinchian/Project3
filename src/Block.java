public class Block {
		private int top, bottom, left, right;
		private int id;

		//Block class constructor
		public Block(int x1, int y1, int x2, int y2) {
			this.top = x1;
			this.bottom = x2;
			this.left = y1;
			this.right = y2;
			this.id = 0;
		}
		
		public void setID(int i){
			id = i;
		}

		public String toString(){
			return "Block<("+height()+"x"+width()+")@["+top+","+left+"]>";
		}

		public int height() {
			return bottom - top + 1;
		}

		public int width() {
			return right - left + 1;
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
			b.setID(this.id());
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
}