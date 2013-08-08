import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;


public class BlockTest extends TestCase{
	
	@Test
	public void testConstructor() {
		Block b = new Block(1,2,3,4);
	}	
	
	@Test
	public void testCopy(){
		Block b3 = new Block(123,182,200,122); // random block
		Block copyb3 = b3.copy();
		
		System.out.println(copyb3.height());
		assertFalse(copyb3.height()==78);
		assertTrue(copyb3.width()==60);
		assertEquals(copyb3.toString(),"Position is (123,182). Height is 77 and Width is 60.");
	}
	
	@Test
	public void testOne() {
		
		Block b1 = new Block(0,0,1,1); // 1x1 block
		System.out.println(b1.height());
		assertTrue(b1.height()==1);
		assertFalse(b1.width() == 3);
		assertEquals(b1.toString(),"Position is (0,0). Height is 1 and Width is 1.");
		
		
		Block b2 = new Block(1,1,3,3); // 2x2 block
		System.out.println(b2.height());
		assertFalse(b2.height()==5);
		assertTrue(b2.width()==2);
		assertEquals(b2.toString(),"Position is (1,1). Height is 2 and Width is 2.");
		
		
		Block b3 = new Block(123,182,200,122); // random block
		System.out.println(b3.height());
		assertFalse(b3.height()==78);
		assertTrue(b3.width()==60);
		assertEquals(b3.toString(),"Position is (123,182). Height is 77 and Width is 60.");
		
		/*
		Block b3 = new Block(0,0,0,0); // invalid block;
		try{
			 //invalid block
			b1.height();
			fail();
		}
		catch(IllegalArgumentException e){
			assertTrue(true);
		}
		*/
	}
	
	@Test
	public void testPosition(){
		Block b = new Block(123,182,200,122); // random block
		assertTrue(b.upperLeftCorner().x == 123);
		assertTrue(b.upperLeftCorner().y == 182);
		
	}
	

}


