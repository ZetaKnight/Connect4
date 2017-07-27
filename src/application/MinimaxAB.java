package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MinimaxAB {
	
	HeuristicEvaluation he = new HeuristicEvaluation();	
	
	public Coordinates searchMIN(Board board, int depth, boolean maximisingPlayer){
		depth--;
		List<Coordinates> possibleMoves = getPossibleMoves(board);
		
		//allows different moves to be selected, of the same Utility value
		if(board.getGameMode() == Board.SINGLEPLAYER || board.getGameMode() == Board.AIVSAI){
			Random random = new Random();
			int randomNo = random.nextInt();
			if(randomNo%2!=0)Collections.reverse(possibleMoves);
		}
		
		Coordinates bestMove = new Coordinates();
		bestMove.setHeuristicValue(Double.POSITIVE_INFINITY);
		for(Coordinates c : possibleMoves){
			Board b = generateBoard(c, board);
			double minimumScore = alphaBeta(b, depth, !maximisingPlayer, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			if(minimumScore <= bestMove.getHeuristicValue()){
				bestMove.setX(c.getX());bestMove.setY(c.getY());
				bestMove.setHeuristicValue(minimumScore);
			}
			
		}
		
		return bestMove;
	}
	
	public Coordinates searchMAX(Board board, int depth, boolean maximisingPlayer){
		depth--;
		List<Coordinates> possibleMoves = getPossibleMoves(board);
		
		//allows different moves to be selected, of the same Utility value
		if(board.getGameMode() == Board.SINGLEPLAYER || board.getGameMode() == Board.AIVSAI){
			Random random = new Random();
			int randomNo = random.nextInt();
			if(randomNo%2!=0)Collections.reverse(possibleMoves);
		}
		
		Coordinates bestMove = new Coordinates();
		bestMove.setHeuristicValue(Double.NEGATIVE_INFINITY);
		for(Coordinates c : possibleMoves){
			Board b = generateBoard(c, board);
			double maximumScore = alphaBeta(b, depth, !maximisingPlayer, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			if(maximumScore >= bestMove.getHeuristicValue()){
				bestMove.setX(c.getX());bestMove.setY(c.getY());
				bestMove.setHeuristicValue(maximumScore);
			}
		}
		return bestMove;
	}
	
	public double alphaBeta(Board board, int depth, boolean maximisingPlayer, double alpha, double beta){
		depth=depth-1;
		if(depth==0 || isTerminalNode(board, !maximisingPlayer)) return he.evaluate(board, depth);
		
		List <Coordinates> possibleMoves = getPossibleMoves(board);

		if(maximisingPlayer){
			for(Coordinates c : possibleMoves){
				Board b = generateBoard(c, board);
				alpha = Math.max(alpha, alphaBeta(b, depth, !maximisingPlayer, alpha, beta));
				if(alpha>=beta)break;
			}
			return alpha;
		}else{
			for(Coordinates c : possibleMoves){
				Board b = generateBoard(c, board);
				beta = Math.min(beta, alphaBeta(b, depth, !maximisingPlayer, alpha, beta));
				if(alpha>=beta)break;
			}
			return beta;
		}
	}
	
	private boolean isTerminalNode(Board board, boolean maximisingPlayer){
		if(board.isWinner(
				board.getMovesTaken().get(board.getMovesTaken().size()-1).getX(), 
				board.getMovesTaken().get(board.getMovesTaken().size()-1).getY(), 
				maximisingPlayer) || board.isGameDrawn())
			return true;
		else
			return false;
	}
	
	private Board generateBoard(Coordinates coordinates, Board board){
		Board newBoard = new Board();
		newBoard.copyBoard(newBoard, board);
		newBoard.setCounter(coordinates.getX(), coordinates.getY());
		newBoard.setUserTurn(!newBoard.getUserTurn());
		return newBoard;
	}
	
	private List<Coordinates> getPossibleMoves(Board board){
		List<Coordinates> possibleMoves = new ArrayList<Coordinates>();
		for(int i = 0 ; i < Board.COLUMNS ; i++){
			Integer row = board.findRow(i);
			if(row != null) possibleMoves.add(new Coordinates(i, row));
		}
		return possibleMoves;
	}

}
