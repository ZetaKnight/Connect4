package application;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class AlgorithmTest {
	
	/*
	 * Example Board
	 */
	private Board genBoardSimple(){
		Board board = new Board();
		int [][]state = new int[Board.COLUMNS][Board.ROWS];
		board.setState(state);
		boolean player1Turn = true;
		board.setUserTurn(player1Turn);
		board.setCounter(3, 5);
		board.setUserTurn(!player1Turn);
		return board;
	}
	
	/*
	 * Algorithm speed test
	 */
	@Test
	public void testAlgorithmSpeed8() {
		Board board = genBoardSimple();
		
		Minimax minimax = new Minimax();
		long startTime = System.currentTimeMillis();
		minimax.searchMIN(board, 8, false);
		long endTime = System.currentTimeMillis();
		long minimaxTime = endTime - startTime;
		
		MinimaxAB minimaxAB = new MinimaxAB();
		startTime = System.currentTimeMillis();
		minimaxAB.searchMIN(board, 8, false);
		endTime = System.currentTimeMillis();
		long minimaxABtime = endTime - startTime;
		System.out.println("Speed test at a depth level: 8");
		System.out.println("Minimax execution time: "+minimaxTime+"ms");
		System.out.println("Minimax with Alpha Beta pruning execution time: "+minimaxABtime+"ms");
		assertTrue(minimaxABtime < minimaxTime);
	}
	
	@Test
	public void testAlgorithmSpeed6() {
		Board board = genBoardSimple();
		
		Minimax minimax = new Minimax();
		long startTime = System.currentTimeMillis();
		minimax.searchMIN(board, 6, false);
		long endTime = System.currentTimeMillis();
		long minimaxTime = endTime - startTime;
		
		MinimaxAB minimaxAB = new MinimaxAB();
		startTime = System.currentTimeMillis();
		minimaxAB.searchMIN(board, 6, false);
		endTime = System.currentTimeMillis();
		long minimaxABtime = endTime - startTime;
		System.out.println("Speed test at a depth level: 6");
		System.out.println("Minimax execution time: "+minimaxTime+"ms");
		System.out.println("Minimax with Alpha Beta pruning execution time: "+minimaxABtime+"ms");
		assertTrue(minimaxABtime < minimaxTime);
	}
	
	@Test
	public void testAlgorithmSpeed4() {
		Board board = genBoardSimple();
		
		Minimax minimax = new Minimax();
		long startTime = System.currentTimeMillis();
		minimax.searchMIN(board, 4, false);
		long endTime = System.currentTimeMillis();
		long minimaxTime = endTime - startTime;
		
		MinimaxAB minimaxAB = new MinimaxAB();
		startTime = System.currentTimeMillis();
		minimaxAB.searchMIN(board, 4, false);
		endTime = System.currentTimeMillis();
		long minimaxABtime = endTime - startTime;
		System.out.println("Speed test at a depth level: 4");
		System.out.println("Minimax execution time: "+minimaxTime+"ms");
		System.out.println("Minimax with Alpha Beta pruning execution time: "+minimaxABtime+"ms");
		assertTrue(minimaxABtime < minimaxTime);
	}

}
