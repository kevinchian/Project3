import java.awt.Point;

public class Block {

	private int top;
	private int bottom;
	private int left;
	private int right;
	
	public Block(int i, int j, int i2, int j2){
		if (i == i2) {
			throw new IllegalArgumentException("Block size is invalid");
		} else if (i > i2) {
			bottom = i;
			top = i2;
		} else {
			bottom = i2;
			top = i;
		}
		if (j == j2) {
			throw new IllegalArgumentException("Block size is invalid");
		} else if (j > j2) {
			left = j;
			right = j2;
		} else {
			left = j2;
			right = j;
		}
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
	
	public int height(){
		return bottom - top;
	}
	
	public int width(){
		return right - left;
	}
	public Block clone(){
		return new Block(top, left, bottom, right);
	}
	public String toString(){
		return "Block<"+width()+"x"+height()+"@i,j=["+top+","+left+"]>";
	}
	public boolean intersects(Block b){
		boolean colX = left < b.right && b.left < right;
		boolean rowX = top < b.bottom && b.top < bottom;
		return colX && rowX;
	}
}