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

public class Register {
	private BorderPane root;
	private Stage primaryStage;
		
	Register(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.root = new BorderPane(); 
		
		addElementsToScene();
		
		primaryStage.show();
	}

	private void addElementsToScene(){
			
		Scene scene = new Scene(root,420,340);	//300*250
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			
		primaryStage.setScene(scene); //
		primaryStage.setTitle("Naujo vartotojo registravimas");//
		primaryStage.setResizable(false);//
		primaryStage.centerOnScreen();//
		
		Text txtRegister = new Text("Vartotojo registracija");
		txtRegister.setFont(Font.font("Garamond", FontWeight.BOLD, 24));
		
		Label lblName = new Label("Vardas");
		TextField tfName = new TextField();
		
		Label lblSurname = new Label("Pavardė");
		TextField tfSurname = new TextField();	
		
		Label lblEmail = new Label("El.paštas:");
		TextField tfEmail = new TextField();
		
		Label lblUserName = new Label("Prisijungimo vardas:");
		TextField tfUserName = new TextField();
		
		Label lblPassword = new Label("Slaptažodis:");
		PasswordField pfPassword = new PasswordField();
		Label lblPasswordRepeat = new Label("Pakartokite slaptažodį:");
		PasswordField pfPasswordRepeat = new PasswordField();
		
		Button btnRegister = new Button("Registruotis");
		btnRegister.setMinWidth(100);
		btnRegister.setAlignment(Pos.CENTER);
		
		Button btnBack = new Button("Grįžti");
		btnBack.setMinWidth(100);
		btnBack.setAlignment(Pos.CENTER);
				
		HBox hbRegisterText = new HBox();
		hbRegisterText.getChildren().add(txtRegister);
		hbRegisterText.setPadding(new Insets(10,10,10,10));
		hbRegisterText.setAlignment(Pos.CENTER);
		
		GridPane gpRegister = new GridPane();
		gpRegister.add(lblName, 0, 0);
		gpRegister.add(tfName, 1, 0);
		gpRegister.add(lblSurname, 0, 1);
		gpRegister.add(tfSurname, 1, 1);
		gpRegister.add(lblEmail,0,2);
		gpRegister.add(tfEmail,1,2);
		gpRegister.add(lblUserName,0,3);
		gpRegister.add(tfUserName,1,3);
		gpRegister.add(lblPassword,0,4);
		gpRegister.add(pfPassword,1,4);
		gpRegister.add(lblPasswordRepeat,0,5);
		gpRegister.add(pfPasswordRepeat,1,5);
		gpRegister.add(btnRegister, 1, 6);
		gpRegister.add(btnBack, 0,6);
		gpRegister.setPadding(new Insets(10,10,10,10));
		gpRegister.setVgap(10);
		gpRegister.setHgap(10);
		gpRegister.setAlignment(Pos.CENTER);
		
		//susiejimas su style.css
		//gpRegister.setId("gridPane");
		root.setId("borderPane");
		btnRegister.setId("buttons");
		btnBack.setId("buttons");
		
		txtRegister.setId("text");
		gpRegister.setId("gridPane");
		root.setId("borderPane");
		
		
		root.setTop(hbRegisterText);
		root.setCenter(gpRegister);
		
		btnBack.setOnAction((ActionEvent e)->{
			Login login = new Login(this.primaryStage);
		});
				
		btnRegister.setOnAction((ActionEvent e)->{
			if(!Validation.isValidNameSurnameForAdd(tfName.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpRegister.getScene().getWindow(), "Klaida!", "Įveskite vardą");
				return;
			} else if(!Validation.isValidNameSurnameForAdd(tfSurname.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpRegister.getScene().getWindow(), "Klaida!", "Įveskite pavardę");
				return;
			} else if(!Validation.isValidEmail(tfEmail.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpRegister.getScene().getWindow(), "Klaida!", "Įveskite el. paštą");
				return;				
			} else if(!Validation.isValidCredentials(tfUserName.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpRegister.getScene().getWindow(), "Klaida!", "Netinkamas prisijungimo vardas");
				return;

			} else if(!Validation.isValidCredentials(pfPassword.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpRegister.getScene().getWindow(), "Klaida!", "Netinkamas slaptažodis!");
				return;
			} else if(!pfPassword.getText().toString().equals(pfPasswordRepeat.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpRegister.getScene().getWindow(), "Klaida!", "Slaptažodžiai nesutampa");
				return;
			} else {
				User user = new User(tfUserName.getText().toString(), tfName.getText().toString(), tfSurname.getText().toString(), pfPassword.getText().toString(),1,tfEmail.getText().toString());
				UserDao userDao = new UserDao();
				try {
					userDao.addUser(user);
					showAlert(Alert.AlertType.INFORMATION, gpRegister.getScene().getWindow(), "", "Registracija sėkminga!");
					Login login = new Login(primaryStage);
				} catch (MySQLIntegrityConstraintViolationException e1) {
					showAlert(Alert.AlertType.INFORMATION, gpRegister.getScene().getWindow(), "Form Info!", "Toks vartotojo vardas arba el.paštas jau egzistuoja!");
					//e1.printStackTrace();
				}
			}
			//int id, String name, String surname, String username, String password, int userlevel, String email
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
