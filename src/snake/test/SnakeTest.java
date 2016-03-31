package snake.test;

import static org.junit.Assert.*;

import org.junit.Test;

import snake.eng.Engine;
import snake.eng.Snake;

public class SnakeTest {
	
	
	/**
	 * Tests if the snake's size increase when it eats some simple food
	 */
	@Test
	public void testFood(){
		Engine engine = new Engine (10 , 1);
		engine.setSnake(new Snake(5,5));
		engine.getSnake().addPiece(4,5);
		engine.setBoardItem(4,4 , "F" );
		engine.setLastDir(2);
		engine.moveSnake();
		assertEquals(3, engine.getSnake().getPieces().size());
		
	}
	
	/**
	 * Tests if the snake's speed increases when it eats some "fast food"
	 */
	@Test
	public void testFastFood(){
		Engine engine = new Engine (10 , 1);
		double tempspeed = engine.getSpeed();
		engine.setSnake(new Snake(5,5));
		engine.getSnake().addPiece(4,5);
		engine.setBoardItem(4,4 , "S" );
		engine.setLastDir(2);
		engine.moveSnake();
		assertEquals(tempspeed*1.5, engine.getSpeed(), 0.0001);
	
	}
	
	/**
	 * Tests if the snake's speed decreases when it eats some "slow food"
	 */
	@Test
	public void testSlowFood(){
		Engine engine = new Engine (10 , 1);
		double tempspeed = engine.getSpeed();
		
		engine.setSnake(new Snake(5,5));
		engine.getSnake().addPiece(4,5);
		engine.setBoardItem(4,4 , "L" );
		engine.setLastDir(2);
		engine.moveSnake();
		
		
		assertEquals(tempspeed/1.5, engine.getSpeed(),0);
		
	}
	
	/**
	 * Tests if the snake's die when it eats some "kill food"
	 */
	@Test
	public void checkKill(){
		Engine engine = new Engine (10 , 1);
		
		engine.setSnake(new Snake(5,5));
		engine.getSnake().addPiece(4,5);
		engine.setBoardItem(4,4 , "K" );
		engine.setLastDir(2);
		engine.moveSnake();
		
		assertTrue(engine.getEnd());
	}
	
	/**
	 * Tests if the snake's die when it gives an halter in a wall
	 */
	@Test
	public void checkKillWall(){
		Engine engine = new Engine (10 , 1);
		engine.setPassWalls(false);
		engine.setSnake(new Snake(5,2));
		engine.getSnake().addPiece(5,1);
		engine.setLastDir(2);
		engine.moveSnake();		
		
		assertEquals(true, engine.getEnd());
	}
}
