package darzelis.view;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import darzelis.controller.Validation;
import darzelis.model.User;
import darzelis.model.UserDao;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login {
		private Stage primaryStage;
			
		public Login(Stage primaryStage) {
			
			this.primaryStage = primaryStage;
			
			addElementsToScene();
		
			primaryStage.show();
		}

		private void addElementsToScene() {
			
			primaryStage.setTitle("Vaikų registravimo sistema");
			primaryStage.setResizable(false);
			primaryStage.centerOnScreen();
			
			BorderPane bpRoot = new BorderPane();

			Scene scene = new Scene(bpRoot,310,250);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			
			Label lblUserName = new Label("Prisijungimo vardas:");
			TextField tfUserName = new TextField();
			Label lblPassword = new Label("Slaptažodis");
			PasswordField pfPassword = new PasswordField();
			Text txtLogin = new Text("Prisijungimas");
			txtLogin.setFont(Font.font("Garamond", FontWeight.BOLD, 24));
			Label lblMessage = new Label();
			
			Button btnLogin = new Button("Prisijungti");
			btnLogin.setMinWidth(100);
			btnLogin.setAlignment(Pos.CENTER);
				
			Button btnRegister = new Button("Registruotis");
			btnRegister.setMinWidth(100);
			btnRegister.setAlignment(Pos.CENTER);
			
			btnLogin.setId("buttons");
			btnRegister.setId("buttons");
						
			btnLogin.setOnAction((ActionEvent e)->{
				
				UserDao userDao = new UserDao();
				
				User user = userDao.getUser(tfUserName.getText().toString(), 
						pfPassword.getText().toString());
				
				if (user.getUsername() != null) {
					Dashboard dashBoard = new Dashboard(primaryStage, user);
				}else if(!Validation.isValidCredentials(tfUserName.getText().toString())){
					lblMessage.setText("Neteisingas vartotojo vardas");
					lblMessage.setTextFill(Color.RED);
				}else if(!Validation.isValidCredentials(pfPassword.getText().toString())){
					lblMessage.setText("Neteisingas slaptažodis");
					lblMessage.setTextFill(Color.RED);
				}else{
					lblMessage.setText("Blogi prisijungimo duomenys");
					lblMessage.setTextFill(Color.RED);
				}
			});
				
			btnRegister.setOnAction((ActionEvent e)->{
					Register register = new Register(primaryStage);
			});
			
			HBox hbLoginText = new HBox();
			hbLoginText.setPadding(new Insets(10,10,10,10));
			hbLoginText.setAlignment(Pos.CENTER);
			
			hbLoginText.getChildren().add(txtLogin);
			GridPane gpLogin = new GridPane();
			gpLogin.add(lblUserName, 0, 0);
			gpLogin.add(tfUserName, 1, 0);
			gpLogin.add(lblPassword, 0, 1);
			gpLogin.add(pfPassword, 1, 1);
			gpLogin.add(lblMessage, 1, 2);
			gpLogin.add(btnLogin, 1, 3);
			gpLogin.add(btnRegister, 1, 4);
			gpLogin.setPadding(new Insets(10,10,10,10));
			gpLogin.setVgap(10);
			gpLogin.setHgap(10);
			gpLogin.setAlignment(Pos.CENTER);
			
			//gpLogin.setId("gridPane");
			//bpRoot.setId("borderPane");
			
			txtLogin.setId("text");
						
			bpRoot.setTop(hbLoginText);
			bpRoot.setCenter(gpLogin);
			
			gpLogin.setId("gridPane");
			bpRoot.setId("borderPane");
			
			
			primaryStage.setScene(scene);
		}	
		
}

