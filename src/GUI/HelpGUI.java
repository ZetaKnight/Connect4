package GUI;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class HelpGUI extends GUIservice implements GUI{
	
	private Stage stage;
	private BorderPane bp = new BorderPane();
	private Scene scene = new Scene(bp, 800, 800);
	private ImageView title = new ImageView();
	private HBox header = new HBox();
	private HBox content = new HBox();
	private HBox footer = new HBox();
	private Image backIcon = new Image(getClass().getResourceAsStream(BoardGUI.RESOURCES+"backlogo.png"));
	private Button home = new Button("BACK", new ImageView(backIcon));
	
	private MenuGUI menuGUI;
	private GUIservice gui;
	
	public HelpGUI(MenuGUI menuGUI, GUIservice gui){
		this.menuGUI = menuGUI;
		stage = menuGUI.getStage();
		this.gui = gui;
		
		//setting style sheet 
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		//setting header
		header.setAlignment(Pos.TOP_CENTER);
		title.setImage(new Image(getClass().getResourceAsStream("../resources/title.png")));
		header.getChildren().add(title);
		
		//setting scroll-able content		
		ScrollPane sp = new ScrollPane();
		sp.setPadding(new Insets(50));
		sp.setContent(createContent());
		//setting content
		content.setAlignment(Pos.TOP_CENTER);
		content.getChildren().add(sp);
		
		//setting footer
		home.getStyleClass().add("buttons-2");
		footer.getChildren().add(home);
		footer.setAlignment(Pos.TOP_LEFT);
		setActionListener();
		
		//setting layout
		bp.setPadding(new Insets(10,10,10,10));
		bp.setTop(header);
		bp.setId("root");
		bp.setCenter(content);
		bp.setBottom(footer);
		
		stage.setScene(scene);
		stage.show();
	}
	
	private VBox createContent(){
		VBox spContentHolder = new VBox();
		Label l1 = new Label("...................................................................................."
				+ "............................................................................"
				+ "\nConnect 4 is a 2 player game, there are two types of counters,\n"
				+ "typically red and yellow, players select to be which of the two. \n");
		Label l2 = new Label("The game takes place on a vertical board where players take a turn \n"
				+ "each, stacking their counters. \n");
		Label l3 = new Label("The game is won when a player manages\n"
				+ "to align 4 counters together, this can be vertically, \n");
		Label l4 = new Label("horizontally \n");
		Label l5 = new Label("or diagonally. \n");
		Label l6 = new Label("Alternatively the game can be ended in stalemate \n"
				+ "when all counters are used, and there exists no winner.");
		
		spContentHolder.getChildren().addAll(
				l1,
				l2,
				l3,new ImageView(new Image(getClass().getResourceAsStream("../resources/diagonal_win.png"))),
				l4,new ImageView(new Image(getClass().getResourceAsStream("../resources/hor_win.png"))),
				l5,new ImageView(new Image(getClass().getResourceAsStream("../resources/ver_win.png"))),
				l6);
		spContentHolder.getStyleClass().add("help-text");
		return spContentHolder;
	}
	
	private void setActionListener(){
		EventHandler<Event> eventHandler = new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {
				if(event.getSource().equals(home)){
					menuGUI.display(stage);
				}				
			}
		};
		home.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
	}

	@Override
	public void display(Stage stage) {
		stage.setScene(scene);
		stage.show();	
	}

	@Override
	public void displayPage(Stage stage) {	}
}
