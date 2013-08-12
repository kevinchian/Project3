import java.util.*;


public class Solver {
	
	private static boolean iAmDebugging = true;
	private HashSet<Tray> previousStates;
	private ArrayList<Tray> path;
	private PriorityQueue<Tray> traysToExplore;
	private Tray goal;

	// Create a graph structure with nodes representing 1 move board states. 
	// Depth First search
	// Check HashCode by Saturday
	public Solver(Tray board, Tray end){
		previousStates = new HashSet<Tray>();
		traysToExplore = new PriorityQueue<Tray>();
		path = new ArrayList<Tray>();
		goal = end;
		path.add(board);
	}
	
	// Continue solving
	// use fringe
	// heuristic function
	// breadth first search
	public void solve(){
		Tray current = path.get(path.size() - 1);
		HashSet<Tray> moves = current.moves();
		for(Tray t : moves){
			if(t.equals(goal)){
				path.add(t);
				return;
			}
			
		}
	}
	
	public static void main(String[] args){
		
	   Tray board;
	   Tray goal;
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
            goal = new Tray(height, width);
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
                Block b = new Block(goalX1, goalY1, goalX2, goalY2);
                goal.add(b);
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
	   goal.convertToGoalTray();
	   Solver s = new Solver(board, goal);
	   s.solve();
	   System.out.println(s);
	}
}
	   
