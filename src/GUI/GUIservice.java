package GUI;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class GUIservice {

	public abstract void displayPage(Stage stage);
	
	public void nodeTransition(Node e){
		FadeTransition fadeTrans;
		fadeTrans = new FadeTransition(new Duration(3000), e);
		fadeTrans.setFromValue(0);
		fadeTrans.setToValue(100);
		fadeTrans.setCycleCount(1);
		fadeTrans.play();
	}
	
}
