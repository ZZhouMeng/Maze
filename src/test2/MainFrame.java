package test2;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainFrame extends Application {
	BorderPane pane = new BorderPane();
	GamePane game;
	private MenuItem miExit, miNew, miAuto,miHelp,miALL;
	private MenuItem bt1,bt2,bt3;
	private int n;

	@Override
	public void start(Stage primaryStage) throws Exception {
		MenuBar mb = new MenuBar();
		mb.setPrefHeight(30);
		Menu file = new Menu("游戏");
		file.setStyle("-fx-font-size: 18;");
		Menu help = new Menu("帮助");
		help.setStyle("-fx-font-size: 18;");
		Menu Select = new Menu("关卡");
		Select.setStyle("-fx-font-size: 18;");
		
		miNew = new MenuItem("重新开始");
		miAuto = new MenuItem("自动游戏");
		miALL=new MenuItem("所有路径");
		miExit = new MenuItem("退出");
		file.getItems().addAll(miNew, miAuto,miALL, miExit);
		
		
		miHelp=new MenuItem("帮助");
		help.getItems().add(miHelp);
		
		bt1 = new MenuItem("初级");
		bt2 = new MenuItem("中级");
		bt3=new MenuItem("终极");
		Select.getItems().addAll(bt1, bt2,bt3);
		
		mb.getMenus().addAll(file,Select,help);
		pane.setTop(mb);

		n=1;
		game = new GamePane();
		pane.setCenter(game);

		MyEvent h = new MyEvent();
		miExit.setOnAction(h);
		miNew.setOnAction(h);
		miAuto.setOnAction(h);
		miHelp.setOnAction(h);
		miALL.setOnAction(h);
		bt1.setOnAction(h);
		bt2.setOnAction(h);
		bt3.setOnAction(h);

		Scene scene = new Scene(pane, Config.SWIDTH + 10, Config.SHEIGHT + 50);
		primaryStage.setScene(scene);
		primaryStage.setTitle("迷宫");
		primaryStage.setResizable(false);
		primaryStage.show();

		game.requestFocus();

	}

	class MyEvent implements EventHandler {
		@Override
		public void handle(Event e) {
			Object obj = e.getSource();
			if (obj == miExit) {
				System.exit(0);
			} else if (obj == miNew) {
				game.startGame(0,n);
			} else if (obj == miAuto) {
				game.startGame(1,n);
			}else if (obj == miALL) {
				
				AllPath();
			}else if(obj==miHelp) {
				Help();
			}else if(obj==bt1) {
				n=1;
				game.startGame(0,n);
			}
			else if(obj==bt2) {
				n=2;
				game.startGame(0,n);
			}
			else if(obj==bt3) {
				n=3;
				game.startGame(0,n);
			}
		}
	}
	public void Help() {
		Stage stage = new Stage();
		BorderPane pane=new BorderPane();
		VBox vbox=new VBox(25);
		vbox.setAlignment(Pos.CENTER_LEFT);
		vbox.setPadding(new Insets(100));
		Label a=new Label("重新开始：小人回到起点重新开始游戏\n"
						+ "	         键盘控制小人上下移动");
		Label b=new Label("自动游戏：程序自动查找最短路径并运行");
		Label c=new Label("退出：退出游戏");
		vbox.getChildren().addAll(a,b,c);
		pane.setCenter(vbox);
		Scene scene=new Scene(pane,500,400);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("帮助");
	}
	public void AllPath() {
		Stage stage = new Stage();
		BorderPane pane=new BorderPane();
		Allpath allpath=new Allpath();
		Text text=new Text();
		text.setText(Allpath.list);
		text.setFill(Color.RED);
	    pane.setCenter(text);
		
		Scene scene=new Scene(pane, 600, 400);
		stage.setScene(scene);
		stage.show();
		stage.setTitle("所有路径");
	}
}
