package application;

public class AiSimulation{

	private boolean isSimpleAlg;
	private int difficulty;
	private boolean isSimpleAlg2;
	private int difficulty2;
	private Board board;
	private boolean takingTurn = false;
	
	private Thread thread;

	private Minimax minimax = new Minimax();
	private MinimaxAB minimaxAB = new MinimaxAB();
	
	public AiSimulation(boolean isSimpleAlg, int difficulty, boolean isSimpleAlg2, int difficulty2 ){
		this.isSimpleAlg = isSimpleAlg;
		this.difficulty = difficulty;
		this.isSimpleAlg2 = isSimpleAlg2;
		this.difficulty2 = difficulty2;
	}
	
	
	public void run() {
		
		
		thread = new Thread(){
			public void run(){
				Coordinates coords;
				while(true){
					if(!board.getWinningState() && !board.isGameDrawn() && !takingTurn && board.getUserTurn()){
						//player 1 ---
						takingTurn = true;
						
						if(isSimpleAlg) coords = minimax.searchMAX(board, difficulty, true);
						else coords = minimaxAB.searchMAX(board, difficulty, true);
						board.setCounter(coords.getX(),coords.getY());			
						if(board.isWinner(coords.getX(), coords.getY(), board.getUserTurn()))board.setWinningState(true);
						board.setUserTurn(!board.getUserTurn());
						board.animateMove(coords.getX(),coords.getY());
						takingTurn = false;
					}else if(!board.getWinningState() && !board.isGameDrawn() && !takingTurn && !board.getUserTurn()){
						//player 2
						takingTurn = true;
						
						if(isSimpleAlg2) coords = minimax.searchMIN(board, difficulty2, false);
						else coords = minimaxAB.searchMIN(board, difficulty2, false);
						board.setCounter(coords.getX(),coords.getY());			
						if(board.isWinner(coords.getX(), coords.getY(), board.getUserTurn()))board.setWinningState(true);
						board.setUserTurn(!board.getUserTurn());
						board.animateMove(coords.getX(),coords.getY());
						takingTurn = false;
					}
					try {Thread.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
				}
			}
		};
		thread.start();
	}
	
	
	public boolean isSimpleAlg() {
		return isSimpleAlg;
	}

	public void setSimpleAlg(boolean isSimpleAlg) {
		this.isSimpleAlg = isSimpleAlg;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public boolean isSimpleAlg2() {
		return isSimpleAlg2;
	}

	public void setSimpleAlg2(boolean isSimpleAlg2) {
		this.isSimpleAlg2 = isSimpleAlg2;
	}

	public int getDifficulty2() {
		return difficulty2;
	}

	public void setDifficulty2(int difficulty2) {
		this.difficulty2 = difficulty2;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
}
