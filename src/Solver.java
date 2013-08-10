import java.util.HashSet;


public class Solver {
	
	private static boolean iAmDebugging = true;
	private HashSet<Tray> previousStates;

	// Create a graph structure with nodes representing 1 move board states. 
	// Depth First search
	// Check HashCode by Saturday
	public Solver(Tray board){
		previousStates = new HashSet<Tray>();
	}
	
	// Continue solving
	// use fringe
	// heuristic function
	// breadth first search
	public void solve(){
		
	}
	
	public static void main(String[] args){
		
	   Tray board;
	   if (args.length == 3){
		   // -o options. Implement later
		   // -otime, -oinfo, -odebug, 
	   }
	   if (args.length == 2) {
            InputSource initialBoardState = new InputSource (args[0]);
            InputSource goalInput = new InputSource(args[1]);
            
            String dimensions = initialBoardState.readLine(); //reads first line of input, which are dimensions
            String[] boardDimension = dimensions.split(" ");
            int height = Integer.parseInt(boardDimension[0]);
            int width = Integer.parseInt(boardDimension[1]);
            board = new Tray(height, width);
            
            while(true){
            	String goalString = goalInput.readLine();
            	if(goalString == null){
            		break;
            	}
                String[] goalLocation = goalString.split(" ");
                int goalX1 = Integer.parseInt(goalLocation[0]);
                int goalY1 = Integer.parseInt(goalLocation[1]);
                int goalX2 = Integer.parseInt(goalLocation[2]);
                int goalY2 = Integer.parseInt(goalLocation[3]); 
                Block goal = new Block(goalX1, goalY1, goalX2, goalY2);
                board.addGoals(goal);
            }
            while (true) {
                // Read a line from the input file.
                String line = initialBoardState.readLine ( );
                if(iAmDebugging){
                	System.out.println(line);
                }
                if(line == null){
                	break;
                }
                String[] arr = line.split(" ");
                int x1 = Integer.parseInt(arr[0]);
                int y1 = Integer.parseInt(arr[1]);
                int x2 = Integer.parseInt(arr[2]);
                int y2 = Integer.parseInt(arr[3]);
                Block b = new Block(x1, y1, x2, y2);
                board.add(b);
            }
            
	   }
	   else{
		   throw new IllegalArgumentException("Illegal amount of arguments: " + args.length);
	   } 
	}
}
	   
