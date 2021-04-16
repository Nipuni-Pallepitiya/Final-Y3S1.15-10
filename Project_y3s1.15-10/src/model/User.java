package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
public Connection connect() {
		
		Connection con = null;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/userdb", "root", "");
		
		    System.out.println("Successfully connected");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public String insertUser(String fname,String lname,String uname,String email, String pwd,String nic,String level,String type) {
		
		String output = "";
		
		try {
		Connection con = connect();
		
		if(con == null) {
			
			return "Error while connecting to the database";
			
		}
		
		if(fname.equals("") || lname.equals("") || uname.equals("") || email.equals("") || pwd.equals("")) {
			
			return output+="Please enter all your details";
		}
		
		else {
			
			if(pwd.length()<8)
				return output+="Password should be at least 8 characters";
		}
		String query = "insert into user(userId,fname,lname,uname,dateActive,email,pwd,type)"+" values(?,?,?,?,?,?,?,?)";
		
		PreparedStatement preparedStmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
		
		preparedStmt.setInt(1, 0);
		preparedStmt.setString(2, fname);
		preparedStmt.setString(3, lname);
		preparedStmt.setString(4, uname);
		preparedStmt.setString(5, java.time.LocalDate.now().toString());
		preparedStmt.setString(6, email);
		preparedStmt.setString(7, pwd);
		preparedStmt.setString(8, type);
		
		preparedStmt.executeUpdate();
		ResultSet rs= preparedStmt.getGeneratedKeys();
		long key=0;
		int keynew=0;
		  if (rs.next()) 
	        {
	          key = rs.getLong(1);
	        }
		
		  keynew = (int) key;
		if(type.equals("customer")) {	            
	       
			query = "insert into customer values(?)";
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, keynew);
			preparedStmt.execute();
		}
		
		else {
			          
		     query = "insert into researcher(researcherId,nic,level) values(?,?,?)";
		     preparedStmt = con.prepareStatement(query);
		     preparedStmt.setInt(1, keynew);
		     preparedStmt.setString(2, nic);
		     preparedStmt.setString(3, level);
		     preparedStmt.execute();
		}
		
		con.close();
		
		output = "User Registered successfully";
		
		}
		
		catch(Exception e){
			output = "Error while registering";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String readUsers() {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			 
			 output = "<table border='1'><tr><th>UserId</th>"
			  +"<th>Name</th><th>Username</th>"
			  + "<th>Email</th>"
			  + "<th>Type</th>"
			  + "<th>NIC</th>"
			  + "<th>Level</th></tr>";
			
			 String query = "select * from user";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 
			 while (rs.next())
			 {
				 String userId = Integer.toString(rs.getInt("userId"));
				 String username = rs.getString("uname");
				 String fname = rs.getString("fname");
				 String lname = rs.getString("lname");
				 String userEmail = rs.getString("email");
				 String userType = rs.getString("type");
				 
				 output += "<tr><td>" + userId + "</td>";
				 output += "<td>" + fname +" "+lname + "</td>";
				 output += "<td>" + username + "</td>";
				 output += "<td>" + userEmail + "</td>"; 	
				 output += "<td>" + userType + "</td>";
				 
				if(userType.equals("researcher")) {
					 				
					 String query2 = "select * from researcher where researcherId = ?;";
					 PreparedStatement stmtr = con.prepareStatement(query2);
					 stmtr.setInt(1, Integer.parseInt(userId));	 
					 ResultSet rs2 = stmtr.executeQuery();
					 
					 while(rs2.next()) {
						 String nic = rs2.getString("nic");
						 String level = rs2.getString("level");
						 
						 output += "<td>" + nic + "</td>";
						 output += "<td>" + level + "</td></tr>";
					 }
					 
				 }
				
			 	}
			 con.close();
			 // Complete the html table
			 output += "</table>";
			}
			catch (Exception e)
			{
				 output = "Error while reading the user.";
				 System.err.println(e.getMessage());
			}
		
			return output;
	}
	
	public String userLogin(String uname,String pwd) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			
			if(uname.equals("") || pwd.equals("")) {
				return "Please enter your username and password";
			}
			 output = "<table border='1'><tr><th>UserId</th>"
					  +"<th>Name</th><th>Username</th>"
					  + "<th>Email</th>"
					  + "<th>Type</th>";
					  
					 int flag=0;
					 String query = "select * from user where uname = ? and pwd=?";
					 PreparedStatement stmt = con.prepareStatement(query);
					 stmt.setString(1, uname);	
					 stmt.setString(2, pwd);	
					 ResultSet rs = stmt.executeQuery();
					
					 while (rs.next())
					 {
						 String userId = Integer.toString(rs.getInt("userId"));
						 String username = rs.getString("uname");
						 String fname = rs.getString("fname");
						 String lname = rs.getString("lname");
						 String userEmail = rs.getString("email");
						 String userType = rs.getString("type");
						 
						 if(userType.equals("researcher")) {
						 output+= "<th>NIC</th>"
						  + "<th>Level</th></tr>";
						 }
						 
						 output += "<tr><td>" + userId + "</td>";
						 output += "<td>" + fname +" "+lname + "</td>";
						 output += "<td>" + username + "</td>";
						 output += "<td>" + userEmail + "</td>"; 	
						 output += "<td>" + userType + "</td>";
						 
						if(userType.equals("researcher")) {
							 				
							 String query2 = "select * from researcher where researcherId = ?;";
							 PreparedStatement stmtr = con.prepareStatement(query2);
							 stmtr.setInt(1, Integer.parseInt(userId));	 
							 ResultSet rs2 = stmtr.executeQuery();
							 
							 while(rs2.next()) {
								 String nic = rs2.getString("nic");
								 String level = rs2.getString("level");
								 
								 output += "<td>" + nic + "</td>";
								 output += "<td>" + level + "</td></tr>";
							 }
							 
						 }
						flag=1;
					 	}
					 
					 if(flag==0) {
						 return "Invalid User";
					 }
			 con.close();
			 // Complete the html table
			 output += "</table>";
			}
			catch (Exception e)
			{
				 output = "Error while reading the user.";
				 System.err.println(e.getMessage());
			}
		
			return output;
	}
	
	public String deleteUser(String uid) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database for deleting.";
			}
			
			String query = "delete from user where userId = ?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			preparedStmt.setInt(1, Integer.parseInt(uid));
			
			preparedStmt.execute();
			con.close();
			
			output = "Deleted successfully";
		}
		catch(Exception e) {
			output = "Error while deleting the user";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
 public String updateUser(String id,String fname,String lname,String email) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database for update.";
			}
			
			String query = "update user set fname = ?,lname = ?,email = ? where userId = ?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);			
			
			preparedStmt.setString(1, fname);
			preparedStmt.setString(2, lname);
			preparedStmt.setString(3, email);
			preparedStmt.setInt(4, Integer.parseInt(id));
			
			preparedStmt.execute();
			con.close();
			
			output = "Updated successfully";
		}
		catch(Exception e) {
			output = "Error while updating the user details";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
