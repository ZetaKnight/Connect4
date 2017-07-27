package application;

import java.io.Serializable;

public class Coordinates implements Serializable{
	
	private int x;
	private int y;
	
	private double heuristicValue;

	public Coordinates(){
	}
	
	public Coordinates(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public double getHeuristicValue() {
		return heuristicValue;
	}

	public void setHeuristicValue(double heuristicValue) {
		this.heuristicValue = heuristicValue;
	}

}
