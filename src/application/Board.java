package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import GUI.BoardGUI;

public class Board {
	
	public static final int COLUMNS = 7;
	public static final int ROWS = 6;
	private static final int CONNECT4 = 4;
	public static final int SINGLEPLAYER = 1;
	public static final int TWOPLAYER = 2;
	public static final int ONLINEMULTIPLAYER = 3;
	public static final int AIVSAI = 4;
	
	public static final int EASY = 4;
	public static final int MEDIUM = 6;
	public static final int HARD = 8;
	public static final int VERY_HARD = 10;
	
	private int [][] state;
	private boolean userTurn;
	private boolean winningState;
	private BoardGUI boardGUI;
	
	private int gameMode;//Single player (against AI) or Two player	
	private List<Coordinates> movesTaken;//For undoing a move in Single player mode
	private Opponent opponent; //Single player mode
	private int difficulty = 8;//Single player mode level of difficulty (default 8)
	private OnlineMultiplayer onlineMultiPlayer;
	
	
	public Board(BoardGUI boardGUI, int gameMode, int difficulty){
		this.boardGUI = boardGUI;
		this.gameMode = gameMode;
		userTurn = true;
		state = new int[COLUMNS][ROWS];
		movesTaken = new ArrayList<Coordinates>();
		if(gameMode == SINGLEPLAYER){
			this.difficulty = difficulty;
			opponent = new Opponent(this, difficulty);		
		}else if(gameMode == ONLINEMULTIPLAYER){
			onlineMultiPlayer = new OnlineMultiplayer(this, this.boardGUI);	
		}
	}
	// computer verses computer (ai vs ai)
	public Board(BoardGUI boardGUI, int gameMode, AiSimulation ai){
		this.boardGUI = boardGUI;
		this.gameMode = gameMode;
		userTurn = true;
		state = new int[COLUMNS][ROWS];
		movesTaken = new ArrayList<Coordinates>();
		this.gameMode = gameMode;
		ai.setBoard(this);
		ai.run();
	}
	
	public Board(){}
	
	public void copyBoard(Board b1, Board b2){
		b1.setUserTurn(b2.getUserTurn());
		for(int i =0; i<ROWS; i++){
			for(int j=0;j<COLUMNS;j++){
				b1.getState()[j][i] = b2.getState()[j][i];
			}	
		}
		b1.setGameMode(b2.getGameMode());
	}
	
	// for retrieving board from a save ---
	public Board(Board board){
		this.userTurn = board.getUserTurn();
		this.state = board.getState();
		this.gameMode = board.getGameMode();
		movesTaken = new ArrayList<Coordinates>();
	}
	// ------------------------------------
	
	public List<Coordinates> getPossibleMoves(Board board){
		List<Coordinates> possibleMoves = new ArrayList<Coordinates>();
		for(int col = 0 ; col < COLUMNS ; col++){
			Integer row = findRow(col);
			if(row != null) possibleMoves.add(new Coordinates(col, row));
		}
		return possibleMoves;
	}
	
	public void setGameMode(int gameMode){
		this.gameMode = gameMode;
	}
	public int getGameMode(){
		return gameMode;
	}
	
	public boolean getUserTurn(){
		return userTurn;
	}
	
	public void setUserTurn(boolean turn){
		this.userTurn = turn;
	}
	
	public void setWinningState(boolean winningState){
		this.winningState=winningState;
	}
	
	public boolean getWinningState(){
		return winningState;
	}
	
	public int [][] getState(){
		if(state==null)state=new int[COLUMNS][ROWS];
		return state;
	}
	
	public void setState(int [][] state){
		this.state = state;
	}
	
	public void setOpponentsMove(){
		opponent.setMove();
	}
	
	//finds the row for the counter to stack upon
	public Integer findRow(int column){
		for(int i = ROWS-1; i >= 0 ; i--){
			if(state[column][i] == 0){
				return i;
			}
		}
		return null;
	}
	
	//sets players 
	public void setCounter(int x, int y){
		if(userTurn) state[x][y] = 1;
		else state[x][y] = 2;
		setMove(x, y);
		
		if(gameMode == ONLINEMULTIPLAYER && userTurn) onlineMultiPlayer.sendMove(new Coordinates(x, y));
	}
	
	public void animateMove(int x, int y){
		Platform.runLater(() -> { boardGUI.animateCounter(x, y);});
	}
	
	public boolean isGameDrawn(){
		if(getMovesTaken().size()==42 && !getWinningState())return true;
		else return false;
	}
	
	public boolean isWinner(int x, int y, boolean userTurn){
		int player = userTurn ? 1 : 2;		
		//checking horizontally
		int horizontal = 0;
		for(int i = 0; i < COLUMNS ; i++){
			if(state[i][y] == player) {
				horizontal++;
				if(horizontal == CONNECT4)return true;
			}else horizontal = 0;
		}
		//checking vertically
		int vertical = 0;
		for(int i = 0; i < ROWS ; i++){
			if(state[x][i] == player) {
				vertical++;
				if(vertical == CONNECT4) return true;
			}else vertical = 0;
		}
		//checking diagonally
		int rightDiagonally = 0;
		int col = (COLUMNS-1) - x;//number of cols to right
		int row = y;//number of rows up
		
		//starting position of coordinates check
		int startPos = Math.min(col, row);
		col = x + startPos;
		row = y - startPos;
		
		//6 is the max diagonal path
		//check for forward slash
		for(int i = 0; i < 6; i++){
			if(col - i >= 0 && row + i <= ROWS-1){
				if(state[col - i][row + i] == player){
					rightDiagonally++;
					if(rightDiagonally == CONNECT4)return true;
				}else rightDiagonally = 0;
			}
		}
		
		int leftDiagonally = 0;
		col = x;
		row = y;
		
		//starting position of coordinates check
		startPos = Math.min(col, row);
		col = x - startPos;
		row = y - startPos;
		
		//check for back slash
		for(int i = 0; i < 6; i++){
			if(col + i <= COLUMNS-1 && row + i <= ROWS-1){
				if(state[col + i][row + i] == player){
					leftDiagonally++;
					if(leftDiagonally == CONNECT4)return true;
				}else leftDiagonally = 0;
			}
		}
		return false;
	}
	
