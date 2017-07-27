package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import GUI.MenuGUI;

public class Main extends Application {
	
	private MenuGUI menu;
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Connect 4");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/logo.png")));
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
		menu = new MenuGUI(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
