import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;


public class Solver {
	
	private static boolean iAmDebugging = false;
	private HashSet<Tray> previousStates;
	private Stack<Tray> stackFringe;
//	private Queue<Tray> queueFringe;
//	private PriorityQueue<Tray> priorityQueueFringe;
	private Tray init;
	private Tray goal;
	private ArrayList<String> path;

	public Solver(Tray init, Tray goal){
		this.goal = goal;
		this.init = init;
		
		stackFringe = new Stack<Tray>();
		stackFringe.push(init);
		path = new ArrayList<String>();
//		queueFringe = new LinkedList<Tray>();
//		queueFringe.add(init);
//		
//		priorityQueueFringe = new PriorityQueue<Tray>();
//		priorityQueueFringe.add(init);
		
		previousStates = new HashSet<Tray>();
		//previousStates.add(init);
		
	}
	
	public StringBuilder stackSolve(){

		StringBuilder s = new StringBuilder();
		
		while(!stackFringe.isEmpty()){
			
			//System.out.println(stackFringe.size());
			//System.out.println("before the pop");
			Tray lastPop = stackFringe.pop();
			if(lastPop.getMove() != null){
				path.add(lastPop.getMove());
			}
			//System.out.println("after the pop");	
			if (lastPop.equals(goal)){		
				System.out.println("if last pop equals goal");
				Iterator<String> iter = path.iterator();
				while (iter.hasNext()){
					s.append(iter.next().toString() + "\n");
				}
				return s;
			}
			while (previousStates.contains(lastPop) ){
				// keep updating last pop
				//System.out.println("reupdating the states");
				lastPop = stackFringe.pop();	
			}	
			
			HashSet<Tray> moves = lastPop.moves();
			for(Tray t : moves){
				if(previousStates.contains(t)){
					System.out.println("break");
					continue;
				}
				stackFringe.add(t);
			//	System.out.println("added something t");
			}
			
			//System.out.println("added all the children");			
			previousStates.add(lastPop);
			//System.out.println(stackFringe.size());
		}
		System.exit(1);
		return s;
	}
	
	public String queueSolve(){
		return null;
	}
	
	public String priorityQueueSolve(){
		return null;
	}

	public static void main(String[] args){
		
		long start = System.currentTimeMillis();
		
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
		   //System.out.println("Goal: " + goal);
		   Solver s = new Solver(board, goal);
		   StringBuilder solution = s.stackSolve();
		   System.out.println("Solution: " + "\n" + solution);
		   
		   long end = System.currentTimeMillis();
		   System.out.println("The time it took: " + (end-start));
		   
		   //tests/easy/140x140 tests/easy/140x140.goal
		   //tests/hard/hard/big.tray.1 tests/hard/hard/big.tray.1.goal
		   //tests/hard/hard/lane.rouge tests/hard/hard/lane.rouge.goal
		}
}
