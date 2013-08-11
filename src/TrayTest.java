import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.TestCase; 
import org.junit.Test;

public class TrayTest extends TestCase {
/*	
	String[] input = {"easy/140x140", "easy/1x2.two.blocks", "easy/big.block.1", "easy/big.search.1", "easy/big.tray.2", 
			"easy/enormous.full.1", "easy/tree+270", "medium/big.tray.4", "medium/blockado", "medium/dads", 
			"medium/c18", "hard/big.tray.1",  "hard/big.tray.3", "hard/big.tray.4", "hard/blockado", "hard/century+180",
			"hard/d209", "hard/dads", "hard/lane.rouge"};
	String[] output =  {"easy/140x140.goal", "easy/1x2.two.blocks.goal", "easy/big.block.1.goal", "easy/big.search.1.goal", "easy/big.tray.2.goal", 
			"easy/enormous.full.goal.1", "easy/tree+270.goal", "medium/big.tray.4.goal", "medium/blockado.goal", "medium/dads.goal", 
			"medium/18.21.goal", "hard/big.tray.1.goal", "hard/big.tray.3.goal", "hard/big.tray.4.goal", "hard/blockado.goal", "hard/century+180.goal",
			"hard/d209.goal", "hard/dads.goal", "hard/lane.rouge.goal"};
	
	public static Tray initialize(InputSource init, InputSource end){
		Tray board;
        String dimensions = init.readLine(); //reads first line of input, which are dimensions
        String[] boardDimension = dimensions.split(" ");
        int height = Integer.parseInt(boardDimension[0]);
        int width = Integer.parseInt(boardDimension[1]);
        board = new Tray(height, width);
        
        while(true){
        	String goalString = end.readLine();
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
            String line = init.readLine ( );
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
        return board;
	}
	
	@Test
	public void testTimeTrayInitialization(){
		for(int i = 0; i < input.length; i++){
			long start = System.currentTimeMillis();
		    InputSource initialBoardState = new InputSource ("tests/"+input[i]);
		    InputSource goalInput = new InputSource("tests/"+output[i]);
		    Tray test = initialize(initialBoardState, goalInput);
	        long time = System.currentTimeMillis() - start;
	        System.out.println("Initialize " + input[i] + ": " + time + "ms");
		}
	}
	
	@Test
	public void testTimeFindMoves(){
		for(int i = 0; i < input.length; i++){
			InputSource initialBoardState = new InputSource ("tests/"+input[i]);
		    InputSource goalInput = new InputSource("tests/"+output[i]);
		    Tray test = initialize(initialBoardState, goalInput);
		    long start = System.currentTimeMillis();
		    ArrayList<Tray> possibleMoves = test.moves();
		    long time = System.currentTimeMillis() - start;
		    System.out.println("Find moves for " + input[i] + ": " + time + "ms");
		}
	}
	
	@Test
	public void testTimeFinished(){
		for(int i = 0; i < input.length; i++){
			InputSource initialBoardState = new InputSource ("tests/"+input[i]);
		    InputSource goalInput = new InputSource("tests/"+output[i]);
		    Tray test = initialize(initialBoardState, goalInput);
		    long start = System.currentTimeMillis();
		    boolean done = test.finished();
		    long time = System.currentTimeMillis() - start;
		    System.out.println("Check if done for " + input[i] + ": " + time + "ms");
		}
	}

	*/
	public void testEquals(){	
		Tray t1 = new Tray(4, 5);
		Tray t2 = new Tray(3, 5);
		Tray t3 = new Tray(4, 5);
		Block b = new Block(0,0, 2, 2);
		t1.add(b);
		t3.add(b);
		
		System.out.print(t1.toString());
		System.out.print(t3.toString());
		
		assertFalse(t1.equals(t2)); //different size of tray.
		assertTrue(t1.equals(t3)); //same tray but keep getting error from block equals method.
	}
	
	public void testclone(){
		Tray t1 = new Tray(4, 5);
		assertTrue(t1.equals(t1.clone()));
	}
	
	public void testisOK(){
		Tray t1 = new Tray(4,5);
		Block b1 = new Block(0,0,1,2); //is Not Ok
		Block b2 = new Block(0,1,1,5);
		try{
		t1.isOK();
		fail();
		}catch(IllegalStateException e){
			assertTrue(true);
		}
	
		
		Tray t2 = new Tray(4,5);
		Block b3 = new Block(0,0,1,2); //is Ok
		Block b4 = new Block(3,3,4,4); // again equal method is not working.
		try{
			t2.isOK();
			assertTrue(true);
		}catch(IllegalStateException e){
			fail();
		}
		
		
		
		
	}
}
