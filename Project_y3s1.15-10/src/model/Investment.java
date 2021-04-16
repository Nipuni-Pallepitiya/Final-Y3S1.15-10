package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Investment {
public Connection connect() {
		
		Connection con = null;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/investmentdb", "root", "");
		
		    System.out.println("Successfully connected");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public String insertInvestment(String amountFunded,String equityAssigned, String confirmedDate,String investDate,String projId) {
		
		String output = "";
		
		try {
		Connection con = connect();
		
		if(con == null) {
			
			return "Error while connecting to the database";
			
		}
		
		Date conDate=Date.valueOf(confirmedDate);
		Date invDate=Date.valueOf(investDate);
		
		String query = "insert into investment(investId,amountFunded,equityAssigned,confirmedDate,investmentDate,projId)"+" values(?,?,?,?,?,?)";
		
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		preparedStmt.setInt(1, 0);
		preparedStmt.setDouble(2, Double.parseDouble(amountFunded));
		preparedStmt.setDouble(3, Double.parseDouble(equityAssigned));
		preparedStmt.setDate(4, conDate);
		preparedStmt.setDate(5, invDate);
		preparedStmt.setInt(6, Integer.parseInt(projId));
		
		preparedStmt.execute();
		con.close();
		
		output = "Project Investment added successfully";
		
		}
		
		catch(Exception e){
			output = "Error while adding";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String readInvestment() {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			 
			 output = "<table border='1'><tr><th>Investment Id</th>"
			  +"<th>Amount Funded</th><th>Equity Assigned</th>"
			  + "<th>Confirmed Date</th>"
			  + "<th>Investment Date</th>"
			  + "<th>Project Id</th></tr>";
			
			 String query = "select * from investment";
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 
			 while (rs.next())
			 {
				 String investId = Integer.toString(rs.getInt("investId"));
				 String amount = Double.toString(rs.getDouble("amountFunded"));
				 String equity = Double.toString(rs.getDouble("equityAssigned"));
				 String conDate = rs.getDate("confirmedDate").toString();
				 String invDate = rs.getDate("investmentDate").toString();
				 String projId = Integer.toString(rs.getInt("projId"));
				 
				 output += "<tr><td>" + investId + "</td>";
				 output += "<td>" + amount + "</td>";
				 output += "<td>" + equity + "</td>"; 	
				 output += "<td>" + conDate + "</td>";
				 output += "<td>" + invDate + "</td>";
				 output += "<td>" + projId + "</td>";
				
			 	}
			 con.close();
			 // Complete the html table
			 output += "</table>";
			}
			catch (Exception e)
			{
				 output = "Error while reading the investment details.";
				 System.err.println(e.getMessage());
			}
		
			return output;
	}
	
	public String deleteInvestment(String invId) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database for deleting.";
			}
			
			String query = "delete from investment where investId= ?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			preparedStmt.setInt(1, Integer.parseInt(invId));
			
			preparedStmt.execute();
			con.close();
			
			output = "Deleted successfully";
		}
		catch(Exception e) {
			output = "Error while deleting the project investment";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
public String updateInvestment(String invid,String amountFunded,String equityAssigned, String confirmDate,String investDate) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			if(con == null) {
				return "Error while connecting to the database for update.";
			}
			
			Date conDate=Date.valueOf(confirmDate);
			Date invDate=Date.valueOf(investDate);
			
			String query = "update investment set amountFunded = ?,equityAssigned = ?,confirmedDate = ?,investmentDate = ? where investId = ?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);			
			
			preparedStmt.setDouble(1, Double.parseDouble(amountFunded));
			preparedStmt.setDouble(2, Double.parseDouble(equityAssigned));
			preparedStmt.setDate(3, conDate);
			preparedStmt.setDate(4, invDate);
			preparedStmt.setInt(5, Integer.parseInt(invid));
			
			preparedStmt.execute();
			con.close();
			
			output = "Updated successfully";
		}
		catch(Exception e) {
			output = "Error while updating the investment details";
			System.err.println(e.getMessage());
		}
		return output;
	}
}
