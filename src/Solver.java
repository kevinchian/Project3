import java.util.HashSet;
import java.util.PriorityQueue;

public class Solver {

	// list of visited nodes
	private HashSet<Tray> visited;
	/// list of nodes yet to be explored
	private PriorityQueue<Tray> fringe;

	public static boolean debugging = false;
	public static boolean printing = false;
	public static boolean timing = false;

	/**
	 * creates a new solver class, but not solve the problem yet
	 * @param init initial tray state
	 * @param goal goal tray state
	 */
	public Solver(Tray init, Tray goal){
		Tray.goal = goal;
		visited = new HashSet<Tray>();
		fringe = new PriorityQueue<Tray>();
		fringe.add(init);
	}

	/**
	 * print o.toString() if currently debugging
	 * @param o the object to print
	 */
	public static void log(Object o) {
		if (debugging)
			System.out.println(o);
	}

	/**
	 * runs dijkstra's algorithm with the the fringe this.fringe
	 * and Tray.heuristic as the priority code
	 * calls winner() when the goal state has been reached
	 */
	public void solve() {
		while (!fringe.isEmpty()) {
			log("fringe size: "+fringe.size());
			Tray node = fringe.poll();
			if (node.heuristic() == 0){
				winner(node);
				return;
			}
			visited.add(node);
			for (Tray next: node.moves()) {
				if (visited.contains(next)) continue;
				if (fringe.contains(next)) continue;
				fringe.add(next);
			}
		}
		log("fringe reached end");
		System.exit(1);
	}

	/**
	 * called when the goal state has been reached, prints moves
	 * @param node the winning tray, which contains the list of moves
	 */
	public void winner(Tray node) {
		log("winner!");
		for (String s: node.getMoves())
			System.out.println(s);
	}

	/**
	 * sets the debugging options
	 * @param option the option from command line
	 */
	public static void setParams(String option) {
		if (option.equals("-ooptions")) {
			System.out.println("-odebug: print all debug info");
			System.out.println("-otime: print time it takes to solve puzzle");
			System.out.println("-oprint: print starting and ending tray");
			System.exit(0);
		} else if (option.equals("-odebug")) {
			debugging = true;
		} else if (option.equals("-otime")) {
			timing = true;
		} else if (option.equals("-oprint")) {
			printing = true; 
		}
	}
	
	/**
	 * loads a board from the file
	 * @param file filename of the initial tray
	 * @return a tray that has the information in the intial tray
	 */
	public static Tray loadBoard(String file) {
		log("loading board: "+file);
		InputSource source = new InputSource(file);
		String[] dim = source.readLine().split(" ");
		int height = Integer.parseInt(dim[0]);
		int width = Integer.parseInt(dim[1]);
		Tray res = new Tray(height, width);
		while (true){
			String line = source.readLine();
			if (line == null) break;
			String[] args = line.split(" ");
			int i1 = Integer.parseInt(args[0]);
			int j1 = Integer.parseInt(args[1]);
			int i2 = Integer.parseInt(args[2]);
			int j2 = Integer.parseInt(args[3]); 
			Block b = new Block(i1, j1, i2, j2);
			res.add(b);
		}
		log("board loaded");
		log(res);
		if (debugging)
			res.printBoard();
		return res;
	}
	
	/**
	 * create a new tray and load it with the data from the file
	 * @param init the initial tray, which specifies the width/height
	 * @param file the filename/path of the goal tray
	 * @return a tray that has the information described in the goal tray
	 */
	public static Tray loadGoalBoard(Tray init, String file) {
		log("loading goal board: "+file);
		InputSource source = new InputSource(file);
		Tray res = new Tray(init.height(), init.width());
		while (true){
			String line = source.readLine();
			if (line == null) break;
			String[] args = line.split(" ");
			int i1 = Integer.parseInt(args[0]);
			int j1 = Integer.parseInt(args[1]);
			int i2 = Integer.parseInt(args[2]);
			int j2 = Integer.parseInt(args[3]); 
			Block b = new Block(i1, j1, i2, j2);
			res.add(b);
		}
		log("goal board loaded");
		log(res);
		if (debugging)
			res.printBoard();
		return res;
	}
	
	/**
	 * loads the files that describe the problem and solves the problem
	 * @param initFile filename/path for the initial tray
	 * @param goalFile filename/path for the goal tray
	 */
	public static void solveProblem(String initFile, String goalFile) {
		log("starting to solve problem");
		long startTime = System.currentTimeMillis();
		Tray init = loadBoard(initFile);
		if (printing) init.printBoard();
		Tray goal = loadGoalBoard(init, goalFile);
		if (printing) goal.printBoard();
		Solver s = new Solver(init, goal);
		s.solve();
		if (timing)
			System.out.println("runtime: " + (System.currentTimeMillis() - startTime) + "ms");
	}
	
	/**
	 * main loop for the program.
	 * @param args: if length 2, set the option specified
	 * if length 2, solve the problem specified by the input files with no options
	 * if length 3, solve the problem specified with the options turned on
	 */
	public static void main(String[] args){
		if (args.length == 1) {
			setParams(args[0]);
		} if (args.length == 2) {
			solveProblem(args[0], args[1]);
		} else if (args.length == 3) {
			setParams(args[0]);
			solveProblem(args[1], args[2]);
		} else {
			System.exit(1);
		}
		
		//tests/easy/140x140 tests/easy/140x140.goal
		//tests/hard/hard/big.tray.1 tests/hard/hard/big.tray.1.goal
		//tests/hard/hard/lane.rouge tests/hard/hard/lane.rouge.goal
	}
}
