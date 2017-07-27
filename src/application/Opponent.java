package application;

public class Opponent {
	
	private Board board;
	private boolean takingTurn = false;
	private Thread thread;
	private Minimax minimax = new Minimax();
	private MinimaxAB minimaxAB = new MinimaxAB();
	private int level;
	
	public Opponent(Board board, int level){
		this.board = board;
		this.level = level;
		thread = new Thread(){
			public void run(){
				while(true){
					if(board.getUserTurn() == false && takingTurn == false && !board.getWinningState() && !board.isGameDrawn()){
						takingTurn = true;
						setMove();
					}
					try {Thread.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
				}
			}
		};
		thread.start();
	}
	
	public void setMove(){
		Coordinates coords = minimaxAB.searchMIN(board, level, false);
		board.setCounter(coords.getX(),coords.getY());			
		if(board.isWinner(coords.getX(), coords.getY(), board.getUserTurn()))board.setWinningState(true);
		board.setUserTurn(!board.getUserTurn());
		board.animateMove(coords.getX(),coords.getY());
		takingTurn=false;
	}
}
