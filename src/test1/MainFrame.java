package test1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainFrame extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		GamePane game=new GamePane();
		Pane pane=new Pane();
		pane.getChildren().add(game);
		
		Scene scene=new Scene(pane,Config.SWIDTH,Config.SHEIGHT);
		primaryStage.setScene(scene);
		primaryStage.setTitle("รินฌ");
		primaryStage.setResizable(false);
		primaryStage.show();
		
		game.requestFocus();
		
	}

}
