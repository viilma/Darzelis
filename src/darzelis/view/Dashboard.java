package darzelis.view;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import darzelis.controller.Validation;
import darzelis.model.User;
import darzelis.model.Vaikas;
import darzelis.model.VaikasDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Dashboard {
	private Stage primaryStage;
	private BorderPane bpRoot;
	private Scene scene;
	
	private User user;
	private Vaikas vaikas;
	private String uzsiemimai = "";
	
	private TextField tfId;
	private TextField tfVardas;
	private TextField tfPavarde;
	private TextField tfAk;
	
	private CheckBox cb1;
	private CheckBox cb2;
	private CheckBox cb3;
	private CheckBox cb4;
	private CheckBox cb5;
	
	private RadioButton selectedRadioButton;
	private RadioButton rb1;
	private RadioButton rb2;
	private RadioButton rb3;
	private ToggleGroup toggleGroup;

	private ObservableList<Vaikas> data;
	private GridPane gpAnketa;
	
	public final static int ADMIN_LEVEL = 9;
	public final static int USER_LEVEL = 1;
	
	Dashboard(Stage primaryStage, User user){
		this.primaryStage=primaryStage;
		this.user = user;
		
		bpRoot = new BorderPane();
		scene = new Scene(bpRoot,1250,650);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		this.primaryStage.setTitle("Anketa");
		this.primaryStage.setResizable(false);
		this.primaryStage.centerOnScreen();
		
		addElementsToScene();
		
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}

	private void addElementsToScene() {
		Label lblId = new Label("Id:");
		Label lblVardas = new Label("Vardas:");
		Label lblPavarde = new Label("Pavardė:");
		Label lblAk = new Label("Asmens kodas:");
		Label lblUgdymas = new Label("Ugdymas:");
		Label lblGrupe = new Label("Grupė:");
		Label lblUzsiemimai = new Label("Lankomi užsiėmimai:");
		
		Label lblUserName = new Label("Prisijungęs vartotojas: ");
		Label lblPrisijunges = new Label();
		lblPrisijunges.setText(user.getUsername());
		
		Label lblEmpty = new Label(" ");
			
		Label lblEmail = new Label("Elektroninis paštas:");
		Label lblLoggedEmail = new Label();
		lblLoggedEmail.setText(user.getEmail());
		
		tfId = new TextField();
		tfVardas = new TextField();
		tfPavarde = new TextField();
		tfAk = new TextField();
		
		toggleGroup = new ToggleGroup();
		rb1 = new RadioButton();
		rb2 = new RadioButton();
		rb3 = new RadioButton();
		
		rb1.setText("Ankstyvasis");
		rb2.setText("Ikimokyklinis");
		rb3.setText("Priešmokyklinis");
		
		rb1.setSelected(true);
		rb1.setToggleGroup(toggleGroup);
		rb2.setToggleGroup(toggleGroup);
		rb3.setToggleGroup(toggleGroup);
		
		ComboBox cbGrupe = new ComboBox();
		cbGrupe.getItems().addAll("Cypliukai","Paukšteliai","Ežiukai","Drugeliai", "Gandriukai", "Spinduliukai", "Pagrandukai", "Žiogeliai","Varpeliai", "Vyturiukai", "Žvirbliukai","Žiniukai");
		cbGrupe.setValue("Cypliukai");
		cbGrupe.setId("combobox");
		
		cb1 = new CheckBox();
		cb2 = new CheckBox();
		cb3 = new CheckBox();
		cb4 = new CheckBox();
		cb5 = new CheckBox();
		
		cb1.setSelected(true);
		cb1.setText("Šokiai");
		cb2.setText("Krepšinis");
		cb3.setText("Futbolas");
		cb4.setText("Keramika");
		cb5.setText("Karate");
		
		Button btnSearchById = new Button("Ieškoti pagal id");
		btnSearchById.setMinWidth(160);
		//btnSearchById.setDisable(true);
		Button btnSearchByName = new Button("Ieškoti pagal vardą");
		btnSearchByName.setMinWidth(160);	
		Button btnSearchBySurname = new Button("Ieškoti pagal pavardę");
		btnSearchBySurname.setMinWidth(160);
		Button btnSearchByPersonId = new Button("Ieškoti pagal a.k.");
		btnSearchByPersonId.setMinWidth(160);
		Button btnSearchByEducation = new Button("Ieškoti pagal ugdymą");
		btnSearchByEducation.setMinWidth(160);
		Button btnSearchByGroup = new Button("Ieškoti pagal grupę");
		btnSearchByGroup.setMinWidth(160);
		//Button btnSearchByTraining = new Button("Ieškoti pagal užsiėmimą");
		
		Button btnAdd = new Button("Pridėti");
		btnAdd.setMinWidth(80);
		Button btnDelete = new Button("Ištrinti");
		btnDelete.setMinWidth(80);
		Button btnUpdate = new Button("Atnaujinti");
		btnUpdate.setMinWidth(80);
		
		Button btnUsersAdministration = new Button("Vartotojai");
		btnUsersAdministration.setMinWidth(160);
		
		Button btnLogout = new Button("Atsijungti");
		btnLogout.setMinWidth(105);
		btnLogout.setAlignment(Pos.CENTER);
		Button btnChangeEmail = new Button("Keisti el.paštą");
		btnChangeEmail.setMinWidth(105);
		btnChangeEmail.setAlignment(Pos.CENTER);
		Button btnChangePassword = new Button("Keisti slaptažodį");
		btnChangePassword.setMinWidth(105);
		btnChangePassword.setAlignment(Pos.CENTER);
		
		btnSearchById.setId("buttons");
		btnSearchByName.setId("buttons");
		btnSearchBySurname.setId("buttons");
		btnSearchByPersonId.setId("buttons");
		btnSearchByEducation.setId("buttons");
		btnSearchByGroup.setId("buttons");
		btnAdd.setId("buttons");
		btnDelete.setId("buttons");
		btnUpdate.setId("buttons");
		btnUsersAdministration.setId("buttons");
		btnLogout.setId("buttons");
		btnChangeEmail.setId("buttons");
		btnChangePassword.setId("buttons");
			
		Text txtDashboard = new Text("Vaikų registravimo sistema");
		txtDashboard.setFont(Font.font("Garamond", FontWeight.BOLD, 30));
		txtDashboard.setId("text");
		
		TableView table = new TableView();
		table.setEditable(true); //?
		TableColumn idCol = new TableColumn("Id");
		idCol.setMinWidth(20);
		idCol.setStyle("-fx-alignment: center");
		TableColumn userCol = new TableColumn("Vartotojas");
		userCol.setMinWidth(100);
		userCol.setStyle("-fx-alignment: center");
		TableColumn vardasCol = new TableColumn("Vardas");
		vardasCol.setMinWidth(100);
		vardasCol.setStyle("-fx-alignment: center");
		TableColumn pavardeCol = new TableColumn("Pavardė");
		pavardeCol.setMinWidth(100);
		pavardeCol.setStyle("-fx-alignment: center");
		TableColumn akCol = new TableColumn("Asmens kodas");
		akCol.setMinWidth(100);
		akCol.setStyle("-fx-alignment: center");
		TableColumn ugdymasCol = new TableColumn("Ugdymas");
		ugdymasCol.setMinWidth(100);
		ugdymasCol.setStyle("-fx-alignment: center");
		TableColumn grupeCol = new TableColumn("Grupė");
		grupeCol.setMinWidth(100);
		grupeCol.setStyle("-fx-alignment: center");
		TableColumn uzsiemimaiCol = new TableColumn("Užsiėmimai");
		uzsiemimaiCol.setMinWidth(100);
		uzsiemimaiCol.setStyle("-fx-alignment: center");
		
		if(user.getUserlevel() == USER_LEVEL){//Vartotojas nemato
			table.getColumns().addAll(idCol,vardasCol,pavardeCol,akCol,ugdymasCol,grupeCol,uzsiemimaiCol);
		} else {//Adminas mato vartotojus
			table.getColumns().addAll(idCol,userCol,vardasCol,pavardeCol,akCol,ugdymasCol,grupeCol,uzsiemimaiCol);
		}
		//table.setMaxHeight(350);
		
		idCol.setCellValueFactory(new PropertyValueFactory<Vaikas,Integer>("id"));
		userCol.setCellValueFactory(new PropertyValueFactory<Vaikas,String>("user"));
		vardasCol.setCellValueFactory(new PropertyValueFactory<Vaikas,String>("vardas"));
		pavardeCol.setCellValueFactory(new PropertyValueFactory<Vaikas,String>("pavarde"));
		akCol.setCellValueFactory(new PropertyValueFactory<Vaikas,String>("ak"));
		ugdymasCol.setCellValueFactory(new PropertyValueFactory<Vaikas,String>("ugdymas"));
		grupeCol.setCellValueFactory(new PropertyValueFactory<Vaikas,String>("grupe"));
		uzsiemimaiCol.setCellValueFactory(new PropertyValueFactory<Vaikas,String>("uzsiemimai"));
		
		data = FXCollections.observableArrayList();
		VaikasDao vaikasDao = new VaikasDao();
		vaikasDao.showVaikai(data, user);
		table.setItems(data);
		
		GridPane gpMygtukai = new GridPane();
		gpMygtukai.add(btnAdd,0,0);
		gpMygtukai.add(btnDelete,1,0);
		gpMygtukai.add(btnUpdate,2,0);
		gpMygtukai.add(btnSearchByName,3,0);
		//gpMygtukai.setPadding(new Insets(10,10,10,10));
		gpMygtukai.setVgap(10);
		gpMygtukai.setHgap(10);
				
		
		HBox hbDashboardText = new HBox();
		hbDashboardText.getChildren().add(txtDashboard);
		hbDashboardText.setPadding(new Insets(10,10,10,10));
		hbDashboardText.setAlignment(Pos.CENTER);
		
		GridPane gpTop = new GridPane();  //ar cia yra prasme?
		gpTop.add(hbDashboardText, 0, 0);
		//gpTop.add(gpUserInfo, 1, 0);
		
		HBox hbUserInfo = new HBox(); //ar cia yra prasme?
		hbUserInfo.getChildren().add(gpTop);
		hbUserInfo.setAlignment(Pos.TOP_CENTER);
		
		
		GridPane gpUgdymas = new GridPane();
		gpUgdymas.add(rb1, 0, 0);
		gpUgdymas.add(rb2, 0, 1);
		gpUgdymas.add(rb3, 0, 2);
		
		GridPane gpUzsiemimai = new GridPane();
		gpUzsiemimai.add(cb1, 0, 0);
		gpUzsiemimai.add(cb2, 0, 1);
		gpUzsiemimai.add(cb3, 0, 2);
		gpUzsiemimai.add(cb4, 0, 3);
		gpUzsiemimai.add(cb5, 0, 4);
		
		gpAnketa = new GridPane();
		gpAnketa.add(lblId, 0, 0);
		gpAnketa.add(tfId, 1, 0);
		gpAnketa.add(btnSearchById, 2, 0);
		gpAnketa.add(lblVardas, 0, 1);
		gpAnketa.add(tfVardas, 1, 1);
		gpAnketa.add(btnSearchByName, 2, 1);
		gpAnketa.add(lblPavarde, 0, 2);
		gpAnketa.add(tfPavarde, 1, 2);
		gpAnketa.add(btnSearchBySurname, 2, 2);
		gpAnketa.add(lblAk, 0, 3);
		gpAnketa.add(tfAk, 1, 3);
		gpAnketa.add(btnSearchByPersonId, 2, 3);
		gpAnketa.add(lblUgdymas, 0, 4);
		gpAnketa.add(gpUgdymas, 1, 4);
		gpAnketa.add(btnSearchByEducation, 2, 4);
		gpAnketa.add(lblGrupe, 0, 5);
		gpAnketa.add(cbGrupe, 1, 5);
		gpAnketa.add(btnSearchByGroup, 2, 5);
		gpAnketa.add(lblUzsiemimai, 0, 6);
		gpAnketa.add(gpUzsiemimai, 1, 6);
		//gpAnketa.add(btnSearchByTraining, 2, 6);
		
		//gpAnketa.add(gpMygtukai, 0, 8, 2, 1);
		gpAnketa.add(gpMygtukai, 0, 8, 4, 1);
		gpAnketa.setPadding(new Insets(30,10,10,20));
		gpAnketa.setVgap(10);
		gpAnketa.setHgap(10);
		
		/*
		gpAnketa.add(lblUserName, 0, 11);
		gpAnketa.add(lblPrisijunges, 1, 11);
		gpAnketa.add(btnLogout, 1, 12);
		gpAnketa.add(btnChangeEmail, 0, 13);
		gpAnketa.add(btnChangePassword, 1, 13);
		*/
		
		GridPane gpLoggedUser = new GridPane();
		gpLoggedUser.add(lblUserName, 0,0);
		gpLoggedUser.add(lblPrisijunges, 1, 0);
		gpLoggedUser.add(lblEmail, 0, 1);
		gpLoggedUser.add(lblLoggedEmail, 1, 1);
		gpLoggedUser.add(btnChangeEmail, 0, 2);		
		gpLoggedUser.add(btnChangePassword, 1, 2);
		gpLoggedUser.add(btnLogout, 2, 2);		
		gpLoggedUser.setPadding(new Insets(90,5,10,10));
		gpLoggedUser.setVgap(10);
		gpLoggedUser.setHgap(10);
		if(user.getUserlevel() != USER_LEVEL){//Vartotojas nemato
			//gpLoggedUser.add(btnUsersAdministration, 2, 0);
			gpAnketa.add(btnUsersAdministration, 2, 8);
		}

		GridPane gpLeft = new GridPane();
		gpLeft.add(gpAnketa, 0, 0);
		gpLeft.add(gpLoggedUser, 0, 2);

		gpLeft.setId("gridPane");
		bpRoot.setId("borderPane");
				
		bpRoot.setTop(hbUserInfo);
		bpRoot.setLeft(gpLeft);
		bpRoot.setCenter(table);
		//bpRoot.setBottom(gpLoggedUser);
		
		btnAdd.setOnAction((ActionEvent e)->{
			if(vaikas_validate("add")){
				Vaikas addVaikas = new Vaikas(
					//Integer.parseInt(tfId.getText().toString()),
					user.getUsername(),
					tfVardas.getText().toString(),
					tfPavarde.getText().toString(),
					tfAk.getText().toString(),
					selectedRadioButton.getText().toString(),
					cbGrupe.getValue().toString(),
					uzsiemimai
				);
				try {
					vaikasDao.addVaikas(addVaikas);
				} catch (MySQLIntegrityConstraintViolationException ex) {
					showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Form Info!", "Toks įrašas jau egzistuoja");
				}
				// isvalyti table ir sudeti is duombazes su naujai ideta reiksme
				table.getItems().clear();
				vaikasDao.showVaikai(data,user);
				tfId.clear();
				tfVardas.clear();
				tfPavarde.clear();
				tfAk.clear();
			}
		});
		
		btnDelete.setOnAction((ActionEvent e)->{
			vaikas_validate("delete");
			table.getItems().clear();
			vaikasDao.showVaikai(data,user);
			boolean isExistingEntryIdByUser = false;
			for(int i=0;i<data.size();i++)
			{
				if(data.get(i).getId() == Integer.parseInt(tfId.getText().toString()))
				{
					isExistingEntryIdByUser = true;
					vaikasDao.deleteVaikas(Integer.parseInt(tfId.getText()));
					table.getItems().clear();
					vaikasDao.showVaikai(data,user);
					tfId.clear();
				}
			}
			if (!isExistingEntryIdByUser)
			{
				showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Form Info!", "Toks Id neegzistuoja");
			}
		});
		
		btnUpdate.setOnAction((ActionEvent e)->{
			if(vaikas_validate("update")) {
				//int id=0 ;
				Vaikas updateVaikas = new Vaikas(
						Integer.parseInt(tfId.getText().toString()),
						user.getUsername(),
						tfVardas.getText().toString(),
						tfPavarde.getText().toString(),
						tfAk.getText().toString(),
						selectedRadioButton.getText().toString(),
						cbGrupe.getValue().toString(),
						uzsiemimai
				);
				boolean isExistingEntryIdByUser = false;
				for (int i=0; i<data.size(); i++)
				{
					if(data.get(i).getId()==Integer.parseInt(tfId.getText()) )
					{
						isExistingEntryIdByUser = true;
						vaikasDao.updateVaikas(updateVaikas, user);
						table.getItems().clear();
						vaikasDao.showVaikai(data,user);
						tfId.clear();
						tfAk.clear();
						tfVardas.clear();
						tfPavarde.clear();
					}
				}
				if (!isExistingEntryIdByUser)
				{
					showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Form Info!", "Toks Id neegzistuoja");
				}
			}
		});
		
		btnSearchById.setOnAction((ActionEvent e)->{
			if (tfId.getText().isEmpty()) {
				table.getItems().clear();
				vaikasDao.showVaikai(data,user);
			} else if(!Validation.isValidId(tfId.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpAnketa.getScene().getWindow(), "Klaida!", "Neteisingas ID formatas");
				return;
			} else {
				data=vaikasDao.searchChildrenById (Integer.parseInt(tfId.getText()), user);
				table.getItems().clear();
				table.setItems(data);
				tfId.clear();
				if (data.size() == 0)
				{
					showAlert(Alert.AlertType.ERROR, gpAnketa.getScene().getWindow(), "Klaida!", "Įrašas tokiu id neegzistuoja");
				}
			}
		});
		
		btnSearchByName.setOnAction((ActionEvent e)->{
			data=vaikasDao.searchChildrenByName (tfVardas.getText().toString(), user);
			table.getItems().clear();
			table.setItems(data);
			tfVardas.clear();
			if (data.size() == 0)
			{
				showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Form Info!", "Įrašas tokiu pavadinimu neegzistuoja");
			}
		});
		
		btnSearchBySurname.setOnAction((ActionEvent e)->{
			data=vaikasDao.searchChildrenBySurname (tfPavarde.getText().toString(), user);
			table.getItems().clear();
			table.setItems(data);
			tfPavarde.clear();
			if (data.size() == 0)
			{
				showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Form Info!", "Įrašas tokiu pavadinimu neegzistuoja");
			}
		});
		
		btnSearchByPersonId.setOnAction((ActionEvent e)->{
			
			if (tfAk.getText().isEmpty()) {
				table.getItems().clear();
				vaikasDao.showVaikai(data,user);
			} else if(!Validation.isValidAk(tfAk.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpAnketa.getScene().getWindow(), "Form Klaida!", "Neteisingai įvestas asmens kodas");
				return;
			} else {
				data=vaikasDao.searchChildrenByPersonId (tfAk.getText().toString(), user);
				table.getItems().clear();
				table.setItems(data);
				 tfAk.clear();
				if (data.size() == 0)
				{
					showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Form Info!", "Įrašas tokiu asmens kodu neegzistuoja");
				}
			}
		});
		
		btnSearchByEducation.setOnAction((ActionEvent e)->{
			selectedRadioButton = (RadioButton)toggleGroup.getSelectedToggle();
			data=vaikasDao.searchChildrenByEducation(selectedRadioButton.getText().toString(), user);
			table.getItems().clear();
			table.setItems(data);	
			if (data.size() == 0)
			{
				showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Form Info!", "Tokių įrašų neegzistuoja");
				vaikasDao.showVaikai(data,user);
			}
		});
		
		btnSearchByGroup.setOnAction((ActionEvent e)->{
			data=vaikasDao.searchChildrenByGroup(cbGrupe.getValue().toString(), user);
			table.getItems().clear();
			table.setItems(data);	
			if (data.size() == 0)
			{
				showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Form Info!", "Tokių įrašų neegzistuoja");
				vaikasDao.showVaikai(data,user);
			}
		});
	
		btnUsersAdministration.setOnAction((ActionEvent e)->{
			AdminDashboard adminDashboard = new AdminDashboard(primaryStage, user);
		});
		/*
		btnSearchByTraining.setOnAction((ActionEvent e)->{
			uzsiemimai = "";
			String dance = "";
			String basketball = "";
			String football = "";
			String ceramics = "";
			String karate = "";
			
			if (cb1.isSelected()){
				dance = cb1.getText();
			}
			if (cb2.isSelected()){
				basketball = cb2.getText();
				//uzsiemimai = uzsiemimai + "\n"  + cb2.getText();
			}
			if (cb3.isSelected()) {
				football = cb3.getText();
				//uzsiemimai =  uzsiemimai + "\n" + cb3.getText();
			}
			if (cb4.isSelected()) {
				ceramics = cb4.getText();
				//uzsiemimai = uzsiemimai + "\n" + cb4.getText();
			}
			if (cb5.isSelected()){
				karate = cb5.getText();
				//uzsiemimai = uzsiemimai + "\n" + cb5.getText();
			}
			data=vaikasDao.searchChildrenByTraining (dance, basketball, football, ceramics, karate, user);
			table.getItems().clear();
			table.setItems(data);	
			if (data.size() == 0)
			{
				showAlert(Alert.AlertType.INFORMATION, gpAnketa.getScene().getWindow(), "Form Info!", "Tokių įrašų neegzistuoja");
			}
		});
		*/
		
		btnLogout.setOnAction((ActionEvent e)->{
			Login login = new Login(primaryStage);
		});
		
		btnChangeEmail.setOnAction((ActionEvent e)->{
			ChangeEmail changeEmail = new ChangeEmail(primaryStage, user);
		});
		
		btnChangePassword.setOnAction((ActionEvent e)->{
			ChangePassword changePassword = new ChangePassword(primaryStage, user);
		});
	}

	private boolean vaikas_validate(String action) {
		uzsiemimai = "";
		switch(action) {
		case "delete":
			if(!Validation.isValidId(tfId.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpAnketa.getScene().getWindow(), "Form Klaida!", "Neteisingas ID formatas");	
				return false;
			} else return true;
			
		case "search":
			if(!Validation.isValidNameSurnameForAdd(tfVardas.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpAnketa.getScene().getWindow(), "Form Klaida!", "Neteisingai įvestas vardas");
				return false;
			} else return true;
			
		default:
			if(!Validation.isValidId(tfId.getText().toString()) && action == "update"){
				showAlert(Alert.AlertType.ERROR, gpAnketa.getScene().getWindow(), "Form Klaida!", "Neteisingas ID formatas");
				return false;
			}else if(!Validation.isValidNameSurnameForAdd(tfVardas.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpAnketa.getScene().getWindow(), "Form Klaida!", "Neteisingai įvestas vardas");
				return false;
			}else if(!Validation.isValidNameSurnameForAdd(tfPavarde.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpAnketa.getScene().getWindow(), "Form Klaida!", "Netinkamai įvesta pavardė");
				return false;	
			}else if(!Validation.isValidAk(tfAk.getText().toString())){
				showAlert(Alert.AlertType.ERROR, gpAnketa.getScene().getWindow(), "Form Klaida!", "Netinkamai įvestas asmens kodas");
				return false;
			}else {
				selectedRadioButton = (RadioButton)toggleGroup.getSelectedToggle();
				
				if (cb1.isSelected()){
					uzsiemimai = cb1.getText() + "\n";
				}
				if (cb2.isSelected()){
						uzsiemimai = uzsiemimai + cb2.getText() + "\n";
				}
				if (cb3.isSelected()) {
					uzsiemimai =  uzsiemimai + cb3.getText() + "\n";
				}
				if (cb4.isSelected()) {
					uzsiemimai = uzsiemimai + cb4.getText() + "\n";
				}
				if (cb5.isSelected()){
					uzsiemimai = uzsiemimai + cb5.getText();
				}
			}	
		}
		
		return true;
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
