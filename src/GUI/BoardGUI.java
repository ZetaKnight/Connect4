package GUI;

import java.util.ArrayList;
import java.util.List;

import application.AiSimulation;
import application.Board;
import application.Coordinates;
import application.MinimaxAB;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.VLineTo;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BoardGUI extends GUIservice implements GUI {
	
	public static final String RESOURCES = "../resources/";
	
	private static final int TILE_SIZE = 100;
	private static final int TILE_GAP = 10;
	private static final int COLUMNS = 7;
	private static final int ROWS = 6;
	private static final Color RED = Color.rgb(230, 71, 92);
	private static final Color DARK_RED = Color.rgb(180, 0, 0);
	private static final Color YELLOW = Color.rgb(255, 190, 64);
	private static final Color DARK_YELLOW = Color.rgb(195, 145, 48);
	
	private Stage stage;
	private BorderPane bp = new BorderPane();
	private ImageView title = new ImageView();
	private Image homeIcon = new Image(getClass().getResourceAsStream(RESOURCES+"homelogo.png"));
	private Image undoIcon = new Image(getClass().getResourceAsStream(RESOURCES+"undologo.png"));
	private Image hintIcon = new Image(getClass().getResourceAsStream(RESOURCES+"hintlogo.png"));
	private Image backIcon = new Image(getClass().getResourceAsStream(RESOURCES+"backlogo.png"));
	private Button home = new Button("HOME", new ImageView(homeIcon));
	private Button back = new Button("BACK", new ImageView(backIcon));
	private Button moveBack = new Button("UNDO", new ImageView(undoIcon));
	private Button hint = new Button("HINT", new ImageView(hintIcon));
	private Scene scene = new Scene(bp, 1225, 900);
	private HBox header = new HBox();
	private HBox footer = new HBox();
	private Pane boardPane = new Pane();
	private StackPane boardPaneHolder = new StackPane();
	private HBox content = new HBox();
	private VBox leftBar = new VBox();
	private Text player1 = new Text("Player 1");
	private Text player2 = new Text("Player 2");
	private VBox rightBar = new VBox();
	private Label winningState = new Label();
	
	private boolean animation = false;
	
	private Board board;
	private MinimaxAB minimaxAB = new MinimaxAB();
	
	private MenuGUI menuGUI;
	private GUIservice gui;
	
	/*
	 * enable going a page back
	 */
	public BoardGUI(MenuGUI menuGUI, GUIservice gui, int gameMode, int difficulty){
		this.menuGUI = menuGUI;
		stage = this.menuGUI.getStage();
		this.gui = gui;
		
		//initialising board
		if(gameMode == Board.SINGLEPLAYER){
			board = new Board(this, gameMode, difficulty);
			setGUI(gameMode);
			stage.setScene(scene);
			stage.show();
		}else if(gameMode == Board.TWOPLAYER){
			board = new Board(this, gameMode, 0);
			setGUI(gameMode);
			stage.setScene(scene);
			stage.show();
		}else if(gameMode == Board.ONLINEMULTIPLAYER){
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			bp.setId("root2");
			bp.setCenter(new Label("Waiting for connection"));
			stage.setScene(scene);
			stage.show();
			board = new Board(this, gameMode, 0);
		}else if(gameMode == Board.AIVSAI){
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			bp.setId("root2");
		}
	}
	
	// online multiplayer
	public BoardGUI(MenuGUI menuGUI, int gameMode, int difficulty){
		this.menuGUI = menuGUI;
		stage = this.menuGUI.getStage();
		
		//initialising board
		if(gameMode == Board.SINGLEPLAYER){
			board = new Board(this, gameMode, difficulty);
			setGUI(gameMode);
			stage.setScene(scene);
			stage.show();
		}else if(gameMode == Board.TWOPLAYER){
			board = new Board(this, gameMode, 0);
			setGUI(gameMode);
			stage.setScene(scene);
			stage.show();
		}else if(gameMode == Board.ONLINEMULTIPLAYER){
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			bp.setId("root2");
			bp.setCenter(new Label("Waiting for connection"));
			stage.setScene(scene);
			stage.show();
			board = new Board(this, gameMode, 0);
		}	
	}	
	
	// computer verses computer game
	public BoardGUI(MenuGUI menuGUI, GUIservice gui, int gameMode, AiSimulation ai){
		this.menuGUI = menuGUI;
		stage = this.menuGUI.getStage();
		this.gui = gui;		
		setGUI(gameMode);		
		stage.setScene(scene);
		stage.show();
		board = new Board(this, gameMode, ai);
	}
	
	
	public void setGUI(int gameMode){
		//setting style sheet 
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		//setting header
		header.setAlignment(Pos.TOP_CENTER);
		title.setImage(new Image(getClass().getResourceAsStream(RESOURCES+"title.png")));
		header.getChildren().add(title);		
				
		//board
		Shape grid = createGrid();
		boardPane.getChildren().add(grid);
		if(gameMode != Board.AIVSAI)boardPane.getChildren().addAll(selection());
		
		//setting content
		content.setPadding(new Insets(10,10,10,10));
		boardPaneHolder.getChildren().add(boardPane);
		content.getChildren().add(boardPaneHolder);
		content.setAlignment(Pos.TOP_CENTER);
		
		//setting Left bar
		player1.getStyleClass().add("player1");
		player2.getStyleClass().add("player2");
		leftBar.setPadding(new Insets(10,10,10,10));
		leftBar.getChildren().add(player1);
		
		//setting Right bar
		rightBar.setSpacing(10d);
		home.getStyleClass().add("buttons-2");
		back.getStyleClass().add("buttons-2");
		moveBack.getStyleClass().add("buttons-2");
		hint.getStyleClass().add("buttons-2");
		rightBar.getChildren().addAll(home,back);
		if(gameMode == Board.SINGLEPLAYER || gameMode == Board.TWOPLAYER )rightBar.getChildren().addAll(moveBack,hint);
//		if(gameMode == Board.AIVSAI)rightBar.getChildren().addAll(back);
		rightBar.setAlignment(Pos.TOP_LEFT);
		setActionListener();
		
		//setting layout 
		bp.setPadding(new Insets(10,10,10,10));
		bp.setId("root2");
		bp.setTop(header);
		bp.setBottom(footer);
		bp.setLeft(leftBar);
		bp.setRight(rightBar);
		bp.setCenter(content);
	}
	
	private Shape createGrid(){
		Rectangle gridShape = new Rectangle(TILE_SIZE * (COLUMNS + 1), TILE_SIZE * (ROWS + 1));
		gridShape.setArcWidth(50); 
		gridShape.setArcHeight(50);
		Shape grid = gridShape;
		
		for(int rows = 0; rows < ROWS ; rows++){
			for(int cols = 0; cols < COLUMNS ; cols++){
				Circle circle = new Circle(TILE_SIZE / 2);
				circle.setCenterX(TILE_SIZE / 2);
				circle.setCenterY(TILE_SIZE / 2);
				circle.setTranslateX(cols * (TILE_SIZE + TILE_GAP) + TILE_SIZE/4);
				circle.setTranslateY(rows * (TILE_SIZE + TILE_GAP) + TILE_SIZE/4);
				
				grid = Shape.subtract(grid, circle);
			}
		}
		grid.setFill(Color.rgb(115, 138, 176));
		grid.setStrokeWidth(5);
		grid.setSmooth(true);
		grid.setStroke(Color.rgb(50, 50, 200));
		grid.getStyleClass().add("board");
		return grid;
	}
	
	private ArrayList<Rectangle> selection(){
		ArrayList<Rectangle> colHighlight = new ArrayList<Rectangle>();
		for(int cols = 0; cols < COLUMNS ; cols++){
			Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE * ROWS + TILE_SIZE);
			rect.setTranslateX(cols * (TILE_SIZE + TILE_GAP) + TILE_SIZE/4);
			rect.setId(String.valueOf(cols));
			colHighlight.add(rect);
			rect.setFill(Color.TRANSPARENT);
				
			if(!board.getWinningState() && !board.isGameDrawn()){

					rect.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<Event>() {
		
						@Override
						public void handle(Event event) {
							if(!board.getWinningState() && !board.isGameDrawn()){
								if(board.getUserTurn())rect.setFill(Color.rgb(255, 0, 0, 0.3));
								else rect.setFill(Color.rgb(255, 255, 0, 0.3));
							}
							
						}
					});
					rect.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<Event>() {
		
						@Override
						public void handle(Event event) {
							rect.setFill(Color.TRANSPARENT);
						}
					});
					rect.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
		
						@Override
						public void handle(Event event) {
							if(!animation){
								switch(board.getGameMode()){
								case Board.TWOPLAYER:
									if(!board.getWinningState())
										setCounter(Integer.valueOf(rect.getId()));
									break;
								case Board.SINGLEPLAYER:
									if(board.getUserTurn())
										setCounter(Integer.valueOf(rect.getId()));
									break;
								case Board.ONLINEMULTIPLAYER:
									if(board.getUserTurn())
										setCounter(Integer.valueOf(rect.getId()));
									break;
								}
							}
						}
					});		
				
				
			}
		}
		return colHighlight;
	}
	
	private void setCounter(int column){	
		try{
			int row = board.findRow(column);
			board.setCounter(column, row);
			board.setUserTurn(!board.getUserTurn());
			if(board.isWinner(column, row, !board.getUserTurn()))
				board.setWinningState(true);
			animateCounter(column, row);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void animateCounter(int column, int row){
		Circle circle = new Circle(TILE_SIZE / 2);
		
		//getting the previous player counter colour
		if(!board.getUserTurn()){
			circle.setFill(RED);
			circle.setStroke(DARK_RED);
			
			leftBar.getChildren().clear();
			leftBar.getChildren().add(player1);
		}
		else{
			circle.setFill(YELLOW);
			circle.setStroke(DARK_YELLOW);
			leftBar.getChildren().clear();
			leftBar.getChildren().add(player2);
		}
		circle.setStrokeWidth(20);
		Path path = new Path();
		path.getElements().add(
				new MoveTo(column * (TILE_SIZE + TILE_GAP) + TILE_SIZE/4 + (TILE_SIZE/2),
							0 * (TILE_SIZE + TILE_GAP) + TILE_SIZE/4));
		path.getElements().add(
				new VLineTo(row * (TILE_SIZE + TILE_GAP) + TILE_SIZE/4 + (TILE_SIZE/2)));
		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.millis((row+1)*150));
		pathTransition.setPath(path);
		pathTransition.setNode(circle);
		pathTransition.setCycleCount(1);
		boardPane.getChildren().addAll(circle);
		pathTransition.play();
		circle.toBack();
		
		//check if game has ended
		if(board.getWinningState() || board.isGameDrawn()){
			
			//prevents actions from occurring (undo bug)
			animation = true;
			
			//if not drawn and winning state is found, draw Connect 4
			if(!board.isGameDrawn()){
				List<Coordinates> allign = board.getWinningCoords(column, row);
				Line line = new Line(((allign.get(1).getX()) * (TILE_SIZE+TILE_GAP))+(TILE_SIZE/2)+(TILE_SIZE/4),
									((allign.get(1).getY()) * (TILE_SIZE+TILE_GAP))+(TILE_SIZE/2)+(TILE_SIZE/4),
									((allign.get(0).getX()) * (TILE_SIZE+TILE_GAP))+(TILE_SIZE/2)+(TILE_SIZE/4),
									((allign.get(0).getY()) * (TILE_SIZE+TILE_GAP))+(TILE_SIZE/2)+(TILE_SIZE/4));
				line.setStrokeWidth(10d);
				FadeTransition fadeTransition = new FadeTransition(Duration.millis(((row+1)*150)), line);
				fadeTransition.setFromValue(0);
				fadeTransition.setToValue(1);
				fadeTransition.play();
				boardPane.getChildren().add(line);
			}
			
			//on finished animation sequence
			pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event){
					gameEnded();
				}
			});
		}
		updatePlayerDisplay();
	}
	
	//display players turn
	public void updatePlayerDisplay(){
		leftBar.getChildren().clear();
		if(!board.getUserTurn()){
			leftBar.getChildren().add(player2);
		}else{
			leftBar.getChildren().add(player1);
		}
	}
	
	public void gameEnded(){
		if(board.isGameDrawn())board.setWinningState(false);
		else board.setWinningState(true);
		
		winningState.setId("winning-state");
		if(board.getWinningState()==true){
			if(board.getGameMode()==Board.SINGLEPLAYER){
				String win = board.getUserTurn()?"YOU LOST":"YOU WIN";
				winningState.setText(win);
			}else if(board.getGameMode()==Board.TWOPLAYER || board.getGameMode()==Board.AIVSAI){
				String win = board.getUserTurn()?"P2 WINS":"P1 WINS";
				winningState.setText(win);
			}else if(board.getGameMode()==Board.ONLINEMULTIPLAYER){
				String win = board.getUserTurn()?"YOU LOST":"YOU WIN";
				winningState.setText(win);
			}
		}else{
			winningState.setText("DRAW");
		}
		
		Rectangle rect = new Rectangle(boardPane.getWidth()-10,TILE_SIZE+10/2);
		rect.setFill(Color.rgb(0, 0, 0, 0.3));
		winningState.setTranslateX(boardPane.getWidth()/4);
		winningState.setTranslateY(boardPane.getHeight()/4);
		rect.setTranslateY(boardPane.getHeight()/4+20);
		nodeTransition(winningState);
		nodeTransition(rect);
		boardPane.getChildren().addAll(rect,winningState);
		animation = false;
		
	}
	
	private void setActionListener(){
		EventHandler<Event> eventHandler = new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {
				if(event.getSource().equals(home)){
					menuGUI.display(stage);
				}	
				if(event.getSource().equals(back)){
					gui.displayPage(stage);
				}
				//undo function
				if(event.getSource().equals(moveBack)){
					//rewinds the game from terminal state
					if((board.getWinningState() || board.isGameDrawn()) && !animation){
						boardPane.getChildren().remove(boardPane.getChildren().size()-1);
						boardPane.getChildren().remove(boardPane.getChildren().size()-1);
						boardPane.getChildren().remove(boardPane.getChildren().size()-1);
						boardPane.getChildren().remove(0);//removes last move
						//if you lost and/or the AI had the last turn---
						if(board.getUserTurn() && board.getGameMode() == Board.SINGLEPLAYER){
							boardPane.getChildren().remove(0);//removes second last move
						}else if(!board.getUserTurn() && board.getGameMode() == Board.SINGLEPLAYER){
						//if you win and/or you had the last turn---
							board.setUserTurn(true);
							updatePlayerDisplay();
						}
						if(board.getGameMode() == Board.TWOPLAYER){
							board.setUserTurn(!board.getUserTurn());
							updatePlayerDisplay();
						}
						board.removeMove();
						board.setWinningState(false);
					}else{
						//rewinds the game appropriately for game mode
						if((board.getUserTurn() && board.getGameMode() == Board.SINGLEPLAYER) || 
							board.getGameMode() == Board.TWOPLAYER)
							if(!board.getMovesTaken().isEmpty()){
								boardPane.getChildren().remove(0);
								if(board.getGameMode()==Board.SINGLEPLAYER)boardPane.getChildren().remove(0);
								if(board.getGameMode()==Board.TWOPLAYER){
									board.setUserTurn(!board.getUserTurn());
									updatePlayerDisplay();
								}
								board.removeMove();
							}
					}					
				}	
				if(event.getSource().equals(hint) && !board.getWinningState() && !board.isGameDrawn()){
					if((!animation && board.getGameMode() == Board.SINGLEPLAYER && board.getUserTurn()) ||
						(!animation && board.getGameMode() == Board.TWOPLAYER )){
						
						animation = true;
						Coordinates coords;
						if(board.getUserTurn())
							coords = minimaxAB.searchMAX(board, board.getDifficulty(), board.getUserTurn());
						else
							coords = minimaxAB.searchMIN(board, board.getDifficulty(), !board.getUserTurn());
						Circle circle = new Circle(TILE_SIZE / 2);
						circle.setFill(Color.BLACK);
						circle.setStrokeWidth(20);
						circle.setTranslateX(coords.getX() * (TILE_SIZE + TILE_GAP) + TILE_SIZE/4 + (TILE_SIZE/2));
						circle.setTranslateY(coords.getY() * (TILE_SIZE + TILE_GAP) + TILE_SIZE/4 + (TILE_SIZE/2));
						FadeTransition fadeTrans = new FadeTransition(new Duration(350), circle);
						fadeTrans.setFromValue(0);
						fadeTrans.setToValue(100);
						fadeTrans.setCycleCount(3);
						fadeTrans.play();
						boardPane.getChildren().add(circle);
						circle.toBack();
						fadeTrans.setOnFinished(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								boardPane.getChildren().remove(0);
								animation = false;
							}
						});
					}
					
				}
			}
		};
		home.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		back.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		moveBack.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		hint.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
	}
	
	public Pane getBoardPane() {
		return boardPane;
	}

	public void setBoardPane(Pane boardPane) {
		this.boardPane = boardPane;
	}

	@Override
	public void displayPage(Stage stage){
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void display(Stage stage){
		stage.setScene(scene);
		stage.show();
	}
}
