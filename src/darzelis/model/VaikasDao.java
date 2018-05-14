package darzelis.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import darzelis.view.Dashboard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VaikasDao {
	
	public void showVaikai(ObservableList<Vaikas> data, User user) {
		String query = "";
		if(user.getUserlevel() != Dashboard.USER_LEVEL){
			 query = "SELECT * FROM vaikai";	
		}else{
			String username = user.getUsername();
			query = "SELECT * FROM vaikai WHERE user='"+username+"'";	
		}
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement add = myConn.prepareStatement(query);
			ResultSet rs = add.executeQuery();
			while(rs.next()) {
				data.add(new Vaikas(
						rs.getInt("id"),
						rs.getString("user"),
						rs.getString("vardas"),
						rs.getString("pavarde"),
						rs.getString("ak"),
						rs.getString("ugdymas"),
						rs.getString("grupe"),
						rs.getString("uzsiemimai")				
						));
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}
	}

	public void addVaikas(Vaikas addVaikas) throws MySQLIntegrityConstraintViolationException {
		String sql = "INSERT INTO `vaikai`(`user`, `vardas`, `pavarde`, `ak`, `ugdymas`, `grupe`, `uzsiemimai`) VALUES (?,?,?,?,?,?,?)";
		try {
			//Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?useUnicode=true&characterEncoding=utf-8", "root", "");
			//jdbc:mysql:///dbname?useUnicode=true&characterEncoding=utf-8", "user", "pass"
			PreparedStatement add = myConn.prepareStatement(sql);
			add.setString(1, addVaikas.getUser());
			add.setString(2,addVaikas.getVardas());
			add.setString(3,addVaikas.getPavarde());
			add.setString(4,addVaikas.getAk());
			add.setString(5,addVaikas.getUgdymas());
			add.setString(6,addVaikas.getGrupe());
			add.setString(7,addVaikas.getUzsiemimai());
			add.execute();
			add.close();
		}catch(MySQLIntegrityConstraintViolationException e){
			throw new MySQLIntegrityConstraintViolationException();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteVaikas(int id) {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement delete = myConn.prepareStatement("DELETE FROM vaikai WHERE id = ?");
			delete.setInt(1, id);
			delete.executeUpdate();
			}catch(Exception exc){
				exc.printStackTrace();
		}
	}

	public void updateVaikas(Vaikas updateVaikas, User user) {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?useUnicode=true&characterEncoding=utf-8", "root", "");
			PreparedStatement update = myConn.prepareStatement("UPDATE vaikai SET vardas=?, pavarde=?, ak=?, ugdymas=?, grupe=?, uzsiemimai=? WHERE id=?");
			update.setString(1,updateVaikas.getVardas());
			update.setString(2,updateVaikas.getPavarde());
			update.setString(3,updateVaikas.getAk());
			update.setString(4,updateVaikas.getUgdymas());
			update.setString(5,updateVaikas.getGrupe());
			update.setString(6,updateVaikas.getUzsiemimai());		
			update.setInt(7,updateVaikas.getId());
			update.executeUpdate();
			update.close();
		}catch(Exception exc){
			exc.printStackTrace();
		}
	}

	public ObservableList<Vaikas> searchChildrenByName(String name, User user) {
		String sql = "";		
		if (name.isEmpty() && user.getUserlevel() != 1) {
			sql = "Select * FROM vaikai";
		}				
		else if (name.isEmpty()) {
			sql = "Select * FROM vaikai WHERE user ='"+user.getUsername()+"'";
		}
		else if(!name.isEmpty() && user.getUserlevel() != 1){
			sql = "Select * FROM vaikai WHERE vardas LIKE '%" + name + "%'";	
		} 
		else { // pavadinimas ivestas, ieskoma paprasto vartotojo vaikų
			sql = "Select * FROM vaikai WHERE vardas LIKE '%" + name + "%' AND user ='"+user.getUsername()+"'";	
		}		
		ObservableList<Vaikas>data = FXCollections.observableArrayList();
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement pavad = myConn.prepareStatement(sql);
			
			ResultSet rs = pavad.executeQuery();	
				while(rs.next()){
					data.add(new Vaikas(
							rs.getInt("id"),
							rs.getString("user"),
							rs.getString("vardas"),
							rs.getString("pavarde"),
							rs.getString("ak"),
							rs.getString("ugdymas"),
							rs.getString("grupe"),
							rs.getString("uzsiemimai")				
					));	       		         		         			
				}	
		}catch(Exception exc){
			exc.printStackTrace();	
		}
		return data;
	}

	public ObservableList<Vaikas> searchChildrenById(int id, User user) {
		String sql = "";		

		if (user.getUserlevel()==1) {
			sql = "Select * FROM vaikai WHERE id = '" + id + "' AND user ='"+user.getUsername()+"'";	
		} else {
			sql = "Select * FROM vaikai WHERE id = '" + id + "'";	
		} 
		ObservableList<Vaikas>data = FXCollections.observableArrayList();
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement pavad = myConn.prepareStatement(sql);
			
			ResultSet rs = pavad.executeQuery();	
				while(rs.next()){
					data.add(new Vaikas(
							rs.getInt("id"),
							rs.getString("user"),
							rs.getString("vardas"),
							rs.getString("pavarde"),
							rs.getString("ak"),
							rs.getString("ugdymas"),
							rs.getString("grupe"),
							rs.getString("uzsiemimai")				
					));	       		         		         			
				}	
		}catch(Exception exc){
			exc.printStackTrace();	
		}
		return data;
	}

	public ObservableList<Vaikas> searchChildrenBySurname(String surname, User user) {
		String sql = "";		
		if (surname.isEmpty() && user.getUserlevel() != 1) {
			sql = "SELECT * FROM vaikai";
		} else if (surname.isEmpty()) {
			sql = "SELECT * FROM vaikai WHERE user ='"+user.getUsername()+"'";
		} else if(!surname.isEmpty() && user.getUserlevel() != 1){
			sql = "SELECT * FROM vaikai WHERE pavarde LIKE '%" + surname + "%'";	
		} else { // pavadinimas ivestas, ieskoma paprasto vartotojo vaikų
			sql = "Select * FROM vaikai WHERE pavarde LIKE '%" + surname + "%' AND user ='"+user.getUsername()+"'";	
		}		
		ObservableList<Vaikas>data = FXCollections.observableArrayList();
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement changeSurname = myConn.prepareStatement(sql);
			ResultSet rs = changeSurname.executeQuery();	
				while(rs.next()){
					data.add(new Vaikas(
							rs.getInt("id"),
							rs.getString("user"),
							rs.getString("vardas"),
							rs.getString("pavarde"),
							rs.getString("ak"),
							rs.getString("ugdymas"),
							rs.getString("grupe"),
							rs.getString("uzsiemimai")				
					));	       		         		         			
				}	
		}catch(Exception exc){
			exc.printStackTrace();	
		}
		return data;
	}

	public ObservableList<Vaikas> searchChildrenByPersonId(String personId, User user) {
		String sql = "";		
		if(user.getUserlevel() != 1){
			sql = "Select * FROM vaikai WHERE ak = '" + personId + "'";	
		} else { // pavadinimas ivestas, ieskoma paprasto vartotojo vaikų
			sql = "Select * FROM vaikai WHERE ak = '" + personId + "' AND user ='"+user.getUsername()+"'";	
		}		
		ObservableList<Vaikas>data = FXCollections.observableArrayList();
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement searchPersonId = myConn.prepareStatement(sql);
			
			ResultSet rs = searchPersonId.executeQuery();	
				while(rs.next()){
					data.add(new Vaikas(
							rs.getInt("id"),
							rs.getString("user"),
							rs.getString("vardas"),
							rs.getString("pavarde"),
							rs.getString("ak"),
							rs.getString("ugdymas"),
							rs.getString("grupe"),
							rs.getString("uzsiemimai")				
					));	       		         		         			
				}	
		}catch(Exception exc){
			exc.printStackTrace();	
		}
		return data;
	}

	public ObservableList<Vaikas> searchChildrenByEducation(String ugdymas, User user) {
		String sql = "";		
		if(user.getUserlevel() != 1){
			sql = "Select * FROM vaikai WHERE ugdymas = '" + ugdymas + "'";	
		} else { // pavadinimas ivestas, ieskoma paprasto vartotojo vaikų
			sql = "Select * FROM vaikai WHERE ugdymas = '" + ugdymas + "' AND user ='"+user.getUsername()+"'";	
		}		
		ObservableList<Vaikas>data = FXCollections.observableArrayList();
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement searchPersonId = myConn.prepareStatement(sql);
			
			ResultSet rs = searchPersonId.executeQuery();	
				while(rs.next()){
					data.add(new Vaikas(
							rs.getInt("id"),
							rs.getString("user"),
							rs.getString("vardas"),
							rs.getString("pavarde"),
							rs.getString("ak"),
							rs.getString("ugdymas"),
							rs.getString("grupe"),
							rs.getString("uzsiemimai")				
					));	       		         		         			
				}	
		}catch(Exception exc){
			exc.printStackTrace();	
		}
		return data;
	}

	public ObservableList<Vaikas> searchChildrenByGroup(String grupe, User user) {
		String sql = "";		
		if(user.getUserlevel() != 1){
			sql = "Select * FROM vaikai WHERE grupe = '" + grupe + "'";	
		} else { // pavadinimas ivestas, ieskoma paprasto vartotojo vaikų
			sql = "Select * FROM vaikai WHERE grupe = '" + grupe + "' AND user ='"+user.getUsername()+"'";	
		}		
		ObservableList<Vaikas>data = FXCollections.observableArrayList();
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement searchPersonId = myConn.prepareStatement(sql);
			
			ResultSet rs = searchPersonId.executeQuery();	
				while(rs.next()){
					data.add(new Vaikas(
							rs.getInt("id"),
							rs.getString("user"),
							rs.getString("vardas"),
							rs.getString("pavarde"),
							rs.getString("ak"),
							rs.getString("ugdymas"),
							rs.getString("grupe"),
							rs.getString("uzsiemimai")				
					));	       		         		         			
				}	
		}catch(Exception exc){
			exc.printStackTrace();	
		}
		return data;
	}

	public ObservableList<Vaikas> searchChildrenByTraining(String dance, String basketball, String football, String ceramics, String karate, User user) {
		String sql = "";		
		if (dance.isEmpty() && basketball.isEmpty() && football.isEmpty() && ceramics.isEmpty() && karate.isEmpty() && user.getUserlevel() != 1) {
			sql = "Select * FROM vaikai";
		} else if (dance.isEmpty() && basketball.isEmpty() && football.isEmpty() && ceramics.isEmpty() && karate.isEmpty()) {
			sql = "Select * FROM vaikai WHERE user ='"+user.getUsername()+"'";
		} else if(user.getUserlevel() != 1){
			sql = "Select * FROM vaikai WHERE uzsiemimai LIKE '%" + basketball + "%' OR uzsiemimai LIKE '%" + football + "%' OR uzsiemimai LIKE '%" + ceramics + "%' OR uzsiemimai LIKE '%" + karate + "%'uzsiemimai LIKE '%" + dance + "%'";	
		} else { // pavadinimas ivestas, ieskoma paprasto vartotojo vaikų
			sql = "Select * FROM vaikai WHERE (uzsiemimai LIKE '" + basketball + "%' OR uzsiemimai LIKE '" + football + "%' OR uzsiemimai LIKE '" + ceramics + "%' OR uzsiemimai LIKE '" + karate + "%' OR uzsiemimai LIKE '" + dance + "%') AND user ='"+user.getUsername()+"'";	
		}		
		ObservableList<Vaikas>data = FXCollections.observableArrayList();
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement pavad = myConn.prepareStatement(sql);
			
			ResultSet rs = pavad.executeQuery();	
				while(rs.next()){
					data.add(new Vaikas(
							rs.getInt("id"),
							rs.getString("user"),
							rs.getString("vardas"),
							rs.getString("pavarde"),
							rs.getString("ak"),
							rs.getString("ugdymas"),
							rs.getString("grupe"),
							rs.getString("uzsiemimai")				
					));	       		         		         			
				}	
		}catch(Exception exc){
			exc.printStackTrace();	
		}
		return data;
	}
}
