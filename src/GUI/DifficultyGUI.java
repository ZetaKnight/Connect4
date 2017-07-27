package GUI;

import application.Board;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DifficultyGUI extends GUIservice implements GUI{

	private Stage stage;
	private BorderPane bp = new BorderPane();
	private ImageView title = new ImageView();
	private Image homeIcon = new Image(getClass().getResourceAsStream(BoardGUI.RESOURCES+"homelogo.png"));
	private Image backIcon = new Image(getClass().getResourceAsStream(BoardGUI.RESOURCES+"backlogo.png"));
	private Button home = new Button("HOME", new ImageView(homeIcon));
	private Button back = new Button("BACK", new ImageView(backIcon));
	private Button easy = new Button("Easy");
	private Button medium = new Button("Medium");
	private Button hard = new Button("Hard");
	private Button veryHard = new Button("Very Hard");
	private Scene scene = new Scene(bp, 800, 800);
	private HBox header = new HBox();
	private HBox footer = new HBox();
	private VBox content = new VBox();
	
	private BoardGUI boardGUI;
	private MenuGUI menuGUI;
	private GUIservice gui;
	
	public DifficultyGUI(MenuGUI menuGUI, GUIservice gui) {
		this.menuGUI = menuGUI;
		stage = this.menuGUI.getStage();
		this.gui = gui;
		
		//setting text size
		setSize();
		setActionListener();
		
		//setting style sheet 
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		//setting header
		header.setAlignment(Pos.TOP_CENTER);
		title.setImage(new Image(getClass().getResourceAsStream("../resources/title.png")));
		header.getChildren().add(title);	
		
		//setting content 
		content.setSpacing(10d);
		content.getChildren().addAll(easy, medium, hard, veryHard);
		content.setId("content");
		content.setAlignment(Pos.TOP_CENTER);

		//setting footer
		home.getStyleClass().add("buttons-2");
		back.getStyleClass().add("buttons-2");
		footer.setSpacing(10d);
		footer.getChildren().addAll(back,home);
		footer.setAlignment(Pos.TOP_LEFT);
		
		//setting layout 
		bp.setPadding(new Insets(10,10,10,10));
		bp.setId("root");
		bp.setTop(header);
		bp.setCenter(content);
		bp.setBottom(footer);
		
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	private void setActionListener(){
		EventHandler<Event> eventHandler = new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {
				if(event.getSource().equals(easy)){
					boardGUI=new BoardGUI(menuGUI, DifficultyGUI.this, Board.SINGLEPLAYER, Board.EASY);
				}
				if(event.getSource().equals(medium)){
					boardGUI=new BoardGUI(menuGUI, DifficultyGUI.this, Board.SINGLEPLAYER, Board.MEDIUM);
				}
				if(event.getSource().equals(hard)){
					boardGUI=new BoardGUI(menuGUI, DifficultyGUI.this, Board.SINGLEPLAYER, Board.HARD);
				}
				if(event.getSource().equals(veryHard)){
					boardGUI=new BoardGUI(menuGUI, DifficultyGUI.this, Board.SINGLEPLAYER, Board.VERY_HARD);
				}
				if(event.getSource().equals(home)){
					menuGUI.display(stage);
				}
				if(event.getSource().equals(back)){
					gui.displayPage(stage);
				}
			}
		};
		easy.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		medium.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		hard.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		veryHard.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		home.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		back.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
	}
	
	private void setSize(){
		easy.getStyleClass().add("buttons-3");
		medium.getStyleClass().add("buttons-3");
		hard.getStyleClass().add("buttons-3");
		veryHard.getStyleClass().add("buttons-3");
	}
	
	@Override
	public void displayPage(Stage stage) {
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void display(Stage stage) {
		stage.setScene(scene);
		stage.show();
	}

}
