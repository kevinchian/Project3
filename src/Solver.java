
public class Solver {

	// Create a graph structure with nodes representing 1 move board states. 
	// Depth First search
	// Check HashCode by Saturday
	public Solver(Tray board){
		
	}
	
	public static void main(String[] args){
		
	   Tray board;
	   if (args.length == 3){
		   
	   }
	   if (args.length == 2) {
            InputSource lines = new InputSource (args[0]);
            InputSource goalInput = new InputSource(args[1]);
            String dimensions = lines.readLine();
            String goalString = goalInput.readLine();
            String[] goalDimen = goalString.split(" ");
            String[] dimensionArr = dimensions.split(" ");
            int width = Integer.parseInt(dimensionArr[0]);
            int height = Integer.parseInt(dimensionArr[1]);
            int goalX1 = Integer.parseInt(goalDimen[0]);
            int goalY1 = Integer.parseInt(goalDimen[0]);
            int goalX2 = Integer.parseInt(goalDimen[0]);
            int goalY2 = Integer.parseInt(goalDimen[0]);
            Block goal = new Block(goalX1, goalY1, goalX2, goalY2);

            board = new Tray(width, height, goal);
         
            while (true) {
                // Read a line from the input file.
                String line = lines.readLine ( );
                System.out.println(line);
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
		   throw new IllegalArgumentException("Need 2 arguments");
	   } 
	   System.out.println(board.toString());
	}
	
	// Continue solving
	// use fringe
	// heuristic function
	// breadth first search
	public void solve(){
		
	}
}
	   
