import java.util.*;

public class Tray {

	private static boolean iAmDebugging = true;
	
	private int[][] tray;
	private HashSet<Block> blocks;
	
	public Tray(int trayHeight, int trayWidth){
		tray = new int[trayHeight][trayWidth];
		blocks = new HashSet<Block>();
	}
	
	public void add(Block b){
		blocks.add(b);
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
		String rtn = "";
		
		while(blocks.iterator().hasNext()){
			rtn+= "BlockID: " + blocks.iterator().next() + " ";
		}
		for(int i = 0; i < tray.length; i++){
			for(int j = 0; j < tray[i].length; j++){
				System.out.print(tray[i][j] + " ");
			}
			System.out.println("");
		}	
		
		return rtn;
	}
	
	public boolean equals(Object o){
		// kevin
		// make sure to account for wildcards
		return false;
	}
}
