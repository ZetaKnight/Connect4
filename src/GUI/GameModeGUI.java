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

public class GameModeGUI extends GUIservice implements GUI{

	private Stage stage;
	private BorderPane bp = new BorderPane();
	private ImageView title = new ImageView();
	private Image backIcon = new Image(getClass().getResourceAsStream(BoardGUI.RESOURCES+"backlogo.png"));
	private Button home = new Button("BACK", new ImageView(backIcon));
	private Button twoPlayer = new Button("Two Player");
	private Button singlePlayer = new Button("Single Player");
	private Button aiVSai = new Button("AI vs. AI");
	private Scene scene = new Scene(bp, 800, 800);
	private HBox header = new HBox();
	private HBox footer = new HBox();
	private VBox content = new VBox();
	
	private MenuGUI menuGUI;
	private DifficultyGUI difficultyGUI;
	private MultiplayerGUI multiplayerGUI;
	private AiGUI aiGUI;
	private GUIservice gui;
	
	public GameModeGUI(MenuGUI menuGUI, GUIservice gui){
		this.menuGUI = menuGUI;
		stage = this.menuGUI.getStage();
		this.gui = gui;
		
		//setting text size
		setSize(3);
		setActionListener();
		
		//setting style sheet 
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		//setting header
		header.setAlignment(Pos.TOP_CENTER);
		title.setImage(new Image(getClass().getResourceAsStream("../resources/title.png")));
		header.getChildren().add(title);	
		
		//setting content 
		content.setSpacing(10d);
		content.getChildren().addAll(singlePlayer, twoPlayer, aiVSai);
		content.setId("content");
		content.setAlignment(Pos.TOP_CENTER);

		//setting footer
		home.getStyleClass().add("buttons-2");
		footer.setSpacing(10d);
		footer.getChildren().add(home);
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
	
	private void setSize(int size){
		if(size == MenuGUI.SMALL){
			singlePlayer.getStyleClass().add("buttons-1");
			twoPlayer.getStyleClass().add("buttons-1");
			aiVSai.getStyleClass().add("buttons-1");
		}
		if(size == MenuGUI.MEDIUM){
			singlePlayer.getStyleClass().add("buttons-2");
			twoPlayer.getStyleClass().add("buttons-2");	
			aiVSai.getStyleClass().add("buttons-2");
		}
		if(size == MenuGUI.LARGE){
			singlePlayer.getStyleClass().add("buttons-3");
			twoPlayer.getStyleClass().add("buttons-3");
			aiVSai.getStyleClass().add("buttons-3");
		}
		
	}
	
	private void setActionListener(){
		EventHandler<Event> eventHandler = new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {
				if(event.getSource().equals(singlePlayer)){
					if(difficultyGUI == null)difficultyGUI = new DifficultyGUI(menuGUI, GameModeGUI.this);
					else difficultyGUI.display(stage);
				}
				if(event.getSource().equals(twoPlayer)){
					if(multiplayerGUI == null)multiplayerGUI = new MultiplayerGUI(menuGUI, GameModeGUI.this);
					else multiplayerGUI.display(stage);
				}
				if(event.getSource().equals(aiVSai)){
					if(aiGUI == null)aiGUI = new AiGUI(menuGUI, GameModeGUI.this);
					else aiGUI.display(stage);
				}
				if(event.getSource().equals(home)){
					menuGUI.display(stage);
				}
			}
		};
		singlePlayer.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		twoPlayer.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		aiVSai.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		home.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
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