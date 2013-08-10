import static org.junit.Assert.*;

import org.junit.Test;


public class SolverTest {

	@Test
	public void testTrayInput() {
		Tray board;
        InputSource lines = new InputSource ("medium/big.tray.4");
        InputSource goalInput = new InputSource("medium/big.tray.4.goal");
        String dimensions = lines.readLine();
        String goalString = goalInput.readLine();
        String[] goalDimen = goalString.split(" ");
        String[] dimensionArr = dimensions.split(" ");
        int width = Integer.parseInt(dimensionArr[0]);
        int height = Integer.parseInt(dimensionArr[1]);
        int goalX1 = Integer.parseInt(goalDimen[0]);
        int goalY1 = Integer.parseInt(goalDimen[1]);
        int goalX2 = Integer.parseInt(goalDimen[2]);
        int goalY2 = Integer.parseInt(goalDimen[3]);
        Block goal = new Block(goalX1, goalY1, goalX2, goalY2);
        System.out.println("goal: " + goal.toString());
        assertEquals(goal.toString(), "Position is (139,139). Height is 1 and Width is 1.");
        board = new Tray(width, height, goal);
        System.out.println(board.toString());
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
   System.out.println(board.toString());
}

}
