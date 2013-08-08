import java.util.HashMap;


public class Tray {

	private HashMap<Integer, Block> blocks;
	// Need to figure out which way this 2D tray works. Y first or X first?
	private int[][] tray;
	private static int blockID;
	private Block goal;
	
	public Tray(int trayHeight, int trayWidth, Block goal){
		blockID = 0;
		tray = new int[trayHeight][trayWidth];
		blocks = new HashMap<Integer, Block>();
	}
	
	
	// Could have potential reversal issues due to x and y mixups. Will check back.
	public void add(Block b){
		blockID++;
		System.out.println("b.width" + b.width());
		int upperRightX = b.upperLeftCorner().x + b.width() -1 ;
		System.out.println("upperRightX: " + upperRightX);
		int lowerRightY = b.upperLeftCorner().y +  b.height() -1;
		System.out.println("lowerRightY: " + lowerRightY);
		for(int i = b.upperLeftCorner().x; i <= upperRightX; i++){
			for(int j = b.upperLeftCorner().y; j <= lowerRightY; j++){
				tray[i][j] = blockID;
			}
		}
		blocks.put(blockID, b);
	}
	
	// Need an input stream in tray. How?
	
	public void isOK(){
		
	}
	
	public String toString(){
		String rtn = "";
		for(int i = 0; i <= blockID; i++ )
			rtn+="BlockID: "+ i + " Block: " + blocks.get(i) +"\n";
		for(int i = 0; i < tray.length; i++){
			System.out.println("");
			for(int j = 0; j < tray[i].length; j++){
				System.out.print(tray[i][j] + " ");
			}
		}
		return rtn;
	}
	
	public boolean equals(){
		return true;
	}
	
//	public ArrayList<Tray> moves(){
//		return ArrayList<Tray>;
//	}
}
