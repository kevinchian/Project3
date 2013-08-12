import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;


public class Solver {
	
	private static boolean iAmDebugging = false;
	private HashSet<Tray> previousStates;
	private ArrayList<Tray> path;
	private PriorityQueue<Tray> fringe;
	private Tray init;
	private Tray goal;

	public Solver(Tray init, Tray goal){
		this.goal = goal;
		this.init = init;
		path = new ArrayList<Tray>();
		path.add(init);
		fringe = new PriorityQueue<Tray>();
		fringe.add(init);
		previousStates = new HashSet<Tray>();
		previousStates.add(init);
		this.solve(init);
	}
	
	public void solve(Tray currBoard){
		while(!fringe.isEmpty()){
			previousStates.add(currBoard);
			path.add(currBoard);
			if(currBoard.equals(goal)){
				return;
			}
			HashSet<Tray> moves = currBoard.moves();
			int addCounter = 0;
			for(Tray t : moves){
				if(previousStates.contains(t)){
					break;
				}
				fringe.add(t);
				addCounter++;
			}
			if(addCounter == 0){
				path.remove(currBoard);
			}
			solve(fringe.poll());
		return;
	}
}

	public String toString(){
		String s = "";
		for(Tray t : path){
			s += t.toString() + "\n";
		}
		return s;
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
		   System.out.println(s);
		}
}