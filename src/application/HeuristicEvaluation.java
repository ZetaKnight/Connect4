package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeuristicEvaluation {
	
	private static final int MIN_MOVES_TO_WIN = 7;
	private static final int WINNING_STATE = 10000;
	private static final int NEUTRAL_VAL = 0;
	private static final int POSSITIVE_VAL = 1;
	private static final int NEGATIVE_VAL = -1;

	Board board;
	int[][] state;
	int score;
	
	public int evaluate(Board board,int depth){
		this.board = board;
		this.state = this.board.getState();
		score = 0;
		
		Coordinates lastMove = board.getMovesTaken().get(board.getMovesTaken().size()-1);
		boolean userTurn = state[lastMove.getX()][lastMove.getY()]==1 ? true: false;
		if(board.getMovesTaken().size() >= MIN_MOVES_TO_WIN)
			if(board.isWinner(lastMove.getX(), lastMove.getY(), userTurn)) score+=WINNING_STATE;
		
		if(!board.getWinningState()){
			//scan all counters on board
			for(int i = 0 ; i < Board.ROWS ; i++){
				for(int j = 0 ; j < Board.COLUMNS ; j++){
					if(state[j][i] != 0){
						score += getScore(j, i);
					}
				}
			}
		}
		double depthLevel = depth;
		Double finalScore = (double) score;
		if(depth>0)finalScore = Math.pow(finalScore, Math.round((depthLevel/2)+(depthLevel%2)));
		score = finalScore.intValue();
		
		if(!userTurn && board.getWinningState())score=-score;
		
		return score;
	}

	private int getScore(int col, int row) {
		int score = 0;
		int player = state[col][row];
		score += getHorizontalScore(col, row);
		score += getVerticalScore(col, row);
		score += getDiagonalScores(col, row);
		if(player == 2)score = score*(-1);
		return score;
	}

	private int getDiagonalScores(int col, int row) {
		return getForwardSlash(col, row)+getBackSlash(col, row);
	}
	
	private int getForwardSlash(int col, int row){
		List<Integer> counterAlignment = new ArrayList<Integer>();
		
		int score = 0;
		int player = state[col][row];
		int bottom = 0;
		int top = 0;
		
		counterAlignment.add(state[col][row]);
		
		//check top half
		for(int i=1; i < 4; i++){
			int validMove = checkForValidMove(col+i, row+i, player);
			if(validMove == -1)break;
			if(validMove == 1) score +=1;
			top+=1;
			
			counterAlignment.add(state[col+i][row+i]);
		}
		//check bottom half
		for(int i=1; i < 4; i++){
			int validMove = checkForValidMove(col-i, row-i, player);
			if(validMove == -1)break;
			if(validMove == 1) score += 1;
			bottom+=1;
			
			counterAlignment.add(state[col-i][row-i]);
		}
//		return scoreTotal(bottom+top+1, score);
		return scoreTotalling(counterAlignment, col, row);
	}

	private int getBackSlash(int col, int row){
		List<Integer> counterAlignment = new ArrayList<Integer>();
		
		int score = 0;
		int player = state[col][row];
		int bottom = 0;
		int top = 0;
		
		counterAlignment.add(state[col][row]);
		
		//check top half
		for(int i=1; i < 4; i++){
			int validMove = checkForValidMove(col-i, row+i, player);
			if(validMove == -1)break;
			if(validMove == 1) score +=1;
			top+=1;
			
			counterAlignment.add(state[col-i][row+i]);
		}
		//check bottom half
		for(int i=1; i < 4; i++){
			int validMove = checkForValidMove(col+i, row-i, player);
			if(validMove == -1)break;
			if(validMove == 1) score += 1;
			bottom+=1;
			
			counterAlignment.add(state[col+i][row-i]);
		}
		
//		return scoreTotal(bottom+top+1, score);
		return scoreTotalling(counterAlignment, col, row);
	}
	
	private int getVerticalScore(int col, int row) {
		List<Integer> counterAlignment = new ArrayList<Integer>();
		int score = 0;
		int player = state[col][row];
		int bottom = 0;
		int top = 0;
		
		counterAlignment.add(state[col][row]);
		
		//check top half
		for(int i=1; i < 4; i++){
			int validMove = checkForValidMove(col, row+i, player);
			if(validMove == -1)break;
			if(validMove == 1) score +=1;
			top+=1;
			
			counterAlignment.add(state[col][row+i]);
		}
		//check bottom half
		for(int i=1; i < 4; i++){
			int validMove = checkForValidMove(col, row-i, player);
			if(validMove == -1)break;
			if(validMove == 1) score += 1;
			bottom+=1;
			
			counterAlignment.add(state[col][row-i]);
		}
		
//		return scoreTotal(bottom+top+1, score);
		return scoreTotalling(counterAlignment, col, row);
	}

	private int getHorizontalScore(int col, int row) {
		List<Integer> counterAlignment = new ArrayList<Integer>();
		int score = 0;
		int player = state[col][row];
		int leftSide = 0;
		int rightSide = 0;
		
		counterAlignment.add(state[col][row]);
		
		//check right side
		for(int i=1; i < 4; i++){
			int validMove = checkForValidMove(col+i, row, player);
			if(validMove == -1)break;
			if(validMove == 1) score +=1;
			rightSide+=1;
			counterAlignment.add(state[col+i][row]);
		}
		//check left side
		for(int i=1; i < 4; i++){
			int validMove = checkForValidMove(col-i, row, player);
			if(validMove == -1)break;
			if(validMove == 1) score += 1;
			leftSide+=1;
			counterAlignment.add(0,state[col-i][row]);
		}
		
		//return scoreTotal(leftSide+rightSide+1, score);
		return scoreTotalling(counterAlignment, col, row);
	}
	
	private int checkForValidMove(int col, int row, int player){
		try{
			if(state[col][row] == 0) return NEUTRAL_VAL;
			if(state[col][row] == player) return POSSITIVE_VAL;
		}catch(Exception e){
			
		}
		return NEGATIVE_VAL;
	}
	
	private int scoreTotalling(List<Integer> counterAlignment, int posX, int posY){
		int player = state[posX][posY];
		if(counterAlignment.size() < 4) return 0;
		int combinations = counterAlignment.size() - 3;//combinations
		int countersInVicinity = 0;
		int connectedCounters = 0;
		
		// checking for connected counters ---
		//check right
		for(int i=1; i < 4; i++){
			if(checkForValidMove(posX+i, posY, player) == POSSITIVE_VAL)connectedCounters++;
		}
		//check left
		for(int i=1; i < 4; i++){
			if(checkForValidMove(posX-i, posY, player) == POSSITIVE_VAL)connectedCounters++;
		}
		
		//counters in vicinity ---
		countersInVicinity += Collections.frequency(counterAlignment, player);		
		
		if(connectedCounters>1)	return (int)Math.pow((combinations+countersInVicinity), connectedCounters);
		return combinations+countersInVicinity;
	}
	
}
