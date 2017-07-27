package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import GUI.BoardGUI;
import server.Player;

public class OnlineMultiplayer extends Thread{

	private static final String HOST = "localhost";
	private static final int PORT = 9070;
	
	private Board board;
	private BoardGUI boardGUI;	
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Timer timer;
	private TimerTask timerTask;
	
	public OnlineMultiplayer(Board board, BoardGUI boardGUI){
		this.board = board;
		this.boardGUI = boardGUI;
		
		timer = new Timer();
		timerTask = new TimerTask() {
			
			@Override
			public void run() {
				listenForPlayer();
			}
		};
		
		try {
			s = new Socket(InetAddress.getByName(HOST), PORT);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			boolean userTurn = ois.readBoolean();// the players turn
			this.board.setUserTurn(userTurn);	
			Platform.runLater(() -> { this.boardGUI.setGUI(Board.ONLINEMULTIPLAYER);});
			Platform.runLater(() -> { this.boardGUI.updatePlayerDisplay();});
			if(!userTurn){
				timer.schedule(timerTask, 1000);
			}

		} catch (IOException e) {
//			e.printStackTrace();
//			System.out.println("error in online multiplayer class");
			
		}
		
	}
	
	public void sendMove(Coordinates coord){
		try {
			oos.writeInt(coord.getX());
			oos.writeInt(coord.getY());
			oos.flush();
			timerTask = new TimerTask() {
				
				@Override
				public void run() {
					listenForPlayer();
				}
			};
			timer.schedule(timerTask,  1000);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listenForPlayer(){
		int x;
		int y;
		try {
			x = ois.readInt();
			y = ois.readInt();
			Coordinates coord = new Coordinates(x, y);
			board.setCounter(coord.getX(),coord.getY());			
			if(board.isWinner(coord.getX(), coord.getY(), board.getUserTurn())){
				board.setWinningState(true);
				s.close();
			}
			board.setUserTurn(!board.getUserTurn());
			board.animateMove(coord.getX(),coord.getY());
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
	
	
}
