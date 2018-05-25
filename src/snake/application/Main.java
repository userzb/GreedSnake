package snake.application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.stage.Stage;
import snake.model.Snake;
import snake.view.MainPane;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;


public class Main extends Application {
	Thread thread = null;
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = new VBox();
			Pane mainPane = new AnchorPane(new Rectangle(30,10,10,10),new Rectangle(20,10,10,10),new Rectangle(10,10,10,10));
			mainPane.setStyle("-fx-border-color:#000000");
			mainPane.setPrefSize(100, 100);
			mainPane.setMaxSize(100, 100);
			mainPane.setMinSize(100, 100);
			root.getChildren().add(mainPane);
			Scene scene = new Scene(root,400,400);
			primaryStage.setTitle("贪食蛇");
			primaryStage.setScene(scene);
			primaryStage.show();
			Snake snake = new Snake(mainPane.getChildren());
			scene.setOnKeyPressed(e->{
				snake.changeDirection(e.getCode());
				System.out.println(e.getCode());
				System.out.println(snake.uDirection);
			});
			thread = new Thread(snake);
			thread.start();
			primaryStage.setOnCloseRequest(e->{
				if(!thread.isInterrupted()) {
					thread.interrupt();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
