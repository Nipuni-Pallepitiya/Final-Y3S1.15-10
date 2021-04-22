package model;

import java.sql.*;

public class Sales {

	//DB Connection
	private Connection connect() {
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			//DB Connection Details
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/salesdb", "root", "root");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}

	//Database CRUD Operations
	public String insertSales(String invoiceId, String purchaseDate, String totalUnits, String netAmount, String discountTax, String totalAmount, String paymentType, String orderStatus ) 
	{
	
		String output = "";
		
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connection to the database for inserting";
			}
			//prepared statement
			String query = " insert into sales"
					+ "(invoiceId, purchaseDate,totalUnits,netAmount,discountTax,totalAmount,paymentType,orderStatus) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values
			preparedStmt.setString(1, invoiceId);
			preparedStmt.setString(2,  purchaseDate);
			preparedStmt.setString(3,  totalUnits);
			preparedStmt.setString(4,  netAmount);
			preparedStmt.setString(5,  discountTax);
			preparedStmt.setString(6,  totalAmount);
			preparedStmt.setString(7,  paymentType);
			preparedStmt.setString(8,  orderStatus);
			
			//statement execution
			preparedStmt.execute();
			con.close();
			
			output = "Inserted Successfully";
		}
		catch(Exception e) {
			output = "Error while instering the record";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String allSales() {
		String output = "";
		try
		{
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for reading";
			}
			
			//prepare the html table to be displayed
			output = "<table border='1'><tr><th>Invoice ID</th>"
					+ "<th>Purchase Date</th>"
					+ "<th>Total Units </th>"
					+ "<th>Net Amount</th>"
					+ "<th>Discount Tax</th>"
					+ "<th>Total Amount</th>"
					+ "<th>Payment Type</th>"
					+ "<th>Order Status</th></tr>";
			
			String query = "select * from sales";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			//iterate though the rows in the result set
			while(rs.next()) {
				String invoiceId = rs.getString("invoiceId");
				String purchaseDate = rs.getString("purchaseDate");
				String totalUnits = rs.getString("totalUnits");
				String netAmount = rs.getString("netAmount");
				String discountTax = rs.getString("discountTax");
				String totalAmount = rs.getString("totalAmount");
				String paymentType = rs.getString("paymentType");
				String orderStatus = rs.getString("orderStatus");
				
				//Add into the html table
				output += "<tr><td>" + invoiceId + "</td>";
				output += "<td>" + purchaseDate + "</td>";
				output += "<td>" + totalUnits + "</td>";
				output += "<td>" + netAmount + "</td>";
				output += "<td>" + discountTax + "</td>";
				output += "<td>" + totalAmount + "</td>";
				output += "<td>" + paymentType + "</td>";
				output += "<td>" + orderStatus + "</td>";
				
			}
			con.close();
			
			//complete html table
			output += "</table>";
			
		} catch(Exception e) {
			output = "Error while loading Sales";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updateSales(String invoiceId, String purchaseDate, String totalUnits, String netAmount, String discountTax, String totalAmount, String paymentType, String orderStatus )
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for updating";
			}
			
			//create prepared statement
			String query = "Update sales SET invoiceId=?, purchaseDate=?,totalUnits=?,"
					+ "netAmount=?,discountTax=?,totalAmount=?,paymentType=?,orderStatus=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values
			preparedStmt.setString(1, invoiceId);
			preparedStmt.setString(2,  purchaseDate);
			preparedStmt.setString(3,  totalUnits);
			preparedStmt.setString(4,  netAmount);
			preparedStmt.setString(5,  discountTax);
			preparedStmt.setString(6,  totalAmount);
			preparedStmt.setString(7,  paymentType);
			preparedStmt.setString(8,  orderStatus);
			
			//statement execution
			preparedStmt.execute();
			con.close();
			
			output = "Updated Successfully";
			
		}catch(Exception e) {
			output = "Error while updating sales";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deleteSales(String invoiceId) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if(con == null) {
				return "Error while connection to the database for deleting";
			}
			
			//create prepared statement
			String query = "delete from sales where invoiceId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding value
			preparedStmt.setString(1,  invoiceId);
			
			//execution
			preparedStmt.execute();
			con.close();
			
			output = "Deleted Successfully";
		} catch(Exception e) {
			output = "Errorn while deleting sale";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
}


