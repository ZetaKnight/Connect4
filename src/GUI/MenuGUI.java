package GUI;

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

public class MenuGUI extends GUIservice implements GUI{
	
	public static final int LARGE = 3;
	public static final int MEDIUM = 2;
	public static final int SMALL = 1;
	
	private Stage stage;
	
	private Button play = new Button("Play");
	private Button help = new Button("Help");
	private Button options = new Button("Options");
	
	private BorderPane bp = new BorderPane();
	private HBox header = new HBox();
	private VBox content= new VBox();
	private ImageView title = new ImageView();
	
	private Scene scene = new Scene(bp, 800, 800);
	
	private HelpGUI helpGUI;
	private GameModeGUI gameModeGUI;	
	
	public MenuGUI(Stage stage){
		this.stage = stage;
		
		//setting style sheet 
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		//setting text size
		setSize(3);
		setActionListener();
		
		//setting header
		header.setAlignment(Pos.TOP_CENTER);
		title.setImage(new Image(getClass().getResourceAsStream("../resources/title.png")));
		header.getChildren().add(title);
		
		//setting content 
		content.setSpacing(10d);
		content.getChildren().addAll(play, help);
		content.setId("content");
		content.setAlignment(Pos.TOP_CENTER);
		
		//setting layout 
		bp.setPadding(new Insets(10,10,10,10));
		bp.setId("root");
		bp.setTop(header);
		bp.setCenter(content);

		stage.setScene(scene);
		stage.show();
		
	}
	
	private void setSize(int size){
		if(size == SMALL){
			play.getStyleClass().add("buttons-1");
			help.getStyleClass().add("buttons-1");
			options.getStyleClass().add("buttons-1");
		}
		if(size == MEDIUM){
			play.getStyleClass().add("buttons-2");
			help.getStyleClass().add("buttons-2");
			options.getStyleClass().add("buttons-2");		
		}
		if(size == LARGE){
			play.getStyleClass().add("buttons-3");
			help.getStyleClass().add("buttons-3");
			options.getStyleClass().add("buttons-3");
		}
		
	}
	
	private void setActionListener(){
		EventHandler<Event> eventHandler = new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {
				if(event.getSource().equals(play)){
					if(gameModeGUI == null) gameModeGUI = new GameModeGUI(MenuGUI.this, MenuGUI.this);
					else gameModeGUI.display(stage);
				}
				if(event.getSource().equals(help)){
					if(helpGUI == null) helpGUI = new HelpGUI(MenuGUI.this, MenuGUI.this);
					else helpGUI.display(stage);
				}
			}
		};
		play.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		help.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
	}

	@Override
	public void display(Stage stage) {
		stage.setScene(scene);
		stage.show();
	}
	
	public Stage getStage(){
		return stage;
	}

	@Override
	public void displayPage(Stage stage) {
		stage.setScene(scene);
		stage.show();		
	}
	
}
