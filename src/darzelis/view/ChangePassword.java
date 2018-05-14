package darzelis.view;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import darzelis.controller.Validation;
import darzelis.model.User;
import darzelis.model.UserDao;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ChangePassword {
	private BorderPane root;
	private Stage primaryStage;
	private String username;
	private User user;
		
	ChangePassword(Stage primaryStage, User user){
		this.primaryStage = primaryStage;
		this.root = new BorderPane();
		this.user = user;
		
		addElementsToScene();
		
		primaryStage.show();
	}

	private void addElementsToScene(){
			
		Scene scene = new Scene(root,420,250);	//300*250
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			
		primaryStage.setScene(scene); //
		primaryStage.setTitle("Slaptažodžio keitimas");//
		primaryStage.setResizable(false);//
		primaryStage.centerOnScreen();//
		
		Text txtChangePassword = new Text("Slaptažodžio keitimas");
		txtChangePassword.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
		
		Label lblPassword = new Label("Slaptažodis:");
		PasswordField pfPassword = new PasswordField ();		
		
		Label lblNewPassword = new Label("Naujas slaptažodis:");
		PasswordField pfNewPassword = new PasswordField();
		
		Label lblRepeatNewPassword = new Label("Pakartokite slaptažodį:");
		PasswordField pfRepeatNewPassword = new PasswordField();

		Button btnChangePassword = new Button("Pakeisti slaptažodį");
		btnChangePassword.setMinWidth(100);
		btnChangePassword.setAlignment(Pos.CENTER);
		
		HBox hbChangePasswordText = new HBox();
		hbChangePasswordText.getChildren().add(txtChangePassword);
		hbChangePasswordText.setPadding(new Insets(10,10,10,10));
		hbChangePasswordText.setAlignment(Pos.CENTER);
		
		GridPane gpChangePassword = new GridPane();

		gpChangePassword.add(lblPassword,0,0);
		gpChangePassword.add(pfPassword,1,0);
		gpChangePassword.add(lblNewPassword,0,1);
		gpChangePassword.add(pfNewPassword,1,1);
		gpChangePassword.add(lblRepeatNewPassword,0,2);
		gpChangePassword.add(pfRepeatNewPassword,1,2);
		gpChangePassword.add(btnChangePassword, 1, 3);
		gpChangePassword.setPadding(new Insets(10,10,10,10));
		gpChangePassword.setVgap(10);
		gpChangePassword.setHgap(10);
		gpChangePassword.setAlignment(Pos.CENTER);
		
		//susiejimas su style.css
		gpChangePassword.setId("gridPane");
		root.setId("borderPane");
		btnChangePassword.setId("buttons");
		btnChangePassword.setId("text");
		
		root.setTop(hbChangePasswordText);
		root.setCenter(gpChangePassword);
		
		btnChangePassword.setOnAction((ActionEvent e)->{
			if(!pfPassword.getText().toString().equals(user.getPassword())){
				showAlert(Alert.AlertType.ERROR, gpChangePassword.getScene().getWindow(), "Klaida!", "Blogai įvestas slaptažodis!");
				return;
			} else if(!Validation.isValidCredentials(pfNewPassword.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpChangePassword.getScene().getWindow(), "Klaida!", "Netinkamas slaptažodis!");
				return;
			} else if (pfNewPassword.getText().toString().equals(pfRepeatNewPassword.getText().toString())){
				UserDao userDao = new UserDao();
				userDao.changePassword(pfNewPassword.getText().toString(), user);
				showAlert(Alert.AlertType.INFORMATION, gpChangePassword.getScene().getWindow(), "Info", "Slaptažodis sėkmingai pakeistas!");
				user.setPassword(pfPassword.getText().toString());
				Dashboard dashboard = new Dashboard(primaryStage, user);				
			} else {				
				showAlert(Alert.AlertType.ERROR, gpChangePassword.getScene().getWindow(), "Klaida!", "Slaptažodžiai nesutampa!");
				return;
			}
		});
	}

	private void showAlert(Alert.AlertType alerType, Window owner, String title, String message){
		Alert alert = new Alert(alerType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}

}
