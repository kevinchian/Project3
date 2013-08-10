import java.util.*;

public class Tray {

	private static boolean iAmDebugging = true;
	
	private int[][] tray;
	private HashMap<Integer, Block> blocks;
	
	public Tray(int trayHeight, int trayWidth){
		tray = new int[trayHeight][trayWidth];
		blocks = new HashMap<Integer, Block>();
	}
	
	public void add(Block b){
		blocks.put(b.id(), b);
		for(int i=b.top(); i<b.bottom(); i++)
			for(int j=b.top(); j<b.bottom(); j++)
				tray[i][j] = b.id();
	}
	
	public void isOK(){
		// james
	}
	
	public ArrayList<Tray> moves(){
		return null;
	}
	
	public String toString(){
		// josh
		return "";
	}
	
	// Iterates through the tray to see if the equals are the same.
	public boolean equals(Tray compare){
		if(this.tray.length != compare.tray.length){
			return false;
		}
		if(this.tray[0].length != compare.tray[0].length){
			return false;
		}
		for(int x = 0; x < tray.length; x++){
			for(int y = 0; y<tray[x].length; y++){
				int thisID = this.tray[x][y];
				int otherID = compare.tray[x][y];
				if(otherID == -1 || thisID == -1){ 
					//skip to next iteration: wild card
				}
				else if(thisID == 0 && otherID != 0 || otherID == 0 && thisID != 0){
					return false;
				}
				else{
					Block b1 = blocks.get(thisID);
					Block b2 = compare.blocks.get(otherID);
					if(b1.top() != b2.top() || b1.bottom() != b2.bottom() || b1.left() != b2.left() 
							|| b1.right() != b2.right()){ // Maybe write a block equals method testing its coordinates but not hashcode.
						return false;
					}
				}
			}
		}
		return true;
	}
	
	// Potentially faster equals method
	public boolean equals2(Tray compare){
		if(this.tray.length != compare.tray.length){
			return false;
		}
		if(this.tray[0].length != compare.tray[0].length){
			return false;
		}
		for(int x = 0; x < tray.length; x++){
			while(true){
				
			}
		}
		return false;
	}
	
	
}
