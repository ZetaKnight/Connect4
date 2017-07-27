package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PlayerHandler implements Runnable{

	//for testing rounds of connection
	private static int countOfIterations = 0;
	
	private Player p1;
	private Player p2;
	private Socket s1;
	private Socket s2;
	
	public PlayerHandler(Player p1, Player p2){
		this.p1 = p1;
		this.p2 = p2;
		this.s1 = p1.getSocket();
		this.s2 = p2.getSocket();
	}
	
	public PlayerHandler(Socket s1, Player p1){
		this.s1 = s1;
		this.p1 = p1;
	}
	
	public void addPlayer2(Socket s2, Player p2){
		this.s2 = s2;
		this.p2 = p2;
	}
	
	public void run(){
		try {
			ObjectOutputStream oosP1 = new ObjectOutputStream(s1.getOutputStream());
			ObjectOutputStream oosP2 = new ObjectOutputStream(s2.getOutputStream());
			ObjectInputStream oisP1 = new ObjectInputStream(s1.getInputStream());
			ObjectInputStream oisP2 = new ObjectInputStream(s2.getInputStream());
			oosP1.writeBoolean(true);
			oosP1.flush();
			oosP2.writeBoolean(false);
			oosP2.flush();
			while(true){
				countOfIterations++;
				int x = oisP1.readInt();
				int y = oisP1.readInt();
				oosP2.writeInt(x);
				oosP2.writeInt(y);
				oosP2.flush();

				x = oisP2.readInt();
				y = oisP2.readInt();
				oosP1.writeInt(x);
				oosP1.writeInt(y);
				oosP1.flush();
			}
			
		} catch (IOException e) {
//			e.printStackTrace();
			try {
				s1.close();
				s2.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

	public Socket getS1() {
		return s1;
	}

	public void setS1(Socket s1) {
		this.s1 = s1;
	}

	public Socket getS2() {
		return s2;
	}

	public void setS2(Socket s2) {
		this.s2 = s2;
	}	
	
}
