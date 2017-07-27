package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Server {
	
	private static List<Player> players = new ArrayList<Player>();
	
	public static void main(String args[]){
		
		try{
			ServerSocket ss = new ServerSocket(9070);
			System.out.println("Ready to connect");
			
			while(true){				
				Socket incomingPlayer = ss.accept();
				
				if(!players.isEmpty()){
					Player p1 = players.get(0);
					players.remove(p1);
					new Thread(new PlayerHandler(p1, new Player(incomingPlayer))).start();
					
				}else{
					players.add(new Player(incomingPlayer));
				}
				
			}
		}catch(Exception e){
			System.out.println("Server error");
		}
	}

	
}
