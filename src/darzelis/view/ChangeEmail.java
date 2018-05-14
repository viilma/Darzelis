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

public class ChangeEmail {
	private BorderPane root;
	private Stage primaryStage;
	private String username;
	private User user;
		
	ChangeEmail(Stage primaryStage, User user){
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
		primaryStage.setTitle("Elektroninio pašto keitimas");//
		primaryStage.setResizable(false);//
		primaryStage.centerOnScreen();//
		
		Text txtChangeEmail = new Text("Elektroninio pašto keitimas");
		txtChangeEmail.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
		
		Label lblNewEmail = new Label("Naujas el.paštas:");
		TextField tfNewEmail = new TextField();
		
		Label lblRepeatNewEmail = new Label("Pakartokite el.paštą:");
		TextField tfRepeatNewEmail = new TextField();

		Label lblPassword = new Label("Slaptažodis:");
		PasswordField pfPassword = new PasswordField ();
		
		Button btnChangeEmail = new Button("Pakeisti el.paštą");
		btnChangeEmail.setMinWidth(100);
		btnChangeEmail.setAlignment(Pos.CENTER);
		
		HBox hbChangeEmailText = new HBox();
		hbChangeEmailText.getChildren().add(txtChangeEmail);
		hbChangeEmailText.setPadding(new Insets(10,10,10,10));
		hbChangeEmailText.setAlignment(Pos.CENTER);
		
		GridPane gpChangeEmail = new GridPane();

		gpChangeEmail.add(lblNewEmail,0,0);
		gpChangeEmail.add(tfNewEmail,1,0);
		gpChangeEmail.add(lblRepeatNewEmail,0,1);
		gpChangeEmail.add(tfRepeatNewEmail,1,1);
		gpChangeEmail.add(lblPassword,0,2);
		gpChangeEmail.add(pfPassword,1,2);
		gpChangeEmail.add(btnChangeEmail, 1, 3);
		gpChangeEmail.setPadding(new Insets(10,10,10,10));
		gpChangeEmail.setVgap(10);
		gpChangeEmail.setHgap(10);
		gpChangeEmail.setAlignment(Pos.CENTER);
		
		//susiejimas su style.css
		gpChangeEmail.setId("gridPane");
		root.setId("borderPane");
		btnChangeEmail.setId("buttons");
		btnChangeEmail.setId("text");
		
		root.setTop(hbChangeEmailText);
		root.setCenter(gpChangeEmail);
		
		btnChangeEmail.setOnAction((ActionEvent e)->{
			if(!Validation.isValidEmail(tfNewEmail.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpChangeEmail.getScene().getWindow(), "Klaida!", "Įveskite naują el.paštą");
				return;
			}
			if(!tfNewEmail.getText().toString().equals(tfRepeatNewEmail.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpChangeEmail.getScene().getWindow(), "Klaida!", "Elektroninis paštas nesutampa");
				return;
			}
			if(pfPassword.getText().toString().equals(user.getPassword())){
				UserDao userDao = new UserDao();
				try {
					userDao.changeEmail(tfNewEmail.getText().toString(), user);
					showAlert(Alert.AlertType.INFORMATION, gpChangeEmail.getScene().getWindow(), "Info", "Elektronis paštas sėkmingai pakeistas!");
					user.setEmail(tfNewEmail.getText().toString());
					Dashboard dashboard = new Dashboard(primaryStage, user);
				} catch (MySQLIntegrityConstraintViolationException e1) {
					showAlert(Alert.AlertType.ERROR, gpChangeEmail.getScene().getWindow(), "Klaida!", "Toks el.paštas jau yra duomenų bazėje!");
					//e1.printStackTrace();
				}				
			} else {
				showAlert(Alert.AlertType.ERROR, gpChangeEmail.getScene().getWindow(), "Klaida!", "Blogai įvestas slaptažodis!");
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
