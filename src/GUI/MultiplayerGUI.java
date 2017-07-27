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

public class MultiplayerGUI extends GUIservice implements GUI{
	private Stage stage;
	private BorderPane bp = new BorderPane();
	private ImageView title = new ImageView();
	private Image homeIcon = new Image(getClass().getResourceAsStream(BoardGUI.RESOURCES+"homelogo.png"));
	private Image backIcon = new Image(getClass().getResourceAsStream(BoardGUI.RESOURCES+"backlogo.png"));
	private Button home = new Button("HOME", new ImageView(homeIcon));
	private Button back = new Button("BACK", new ImageView(backIcon));
	private Button twoPlayerLocal = new Button("Local Two Player");
	private Button onlineMultiPlayer = new Button("Online Two Player");
	private Scene scene = new Scene(bp, 800, 800);
	private HBox header = new HBox();
	private HBox footer = new HBox();
	private VBox content = new VBox();
	
	private MenuGUI menuGUI;
	private BoardGUI boardGUI;
	private GUIservice gui;
	
	public MultiplayerGUI(MenuGUI menuGUI, GUIservice gui){
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
		content.getChildren().addAll(onlineMultiPlayer, twoPlayerLocal);
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

	private void setSize(int size){
		if(size == MenuGUI.SMALL){
			onlineMultiPlayer.getStyleClass().add("buttons-1");
			twoPlayerLocal.getStyleClass().add("buttons-1");
		}
		if(size == MenuGUI.MEDIUM){
			onlineMultiPlayer.getStyleClass().add("buttons-2");
			twoPlayerLocal.getStyleClass().add("buttons-2");	
		}
		if(size == MenuGUI.LARGE){
			onlineMultiPlayer.getStyleClass().add("buttons-3");
			twoPlayerLocal.getStyleClass().add("buttons-3");
		}
		
	}
	
	private void setActionListener(){
		EventHandler<Event> eventHandler = new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {
				if(event.getSource().equals(onlineMultiPlayer)){
					boardGUI=new BoardGUI(menuGUI, MultiplayerGUI.this, Board.ONLINEMULTIPLAYER, 0);
				}
				if(event.getSource().equals(twoPlayerLocal)){
					boardGUI=new BoardGUI(menuGUI, MultiplayerGUI.this, Board.TWOPLAYER, 0);
				}
				if(event.getSource().equals(home)){
					menuGUI.display(stage);
				}
				if(event.getSource().equals(back)){
					gui.displayPage(stage);
				}
			}
		};
		onlineMultiPlayer.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		twoPlayerLocal.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		home.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		back.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
	}
	
	@Override
	public void display(Stage stage){
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void displayPage(Stage stage) {
		stage.setScene(scene);
		stage.show();
	}
	

}
