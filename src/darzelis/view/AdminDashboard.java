package darzelis.view;

import darzelis.controller.Validation;
import darzelis.model.User;
import darzelis.model.UserDao;
import darzelis.model.Vaikas;
import darzelis.model.VaikasDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AdminDashboard {

	private Stage primaryStage;
	private BorderPane bpRoot;
	private Scene scene;
	
	private User user;
	
	private TextField tfVardas;
	private TextField tfPavarde;
	private TextField tfUsername;
	
	private RadioButton selectedRadioButton;
	private RadioButton rb1;
	private RadioButton rb2;
	private ToggleGroup toggleGroup;

	
	private ObservableList<User> data;
	
	private GridPane gpAnketa;
	
	public AdminDashboard(Stage primaryStage, User user) {
		this.primaryStage = primaryStage;
		this.user = user;
		bpRoot = new BorderPane();
		scene = new Scene(bpRoot,950, 450);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		primaryStage.setTitle("Vartotojų administravimas");
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		
		addElementsToScene();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void addElementsToScene() {
		tfVardas = new TextField();
		tfPavarde = new TextField();
		tfUsername = new TextField();
		
		Button btnSearchByName = new Button("Ieškoti pagal vardą");
		btnSearchByName.setMinWidth(150);	
		Button btnSearchBySurname = new Button("Ieškoti pagal pavardę");
		btnSearchBySurname.setMinWidth(150);
		Button btnSearchByGroup = new Button("Ieškoti pagal grupę");
		btnSearchByGroup.setMinWidth(150);
		
		Button btnDeleteUser = new Button("Ištrinti vartotoją");
		btnDeleteUser.setMinWidth(150);
		
		Button btnChangeUserlevel = new Button("Keisti vartotojo lygį");
		btnChangeUserlevel.setMinWidth(150);
		
		toggleGroup = new ToggleGroup();
		rb1 = new RadioButton("Vartotojas(1)");
		rb2 = new RadioButton("Administratorius(9)");
		
		rb1.setSelected(true);
		rb1.setToggleGroup(toggleGroup);
		rb2.setToggleGroup(toggleGroup);
		
		
		
		//Label lblChangeUserlevel = new Label("Vartotojo lygis");
		
		Label lblUserName = new Label("Prisijungęs vartotojas: ");
		Label lblPrisijunges = new Label();
		lblPrisijunges.setText(user.getUsername());
		Label lblEmail = new Label("Elektroninis paštas:");
		Label lblLoggedEmail = new Label();
		lblLoggedEmail.setText(user.getEmail());		

		Button btnBack = new Button("Grįžti");
		btnBack.setMinWidth(112);		
		Button btnLogout = new Button("Atsijungti");
		btnLogout.setMinWidth(112);
		Button btnChangeEmail = new Button("Keisti el.paštą");
		btnChangeEmail.setMinWidth(112);
		Button btnChangePassword = new Button("Keisti slaptažodį");
		btnChangePassword.setMinWidth(112);
		
		btnSearchByName.setId("buttons");
		btnSearchBySurname.setId("buttons");
		btnSearchByGroup.setId("buttons");
		btnDeleteUser.setId("buttons");
		btnChangeUserlevel.setId("buttons");
		btnLogout.setId("buttons");
		btnChangeEmail.setId("buttons");
		btnChangePassword.setId("buttons");
		btnBack.setId("buttons");
		
		
		Text txtAdminDashboard = new Text("Vartotojų administravimas ");
		txtAdminDashboard.setFont(Font.font("Garamond", FontWeight.BOLD, 30));
		txtAdminDashboard.setId("text");
		
		TableView table = new TableView();
		table.setEditable(true); //?
		TableColumn usernameCol = new TableColumn("Prisijungimo vardas");
		usernameCol.setMinWidth(80);
		usernameCol.setStyle("-fx-alignment: center");
		TableColumn vardasCol = new TableColumn("Vardas");
		vardasCol.setMinWidth(80);
		vardasCol.setStyle("-fx-alignment: center");
		TableColumn pavardeCol = new TableColumn("Pavardė");
		pavardeCol.setMinWidth(80);
		pavardeCol.setStyle("-fx-alignment: center");
		TableColumn userlevelCol = new TableColumn("Vartotojo lygis");
		userlevelCol.setMinWidth(20);
		userlevelCol.setStyle("-fx-alignment: center");
		TableColumn emailCol = new TableColumn("Elektroninis paštas");
		emailCol.setMinWidth(160);
		emailCol.setStyle("-fx-alignment: center");
		
		//TableColumn grupeCol = new TableColumn("Grupė");
		//grupeCol.setMinWidth(100);

		table.getColumns().addAll(usernameCol, vardasCol, pavardeCol, userlevelCol, emailCol);
		//table.setMaxHeight(350);

		usernameCol.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
		vardasCol.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
		pavardeCol.setCellValueFactory(new PropertyValueFactory<User,String>("surname"));
		userlevelCol.setCellValueFactory(new PropertyValueFactory<User,Integer>("userlevel"));
		emailCol.setCellValueFactory(new PropertyValueFactory<User,String>("email"));
				
		//grupeCol.setCellValueFactory(new PropertyValueFactory<Vaikas,String>("grupe"));
		
		data = FXCollections.observableArrayList();
		UserDao userDao = new UserDao();
		userDao.showUsers(data);
		table.setItems(data);
		
		HBox hbAdminDashboardText = new HBox();
		hbAdminDashboardText.getChildren().add(txtAdminDashboard);
		hbAdminDashboardText.setPadding(new Insets(10,10,10,10));
		hbAdminDashboardText.setAlignment(Pos.CENTER);
		
		GridPane gpUserlevel = new GridPane();
		gpUserlevel.add(rb1, 0, 0);
		gpUserlevel.add(rb2, 0, 1);
		
		
		gpAnketa = new GridPane();
		gpAnketa.add(tfVardas, 0, 0);
		gpAnketa.add(btnSearchByName, 1, 0);
		gpAnketa.add(tfPavarde, 0, 1);
		gpAnketa.add(btnSearchBySurname, 1, 1);
		
		gpAnketa.add(tfUsername, 0, 4);
		tfUsername.setPromptText("Prisijungimo vardas");
		gpAnketa.add(btnDeleteUser, 1, 4);
		
		gpAnketa.add(gpUserlevel, 0, 5);
		gpAnketa.add(btnChangeUserlevel, 1, 5);
		
		gpAnketa.setPadding(new Insets(20,0,10,20));
		gpAnketa.setVgap(10);
		gpAnketa.setHgap(10);
		
		
		GridPane gpLoggedUser = new GridPane();
		gpLoggedUser.add(lblUserName, 0,0);
		gpLoggedUser.add(lblPrisijunges, 1, 0);
		gpLoggedUser.add(lblEmail, 0, 1);
		gpLoggedUser.add(lblLoggedEmail, 1, 1);
		gpLoggedUser.add(btnChangeEmail, 0, 2);		
		gpLoggedUser.add(btnChangePassword, 1, 2);
		gpLoggedUser.add(btnBack, 2, 1);
		
		gpLoggedUser.add(btnLogout, 2, 2);		
		gpLoggedUser.setPadding(new Insets(100,10,10,10));
		gpLoggedUser.setVgap(10);
		gpLoggedUser.setHgap(10);
		
		GridPane gpLeft = new GridPane();
		gpLeft.add(gpAnketa, 0, 0);
		gpLeft.add(gpLoggedUser, 0, 2);
		
		gpLeft.setId("gridPane");
		bpRoot.setId("borderPane");
						
		bpRoot.setTop(hbAdminDashboardText);
		bpRoot.setLeft(gpLeft);
		bpRoot.setCenter(table);
		
		btnSearchByName.setOnAction((ActionEvent e)->{
			data = userDao.searchUserByName(tfVardas.getText().toString());
			table.getItems().clear();
			table.setItems(data);
			tfVardas.clear();
			if (data.size() == 0)
			{
				showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Info!", "Įrašas tokiu pavadinimu neegzistuoja");
			}
		});
		
		btnSearchBySurname.setOnAction((ActionEvent e)->{
			data = userDao.searchUserBySurName(tfPavarde.getText().toString());
			table.getItems().clear();
			table.setItems(data);
			tfPavarde.clear();
			if (data.size() == 0)
			{
				showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Info!", "Įrašas tokiu pavadinimu neegzistuoja");
				return;
			}
		});
		
		btnDeleteUser.setOnAction((ActionEvent e)->{
			table.getItems().clear();
			userDao.showUsers(data);
			boolean isUsername = false;
			for(int i=0; i<data.size(); i++)
			{
				if(data.get(i).getUsername().equals(tfUsername.getText().toString()))
				{
					isUsername = true;
					userDao.deleteUser(tfUsername.getText().toString());
					showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Info!", "Vartotojas sėkmingai ištrintas!");
					table.getItems().clear();
					userDao.showUsers(data);
					tfUsername.clear();
				}
			}
			if (!isUsername) {
				showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Info!", "Tokio vartotojo nėra!");
			}
		});
		
		btnChangeUserlevel.setOnAction((ActionEvent e)->{
			table.getItems().clear();
			userDao.showUsers(data);
			
			boolean isUsername = false;
			
			for(int i=0; i<data.size(); i++)
			{
				if(data.get(i).getUsername().equals(tfUsername.getText().toString())) {
					isUsername = true;
					
					int userlevel;
					selectedRadioButton = (RadioButton)toggleGroup.getSelectedToggle();
					if (selectedRadioButton.getText().toString().equals("Administratorius(9)")){
						userlevel = Dashboard.ADMIN_LEVEL;
					} else {
						userlevel = Dashboard.USER_LEVEL;
					}
					userDao.changeUserlevel(tfUsername.getText().toString(), userlevel);
										
					showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Info!", "Vartotojo lygis pakeistas!");
				}
			}
			if (!isUsername) {
				showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Info!", "Tokio vartotojo nėra!");
			}
						
			table.getItems().clear();
			
			userDao.showUsers(data);
		});
		
		btnLogout.setOnAction((ActionEvent e)->{
			Login login = new Login(primaryStage);
		});
		
		btnChangeEmail.setOnAction((ActionEvent e)->{
			ChangeEmail changeEmail = new ChangeEmail(primaryStage, user);
		});
		
		btnChangePassword.setOnAction((ActionEvent e)->{
			ChangePassword changePassword = new ChangePassword(primaryStage, user);
		});
		
		btnBack.setOnAction((ActionEvent e)->{
			Dashboard dashboard = new Dashboard(primaryStage, user);
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
