package com;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.TimeZone;

public class Payments {
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gadgetbadge", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String ReadPayments() {
		
		String output = "";
		
		try{
			Connection con  = connect();
				if (con == null) {

					return "Error while connecting to the database for reading.";
				}
			
				  output = "<table border='1'><tr><th>Payment ID</th>"+
					 		"<th>Card Number</th> "
                             +"<th>Name on Card</th>"
                             +"<th>cvc</th>"
                             +"<th>amount</th>"
                             +"<th>"
                             + "Update</th>"
					 		+"<th>Order ID</th>"+
                             "<th>Update</th>"
					 		+ "<th>Remove</th></tr>";
				
			LocalDate prvPaymentDate = null;
			String readQuery = "select * from payment";
			
			 PreparedStatement pstmt = con.prepareStatement(readQuery);
			 		 		 
				
			 ResultSet rs = pstmt.executeQuery(readQuery); 
			
			 
			while(rs.next()) {
				int PayID = rs.getInt("payment_id");
				int cno = rs.getInt("card_number");
                String name = rs.getString("name");
				int cvc = rs.getInt("cvc");
                int amount = rs.getInt("amount");
				Date pday = rs.getDate("pdate");
                int oid = rs.getInt("order_id");
			
				
				// Add into the html table
				output += "<tr>"
				+ "<td><input id='hidPayIDUpdate'" + "name='hidPayIDUpdate'" + "type='hidden' value='"
				+ PayID + "'>" + PayID + "</td>";
				output += "<td>" + cno + "</td>";
				output += "<td>" + name + "</td>";
                output += "<td>" + cvc + "</td>";
                output += "<td>" + amount + "</td>";
                output += "<td>" + pday + "</td>";
				output += "<td>" + oid + "</td>";
				
				
				// buttons
				output += "<td><input name='btnUpdate'" + "type='button' value='Update'"
						+ "class='btnUpdate btn btn-secondary'></td>" + "<td><input name='btnRemove'"
						+ "type='button' value='Remove'" + "class='btnRemove btn btn-danger'" + "data-payid='" + PayID
						+ "'>" + "</td></tr>";
			}
		
		   output += "</table>";
			  
		}
		catch(SQLException e){
			
			output = "Error while reading the payments.";
			System.err.println(e.getMessage());
		
		}
		return output;
	}
	
public String addPayment(int cno,String name,int cvc,int amount,String pday,int oid) {
	
	String output = "";
		
		try{
			  
			Connection con  = connect();
			if (con == null) {
				
				return "Error while connecting to the database for inserting.";
			}
			
			
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			java.util.Date startDate=null;
			
			try {
				startDate = formatter.parse(pday);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			


			
			java.sql.Date sDate = new java.sql.Date(startDate.getTime());
			

					
			String insertPayQuery = " insert into payment (card_number,name,cvc,amount,pdate,order_id) values (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmnt = con.prepareStatement(insertPayQuery);
			
			java.sql.Date sDate2 = new java.sql.Date(startDate.getTime());
			        pstmnt.setString(5, "2021-05-12");
			        pstmnt.setInt(1, cno);
					pstmnt.setString(2, name);
					pstmnt.setInt(3, cvc);
					pstmnt.setInt(4, amount);
					pstmnt.setInt(6, oid);
					pstmnt.execute();
			

			
			con.close();
		
			String newPayments = ReadPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
			
//				}
			
		}
		catch(SQLException e){
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the Payments.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
		
	}

public String UpdatePayments( int PayID,int cno,String name,int cvc,int amount,String pday,int oid)  {
	String output = "";
	
	try{
		
		Connection con  = connect();
		if (con == null) {
			
			return "Error while connecting to the database for updating.";
		}
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date startDate2=null;
		String ndate=pday.replaceAll("-", "/");
		try {
			
			startDate2 = formatter1.parse(ndate);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		  
		

		
			String updatePayQuery = "UPDATE payment SET card_number=?,name=?,cvc=?,amount=?,pdate=?,order_id=? WHERE payment_id=?";

		PreparedStatement pstmnt = con.prepareStatement(updatePayQuery);
		
		

		String[] arrOfStr = pday.split("-", 5); 
		String DateArray= arrOfStr[2]+"-"+arrOfStr[0]+"-"+arrOfStr[1];
		
		
       System.out.println("Reconstructed Date "+DateArray);
		System.out.println("Parameter Date "+pday);
		pstmnt.setString(5, pday);
		pstmnt.setInt(1, cno);
		pstmnt.setString(2, name);
		pstmnt.setInt(3, cvc);
		pstmnt.setInt(4, amount);
		pstmnt.setInt(6, oid);
		
         pstmnt.execute();
         con.close();
			String newPayments = ReadPayments();
			
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
		
//		}
			
			
	}
	catch(SQLException e){
		output = "{\"status\":\"error\", \"data\":" + "\"Error while updating the Payment.\"}";
		
	}
	return output;
	
}
//public String DeletePayment(String PayID) {
//	
//	String output = "";
//	
//	try{
//		
//		Connection con  = connect();
//		if (con == null) {
//			
//			return "Error while connecting to the database for inserting.";
//		}
//		
//		//query for get date 
//		
//		String getdateQuery="select pdate  from payment where payment_id = ?";
//		PreparedStatement preparedstatement2 = con.prepareStatement(getdateQuery);
//			
//		preparedstatement2.setInt(1,Integer.parseInt(PayID));
//		ResultSet newresultset = preparedstatement2.executeQuery();
//		
//		newresultset.next();
//		
//		 //assign to variable
//		  Date pday = newresultset.getDate("pdate");
//		
//		  System.out.println(pday);
//		SimpleDateFormat  simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
//		Date pdate = new Date(System.currentTimeMillis());
//	
//		//check past dates
//		
//		if(pday.compareTo(pdate)<0) {
//			
//			output = "{\"status\":\"error\", \"data\":" + "\"You cannot delete past dates as Payment dates only future dates.\"}";
//			
//		
//	}
//		
//		else {
//			
//			 String Deletequery = "delete from payment where payment_id=?";
//				
//			 PreparedStatement pstmnt = con.prepareStatement(Deletequery);
//			 
//				pstmnt.setInt(1, Integer.parseInt(PayID));
//				pstmnt.execute();
//				
//				con.close();
//				String newPayments = ReadPayments();
//				output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
//				
//			
//	}
//	}catch(SQLException e){
//		
//		output = "{\"status\":\"error\", \"data\":" + "\"Error while deleting the Payments.\"}";
//		System.err.println(e.getMessage());
//	}
//	return output;
//} 
public String deletePayment(String PayID) 
{ 
String output = ""; 
try
{ 
Connection con = connect(); 
if (con == null) 
{ 
return "Error while connecting to the database for deleting."; 
} 
// create a prepared statement
String query = "delete from payment where payment_id=?"; 
PreparedStatement preparedStmt = con.prepareStatement(query); 
// binding values
preparedStmt.setInt(1, Integer.parseInt(PayID)); 
// execute the statement
preparedStmt.execute(); 
con.close(); 
String newPayments = ReadPayments(); 
output = "{\"status\":\"success\", \"data\": \"" + 
newPayments + "\"}"; 
} 
catch (Exception e) 
{ 
output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment.\"}"; 
System.err.println(e.getMessage()); 
} 
return output; 
}
}

