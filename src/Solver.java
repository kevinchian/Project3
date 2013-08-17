import java.util.HashSet;
import java.util.PriorityQueue;

public class Solver {

	private HashSet<Tray> visited;
	private PriorityQueue<Tray> fringe;

	public static boolean debugging = false;

	public Solver(Tray init, Tray goal){
		Tray.goal = goal;
		visited = new HashSet<Tray>();
		fringe = new PriorityQueue<Tray>();
		fringe.add(init);
	}

	public static void log(Object o) {
		if (debugging)
			System.out.println(o);
	}

	public void solve() {
		while (!fringe.isEmpty()) {
			log("fringe size: "+fringe.size());
			Tray node = fringe.poll();
			if (node.heuristic() == 0)
				winner(node);
				
			visited.add(node);
			for (Tray next: node.moves()) {
				if (visited.contains(next)) continue;
				if (fringe.contains(next)) continue;
				fringe.add(next);
			}
		}
		log("fringe reached end");
	}

	public void winner(Tray node) {
		log("winner!");
		for (String s: node.getMoves())
			System.out.println(s);
		System.exit(1);
	}

	public static void setParams(String option) {
		if (option.equals("-ooptions")) {
			System.out.println("-odebug: print debug info");
		} else if (option.equals("-odebug")) {
			debugging = true;
		}
	}
	
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
	
	public static void solveProblem(String initFile, String goalFile) {
		log("starting to solve problem");
		long startTime = System.currentTimeMillis();
		Tray init = loadBoard(initFile);
		Tray goal = loadGoalBoard(init, goalFile);
		Solver s = new Solver(init, goal);
		s.solve();
		log("runtime: " + (System.currentTimeMillis() - startTime));
	}
	
	public static void main(String[] args){
//		String folder = "hard";
//		String name = "d209";
//		solveProblem("tests/"+folder+"/"+name, "tests/"+folder+"/"+name+".goal");
		if (args.length == 1) {
			setParams(args[0]);
			System.exit(1);
		} if (args.length == 2) {
			solveProblem(args[0], args[1]);
		} else if (args.length == 3) {
			setParams(args[0]);
			solveProblem(args[1], args[2]);
		} else {
			System.out.println("invalid args");
			System.exit(1);
		}
		
		//tests/easy/140x140 tests/easy/140x140.goal
		//tests/hard/hard/big.tray.1 tests/hard/hard/big.tray.1.goal
		//tests/hard/hard/lane.rouge tests/hard/hard/lane.rouge.goal
	}
}
