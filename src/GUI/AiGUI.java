package GUI;

import application.AiSimulation;
import application.Board;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AiGUI extends GUIservice implements GUI{

	private Stage stage;
	private BorderPane bp = new BorderPane();
	private ImageView title = new ImageView();
	private Image homeIcon = new Image(getClass().getResourceAsStream(BoardGUI.RESOURCES+"homelogo.png"));
	private Image backIcon = new Image(getClass().getResourceAsStream(BoardGUI.RESOURCES+"backlogo.png"));
	private Button home = new Button("HOME", new ImageView(homeIcon));
	private Button back = new Button("BACK", new ImageView(backIcon));
	
	//form contents ---
	private ScrollPane sp = new ScrollPane();
	Alert alert = new Alert(AlertType.INFORMATION);
	private Button start = new Button("Start");
	private Image helpIcon = new Image(getClass().getResourceAsStream(BoardGUI.RESOURCES+"helplogo.png"));
	private Button help = new Button("?");
	private Text player1 = new Text("Player 1");
	private Text player2 = new Text("Player 2");
	private ToggleGroup aiAlg = new ToggleGroup();
	private Text subTitleAlg = new Text("Algorithms");
	private Text subTitleAlg2 = new Text("Algorithms");
	private Text subTitleDiff = new Text("Difficulty");
	private Text subTitleDiff2 = new Text("Difficulty");
	private RadioButton rb1 = new RadioButton("Standard");
	private RadioButton rb2 = new RadioButton("Smart");
	private ToggleGroup aiDiff = new ToggleGroup();
	private RadioButton rb3 = new RadioButton("Easy (depth: 4)");
	private RadioButton rb4 = new RadioButton("Medium (depth: 6)");
	private RadioButton rb5 = new RadioButton("Hard (depth: 8)");
	private RadioButton rb6 = new RadioButton("Very Hard (depth: 10)");
	
	private ToggleGroup aiAlg2 = new ToggleGroup();
	private RadioButton rb12 = new RadioButton("Standard");
	private RadioButton rb22 = new RadioButton("Smart");
	private ToggleGroup aiDiff2 = new ToggleGroup();
	private RadioButton rb32 = new RadioButton("Easy (depth: 4)");
	private RadioButton rb42 = new RadioButton("Medium (depth: 6)");
	private RadioButton rb52 = new RadioButton("Hard (depth: 8)");
	private RadioButton rb62 = new RadioButton("Very Hard (depth: 10)");
	//---
	private Scene scene = new Scene(bp, 800, 800);
	private HBox header = new HBox();
	private HBox footer = new HBox();
	private HBox content = new HBox();
	private VBox ai1 = new VBox();
	private VBox ai2 = new VBox();
	
	private MenuGUI menuGUI;
	private GUIservice gui;
	
	public AiGUI(MenuGUI menuGUI, GUIservice gui) {
		this.menuGUI = menuGUI;
		stage = this.menuGUI.getStage();
		this.gui = gui;
		
		setActionListener();
		
		//setting style sheet 
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		//setting header
		header.setAlignment(Pos.TOP_CENTER);
		title.setImage(new Image(getClass().getResourceAsStream("../resources/title.png")));
		header.getChildren().add(title);	
		
		//setting content 
		content.setSpacing(10d);
		
		//form for constructing type of Ais		
		//AI 1
		// options for creating the AI's alg.
		rb1.setToggleGroup(aiAlg);
		rb1.setSelected(true);
		rb2.setToggleGroup(aiAlg);
		
		// options for creating the the depth level for the AI
		rb3.setToggleGroup(aiDiff);
		rb3.setSelected(true);
		rb4.setToggleGroup(aiDiff);
		rb5.setToggleGroup(aiDiff);
		rb6.setToggleGroup(aiDiff);
		
		//AI 1
		// options for creating the AI's alg.
		rb12.setToggleGroup(aiAlg2);
		rb12.setSelected(true);
		rb22.setToggleGroup(aiAlg2);
		
		// options for creating the the depth level for the AI
		rb32.setToggleGroup(aiDiff2);
		rb32.setSelected(true);
		rb42.setToggleGroup(aiDiff2);
		rb52.setToggleGroup(aiDiff2);
		rb62.setToggleGroup(aiDiff2);
		
		sp.setContent(content);
		sp.getStyleClass().add("scroll-pane");
		
		ai1.getChildren().addAll(player1,subTitleAlg,rb1,rb2,subTitleDiff,rb3,rb4,rb5,rb6);
		ai2.getChildren().addAll(player2,subTitleAlg2,rb12,rb22,subTitleDiff2,rb32,rb42,rb52,rb62);
		content.getChildren().addAll(ai1,ai2,help,start);
		/*
		 * content styling 
		 */
		subTitleAlg.getStyleClass().add("sub-title-help");
		subTitleAlg2.getStyleClass().add("sub-title-help");
		subTitleDiff.getStyleClass().add("sub-title-help");
		subTitleDiff2.getStyleClass().add("sub-title-help");
		
		rb1.getStyleClass().add("radio-help");
		rb2.getStyleClass().add("radio-help");
		rb3.getStyleClass().add("radio-help");
		rb4.getStyleClass().add("radio-help");
		rb5.getStyleClass().add("radio-help");
		rb6.getStyleClass().add("radio-help");
		rb12.getStyleClass().add("radio-help");
		rb22.getStyleClass().add("radio-help");
		rb32.getStyleClass().add("radio-help");
		rb42.getStyleClass().add("radio-help");
		rb52.getStyleClass().add("radio-help");
		rb62.getStyleClass().add("radio-help");
		
		player1.getStyleClass().add("player1");
		player2.getStyleClass().add("player2");
		ai1.getStyleClass().add("form");
		ai2.getStyleClass().add("form");
		help.getStyleClass().add("buttons-1");
		start.getStyleClass().add("buttons-2");
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
		bp.setCenter(sp);
		bp.setBottom(footer);
		
		stage.setScene(scene);
		stage.show();
		
		
		alert.setTitle("Help");
		alert.setHeaderText("Information");
		String helpText = "Standard: AI uses Minimax algorithm \n\n"
				+ "Smart: An efficient algorithm, using Minimax with Alpha Beta pruning \n\n"
				+ "Depth: How far the algorithm will look ahead, to search for best move\n\n";
		
		alert.setContentText(helpText);
	}
	
	private void showHelpDialog(){
		alert.show();
	}
	
	private void setActionListener(){
		EventHandler<Event> eventHandler = new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {
				if(event.getSource().equals(start)){
					//AI 1
					int diffLvl = 0;
					if(rb3.isSelected())diffLvl=4;
					if(rb4.isSelected())diffLvl=6;
					if(rb5.isSelected())diffLvl=8;
					if(rb6.isSelected())diffLvl=10;
					
					//AI 2
					int diffLvl2 = 0;
					if(rb32.isSelected())diffLvl2=4;
					if(rb42.isSelected())diffLvl2=6;
					if(rb52.isSelected())diffLvl2=8;
					if(rb62.isSelected())diffLvl2=10;
					AiSimulation ai = new AiSimulation(rb1.isSelected(), diffLvl, rb12.isSelected(), diffLvl2);
					BoardGUI boardGUI = new BoardGUI(menuGUI, AiGUI.this, Board.AIVSAI, ai);
				}
				if(event.getSource().equals(help)){
					showHelpDialog();
				}
				
				if(event.getSource().equals(home)){
					menuGUI.display(stage);
				}
				if(event.getSource().equals(back)){
					gui.displayPage(stage);
				}
			}
		};
		home.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		back.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		start.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
		help.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
	}
	
	@Override
	public void display(Stage stage) {
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void displayPage(Stage stage) {
		stage.setScene(scene);
		stage.show();
	}

}
