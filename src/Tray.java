import java.util.*;


public class Tray {

	private HashMap<Integer, Block> blocks;
	private ArrayList<Tray> possibleMoves;
	private int[][] tray;
	private static int blockID;
	private ArrayList<Block> goals; // Edit: There can be more than one goal block.
	private static boolean iAmDebugging = true;
	
	public Tray(int trayHeight, int trayWidth){
		blockID = 0;
		tray = new int[trayHeight][trayWidth];
		blocks = new HashMap<Integer, Block>();
		goals = new ArrayList<Block>();
	}
	
	public void addGoals(Block b){
		goals.add(b);
	}

	public void add(Block b){
		blockID++;
		int upperBoundX = b.upperLeftCorner().x + b.height() -1 ;
		int upperBoundY = b.upperLeftCorner().y +  b.width() -1;
		if(iAmDebugging){
			System.out.println("b.height: " + b.height());
			System.out.println("upperBoundX: " + upperBoundX);
			System.out.println("b.width: " + b.width());
			System.out.println("upperBoundY: " + upperBoundY);
		}
		for(int i = b.upperLeftCorner().x; i <= upperBoundX; i++){
			for(int j = b.upperLeftCorner().y; j <= upperBoundY; j++){
				tray[i][j] = blockID;
			}
		}
		blocks.put(blockID, b);
	}
	
	public void isOK(){
		
	}
	
	public ArrayList<Tray> moves(){
		return possibleMoves;
	}
	
	private void move(){ // what should parameters be?
		
	}
	
	public boolean finished(){
		for(Block goal : goals){
			int blockNumber = tray[goal.upperLeftCorner().x][goal.upperLeftCorner().y];
			if(blockNumber == 0){
				return false;
			}
			Block b = blocks.get(blockNumber);
			if(b.upperLeftCorner().equals(goal.upperLeftCorner()) && b.height()==goal.height()
					&& b.width()==goal.width()){
				return true;
			}
		}
		return false;
	}
	
	public String toString(){
		String rtn = "";
		for(int i = 0; i <= blockID; i++ )
			rtn+="BlockID: "+ i + " Block: " + blocks.get(i) +"\n";
		for(int i = 0; i < tray.length; i++){
			for(int j = 0; j < tray[i].length; j++){
				System.out.print(tray[i][j] + " ");
			}
			System.out.println("");
		}
		return rtn;
	}
	
	public boolean equals(Object o){ //When two tray identities equal. Used for HashCode
		Tray compare = (Tray) o;
		if(blocks.size() != compare.blocks.size()){
			return false;
		}
		for(Object values : blocks.values()){
			if(!compare.blocks.containsValue(values)){ //Assuming that all blocks exist with the same block items, the tray will be the same
				return false;
			}
		}
		return true;
	}
}
