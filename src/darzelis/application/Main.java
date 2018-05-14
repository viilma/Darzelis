package darzelis.application;
	
import darzelis.view.Login;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Login login = new Login(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
