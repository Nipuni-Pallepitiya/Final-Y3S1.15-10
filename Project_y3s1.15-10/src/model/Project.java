package model;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
public class Project 
{ //A common method to connect to the DB
	private Connection connect()
	{
		Connection con = null;
		try
		{
		Class.forName("com.mysql.jdbc.Driver");
		//Provide the correct details: DBServer/DBName, username, password
		con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/projectDB", "root", "");
		}
		catch (Exception e)
		{e.printStackTrace();}
		return con;
	}
	public String insertProject(String ProjectName,String ProjectType,String UserID, String sdate, String edate,String status,String investStatus,String InvestmentAmount,String ProposedEquity,String ProjectTimeline) 
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for inserting."; 
			}
			
			else
			{	
				
				if(ProjectName.equals("")||UserID.equals("")||sdate.equals("")||edate.equals("")||status.equals("")||investStatus.equals("")||ProjectTimeline.equals("")||ProposedEquity.equals("")||InvestmentAmount.equals("")||ProjectType.equals(""))
				{
					return "please enter all fields.";
				}
				
				
				{
					
				Boolean ch,ch2;
				
				ch=status.equalsIgnoreCase("Yes") || status.equalsIgnoreCase("No");
				ch2=investStatus.equalsIgnoreCase("Yes")||investStatus.equalsIgnoreCase("No");
				
				
				
				if(ch == false)
				{
					return "invalid input for status ";
				}
				
				if(ch2 == false)
				{
					return "invalid input for invest status";
				}
				
				Date sDate=Date.valueOf(sdate);
				Date eDate=Date.valueOf(edate);
				
				if(eDate.compareTo(sDate)<0)
					return "Edate should be greater than SDate";
				
				
				
				
				
				
			// create a prepared statement
				String query = " insert into projects(`ProjectID`,`ProjectName`,`ProjectType`,`UserID`,`sdate`,`edate`,`status`,`investStatus`,`InvestmentAmount`,`ProposedEquity`,`ProjectTimeline`)"
				+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, ProjectName);
				preparedStmt.setString(3, ProjectType);
				preparedStmt.setString(4, UserID);
				preparedStmt.setDate(5, sDate);
				preparedStmt.setDate(6, eDate);
				preparedStmt.setString(7, status);
				preparedStmt.setString(8, investStatus);
				preparedStmt.setDouble(9, Double.parseDouble(InvestmentAmount));
				preparedStmt.setDouble(10, Double.parseDouble(ProposedEquity));
				preparedStmt.setString(11, ProjectTimeline);
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Inserted successfully";
				
				}
				
				
				
			}
				
			
		}
		catch (Exception e)
		{
			output = "Error while inserting the project details.";
			System.err.println(e.getMessage());
		}
		return output;
			
	
		
		
	}
	public String readProjects()
	{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for reading."; }
		// Prepare the html table to be displayed
		output = "<table border='1'><tr><th>ProjectID</th><th>ProjectName</th><th>ProjectType</th><th>UserID</th><th>sdate</th>" +
		"<th>eDate</th>" +
		"<th>status</th>" +
		"<th>investStatus</th>" +
		"<th>InvestmentAmount</th>" +
		"<th>ProposedEquity</th>" +
		"<th>ProjectTimeline</th>" +
		"<th>Update</th><th>Remove</th></tr>";
		String query = "select * from projects";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		// iterate through the rows in the result set
		while (rs.next())
		{
		String ProjectID = Integer.toString(rs.getInt("ProjectID"));
		String ProjectName = rs.getString("ProjectName");
		String ProjectType=rs.getString("ProjectType");
		String UserID=Integer.toString(rs.getInt("UserID"));
		String sdate = rs.getString("sdate");
		String edate = rs.getString("edate");
		String status = rs.getString("status");
		String investStatus = rs.getString("investStatus");
		String InvestmentAmount = Double.toString(rs.getDouble("InvestmentAmount"));
		String ProposedEquity = Double.toString(rs.getDouble("ProposedEquity"));
		String ProjectTimeline = rs.getString("ProjectTimeline");
		// Add into the html table
		output += "<tr><td>" + ProjectID + "</td>";
		output += "<td>" + ProjectName + "</td>";
		output += "<td>" + ProjectType + "</td>";
		output += "<td>" + UserID + "</td>";
		output += "<td>" + sdate + "</td>";
		output += "<td>" + edate + "</td>";
		output += "<td>" + status + "</td>";
		output += "<td>" + investStatus + "</td>";
		output += "<td>" + InvestmentAmount + "</td>";
		output += "<td>" + ProposedEquity + "</td>";
		output += "<td>" + ProjectTimeline + "</td>";
		// buttons
		output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
		+ "<td><form method='post' action='items.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
		+ "<input name='ProjectID' type='hidden' value='" + ProjectID
		+ "'>" + "</form></td></tr>";
		}
		con.close();
		// Complete the html table
		output += "</table>";
		}
		catch (Exception e)
		{
		output = "Error while reading the projects.";
		System.err.println(e.getMessage());
		}
	return output;
	}
	public String updateProject(String ProjectID,String ProjectName,String ProjectType,String UserID, String sdate, String edate,String status,String investStatus,String InvestmentAmount,String ProposedEquity,String ProjectTimeline)
	{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{
			return "Error while connecting to the database for updating."; 
		}
		else
		{
			
			if(ProjectName.equals("")||UserID.equals("")||sdate.equals("")||edate.equals("")||status.equals("")||investStatus.equals("")||ProjectTimeline.equals("")||ProposedEquity.equals("")||InvestmentAmount.equals("")||ProjectType.equals(""))
			{
				return "please enter all fields.";
			}
			
			//if(!status.equals("yes")||investStatus.equals("yes")||investStatus.equals("Yes")||status.equals("Yes")||status.equals("no")||investStatus.equals("no")||investStatus.equals("No")||status.equals("No"))
			{
			Boolean ch,ch2;
			ch=status.equalsIgnoreCase("Yes") || status.equalsIgnoreCase("No");
			ch2=investStatus.equalsIgnoreCase("Yes")||investStatus.equalsIgnoreCase("No");
			if(ch == false)
			{
				return "invalid input for status";
			}
			
			if(ch2 == false)
			{
				return "invalid input for invest status";
			}
			
			Date s1Date=Date.valueOf(sdate);
			Date e1Date=Date.valueOf(edate);
			
			if(e1Date.compareTo(s1Date)<0)
				return "Edate should be greater than SDate";
			
			// create a prepared statement
			String query = "UPDATE projects SET ProjectName=?,ProjectType=?,UserID=?,sdate=?,edate=?,status=?,investStatus=?,InvestmentAmount=?,ProposedEquity=?,ProjectTimeline=? WHERE ProjectID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, ProjectName);
			preparedStmt.setString(2, ProjectType);
			preparedStmt.setInt(3,  Integer.parseInt(UserID));
			preparedStmt.setDate(4, s1Date);
			preparedStmt.setDate(5, e1Date);
			preparedStmt.setString(6, status);
			preparedStmt.setString(7, investStatus);
			preparedStmt.setDouble(8, Double.parseDouble(InvestmentAmount));
			preparedStmt.setDouble(9, Double.parseDouble(ProposedEquity));
			preparedStmt.setString(10, ProjectTimeline);
			preparedStmt.setInt(11, Integer.parseInt(ProjectID));
		
		
		/*
		 {
    "ProjectID":"5",
    "ProjectName":"xyz",
    "ProjectType":"typ",
    "UserID":"1",
    "sdate":"2020-09-09",
    "edate":"2020-09-09",
    "status":"yes",
    "investStatus":"no",
    "InvestmentAmount":"30",
    "ProposedEquity":"30",
    "ProjectTimeline":"10years"

		}
		 */

		
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Updated successfully";
		}
		}
		}
		catch (Exception e)
		{
		output = "Error while updating the project.";
		System.err.println(e.getMessage());
		}
		
		return output;
	}
	public String deleteProject(String ProjectID)
	{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for deleting."; }
		// create a prepared statement
		String query = "delete from projects where ProjectID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(ProjectID));
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Deleted successfully";
		}
		catch (Exception e)
		{
		output = "Error while deleting the project.";
		System.err.println(e.getMessage());
		}
		return output;
	}
}