	public void setMove(int col, int row){
		if (movesTaken == null)movesTaken = new ArrayList<Coordinates>();
		movesTaken.add(new Coordinates(col, row));
	}
	
	public List<Coordinates> getMovesTaken(){
		if(movesTaken==null)movesTaken = new ArrayList<Coordinates>();
		return movesTaken;
	}
	
	public void removeMove(){
		if(getWinningState() || isGameDrawn()){
			Coordinates lastMove = movesTaken.get(movesTaken.size()-1);
			boolean lastMoveIsP1 = state[lastMove.getX()][lastMove.getY()] == 1;
			Coordinates coords = movesTaken.get(movesTaken.size()-1);
			movesTaken.remove(movesTaken.size()-1);
			state[coords.getX()][coords.getY()] = 0;
			if(getUserTurn() && !lastMoveIsP1 && this.getGameMode() == SINGLEPLAYER){
				Coordinates coords2 = movesTaken.get(movesTaken.size()-1);
				movesTaken.remove(movesTaken.size()-1);
				state[coords2.getX()][coords2.getY()] = 0;
			}
		}else{
			if(!movesTaken.isEmpty()){
				Coordinates coords = movesTaken.get(movesTaken.size()-1);
				movesTaken.remove(movesTaken.size()-1);
				state[coords.getX()][coords.getY()] = 0;
				if(movesTaken.size()>0 && this.getGameMode() == SINGLEPLAYER){
					Coordinates coords2 = movesTaken.get(movesTaken.size()-1);
					movesTaken.remove(movesTaken.size()-1);
					state[coords2.getX()][coords2.getY()] = 0;
				}
			} 
		}	
	}
	
	public List<Coordinates> getWinningCoords(int x, int y){
		int player = state[x][y];
		List <Coordinates> countersAlligned = new ArrayList<>();

		//checking horizontally
		int horizontal = 0;
		for(int i = 0; i < COLUMNS ; i++){
			if(state[i][y] == player) {
				horizontal++;
				if(horizontal == CONNECT4){
					countersAlligned.add(new Coordinates(i, y));
					countersAlligned.add(new Coordinates(i-3, y));
					break;
				}
			}else horizontal = 0;
		}
		
		if (horizontal == CONNECT4)	return countersAlligned;
		
		//checking vertically
		int vertical = 0;
		for(int i = 0; i < ROWS ; i++){
			if(state[x][i] == player) {
				vertical++;
				if(vertical == CONNECT4){
					countersAlligned.add(new Coordinates(x, i));
					countersAlligned.add(new Coordinates(x, i-3));
					break;
				}
			}else vertical = 0;
		}
		if(vertical == CONNECT4) return countersAlligned;
		
		//checking diagonally
		int rightDiagonally = 0;
		int col = (COLUMNS-1) - x;//number of cols to right
		int row = y;//number of rows up
		
		//starting position of coordinates check
		int startPos = Math.min(col, row);
		col = x + startPos;
		row = y - startPos;
		
		//6 is the max diagonal path
		//check for forward slash
		for(int i = 0; i < 6; i++){
			if(col - i >= 0 && row + i <= ROWS-1){
				if(state[col - i][row + i] == player){
					rightDiagonally++;
					if(rightDiagonally == CONNECT4){
						countersAlligned.add(new Coordinates((col - i)+3, (row + i) - 3 ));
						countersAlligned.add(new Coordinates(col - i, row + i ));
					}
				}else rightDiagonally = 0;
			}
			if(rightDiagonally == CONNECT4) return countersAlligned;
		}
		
		int leftDiagonally = 0;
		col = x;
		row = y;
		
		//starting position of coordinates check
		startPos = Math.min(col, row);
		col = x - startPos;
		row = y - startPos;
		
		//check for back slash
		for(int i = 0; i < 6; i++){
			if(col + i <= COLUMNS-1 && row + i <= ROWS-1){
				if(state[col + i][row + i] == player){
					leftDiagonally++;
					if(leftDiagonally == CONNECT4){
						countersAlligned.add(new Coordinates((col + i)-3, (row + i)-3 ));
						countersAlligned.add(new Coordinates(col + i, row + i ));
					}
				}else leftDiagonally = 0;
			}
			if(leftDiagonally == CONNECT4)return countersAlligned;	
		}
		
		return null;
	}
	
	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	//prints state of board
	public void getStateTest() {
		for(int i = 0 ; i < ROWS ; i++){
			for(int j = 0 ; j < COLUMNS ; j++){
				System.out.print(state[j][i] + " ");
			}
			System.out.println();
		}
	}
	
	
}
