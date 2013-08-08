import java.awt.Point;


public class Block {

	private Point upperLeft;
	private int height;
	private int width;
	
	// Hashcode runs as fast as possible
	// Block constructor
	public Block(int x1, int y1, int x2, int y2){
		upperLeft = new Point(x1, y1);
		height = Math.abs(x2 - x1) + 1;
		width = Math.abs(y2-y1) + 1;
		
	}
	
	public int height(){
		if(height==0)
			throw new IllegalArgumentException("Block size is invalid");
		return height;
	}
	
	public int width(){
		if(width==0)
			throw new IllegalArgumentException("Block size is invalid");
		return width;
	}
	
	public Point upperLeftCorner(){
		return upperLeft;
	}
	
	public Block copy(){
		int x1 = upperLeft.x;
		int y1 = upperLeft.y;
		int x2 = x1 + height;
		int y2 = y1 + width;
		return new Block(x1, y1, x2, y2);
	}
	
	
	public String toString(){
		return "Position is ("+ upperLeft.x+","+ upperLeft.y+")."+ " Height is "+height+ " and "+ "Width is "+ width+".";
	}
	
	// return true if argument intersects
	public boolean intersect(){
		return true;
	}
}