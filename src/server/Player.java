package server;

import java.net.Socket;

public class Player {

	private Integer id;// unique attribute to identify them
	private Boolean connected;// signify if they are in a game
	private Socket socket;
	
	public Player(Socket socket){
		this.socket = socket;
	}
	
	@Deprecated
	public Player(Integer id, Boolean connected){
		this.id = id;
		this.connected = connected;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Boolean getConnected() {
		return connected;
	}

	public void setConnected(Boolean connected) {
		this.connected = connected;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
