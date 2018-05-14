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
import javafx.scene.control.TextField;

public class UserDao {
	User user = new User();
	public void addUser(User user) throws MySQLIntegrityConstraintViolationException
	{
		String sql = "INSERT INTO `users`"
				+ "(`username`, `name`, `surname`,`password`, `userlevel`, `email`)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?useUnicode=true&characterEncoding=utf-8", "root", "");
			PreparedStatement add = myConn.prepareStatement(sql);
			add.setString(1,user.getUsername());
			add.setString(2,user.getName());
			add.setString(3,user.getSurname());
			add.setString(4,user.getPassword());
			add.setInt(5,user.getUserlevel());
			add.setString(6,user.getEmail());
			
			add.execute();
			add.close();
		}catch(MySQLIntegrityConstraintViolationException e){
			throw new MySQLIntegrityConstraintViolationException();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User getUser(String username,String password){
		String sql = "SELECT * FROM users WHERE (username = ? AND password = ?)";
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement pavad = myConn.prepareStatement(sql);
			pavad.setString(1,username);
			pavad.setString(2,password);
			ResultSet rs = pavad.executeQuery(); 
				if(rs.next()){
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setUserlevel(rs.getInt("userlevel"));
					user.setEmail(rs.getString("email"));					
				}
		}catch(Exception exc){
			exc.printStackTrace();	
		}
		return user;	
	}

	public void changeEmail(String newEmail, User user) throws MySQLIntegrityConstraintViolationException {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement changeEmail = myConn.prepareStatement("UPDATE users SET email=? WHERE username=?");
			changeEmail.setString(1,newEmail);
			changeEmail.setString(2,user.getUsername());
			changeEmail.executeUpdate();
			changeEmail.close();
		}catch(MySQLIntegrityConstraintViolationException exc){
			throw new MySQLIntegrityConstraintViolationException();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void changePassword(String newPassword, User user) {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement changePassword = myConn.prepareStatement("UPDATE users SET password=? WHERE username=?");
			changePassword.setString(1,newPassword);
			changePassword.setString(2,user.getUsername());
			changePassword.executeUpdate();
			changePassword.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showUsers(ObservableList<User> data) {
		String query = "";
		query = "SELECT * FROM users";	
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement add = myConn.prepareStatement(query);
			ResultSet rs = add.executeQuery();
			while(rs.next()) {
				data.add(new User(
						rs.getString("username"),							
						rs.getString("name"),
						rs.getString("surname"),
						rs.getString("password"),
						rs.getInt("userLevel"),
						rs.getString("email")						
						));
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}
	}

	public ObservableList<User> searchUserByName(String name) {
		String sql = "";		
		sql = "Select * FROM users WHERE name LIKE '%" + name + "%'";	
		 
		ObservableList<User>data = FXCollections.observableArrayList();
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement pavad = myConn.prepareStatement(sql);
			
			ResultSet rs = pavad.executeQuery();
			//String username, String name, String surname,  String password, int userLevel, String email
				while(rs.next()){
					data.add(new User(
							rs.getString("username"),							
							rs.getString("name"),
							rs.getString("surname"),
							//rs.getString("password"),
							rs.getInt("userLevel"),
							rs.getString("email")																		
					));	       		         		         			
				}	
		}catch(Exception exc){
			exc.printStackTrace();	
		}
		return data;
	}

	public ObservableList<User> searchUserBySurName(String surname) {
		String sql = "";		
		sql = "Select * FROM users WHERE surname LIKE '%" + surname + "%'";	
		 
		ObservableList<User>data = FXCollections.observableArrayList();
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement pavad = myConn.prepareStatement(sql);
			
			ResultSet rs = pavad.executeQuery();
			//String username, String name, String surname,  String password, int userLevel, String email
				while(rs.next()){
					data.add(new User(
							rs.getString("username"),							
							rs.getString("name"),
							rs.getString("surname"),
							//rs.getString("password"),
							rs.getInt("userLevel"),
							rs.getString("email")																		
					));	       		         		         			
				}	
		}catch(Exception exc){
			exc.printStackTrace();	
		}
		return data;
	}

	public void deleteUser(String username) {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "");
			PreparedStatement delete = myConn.prepareStatement("DELETE FROM users WHERE username = ?");
			delete.setString(1, username);
			delete.executeUpdate();
		} catch(Exception exc){
			exc.printStackTrace();
		}
	}

	public void changeUserlevel(String username, int changeUserlevel) {
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?useUnicode=true&characterEncoding=utf-8", "root", "");
			PreparedStatement updateUserlevel = myConn.prepareStatement("UPDATE users SET userlevel=? WHERE username=?");
			updateUserlevel.setInt(1,changeUserlevel);
			updateUserlevel.setString(2,username);
			updateUserlevel.executeUpdate();
			updateUserlevel.close();
		}catch(Exception exc){
			exc.printStackTrace();
		}
	}

}
