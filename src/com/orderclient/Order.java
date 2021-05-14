package com.orderclient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Order extends DBConnector{
	//Read Order
	public String getOrder() {
		
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			// Prepare the HTML table to be displayed
			output = "<table  class='table table-dark table-striped'><tr>"
					+ "<th>Order ID</th>"
					+ "<th>Customer ID</th>"
					
					+ "<th> Product ID</th>"
					+ "<th> Items</th>"
					+ "<th> Items Price</th>"
					+ "<th>  Discount</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th></tr>";
			
			
			// Create a prepared statement
			String query = " select * from order_table";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			JsonArray resultArray = new JsonArray();
			while(rs.next()) {
			

				int order_id = rs.getInt("order_id") ;
				int product_id = rs.getInt("product_id") ;
				int items = rs.getInt("items") ;
				double item_price = rs.getDouble("item_price") ;
				double discount = rs.getDouble("discount") ;
				int customerid = rs.getInt("customerid") ;
				
			
				
				// Add a row into the HTML table
				
				output += "<td>" + order_id + "</td>";
				output += "<td>" + customerid + "</td>";
				output += "<td>" + product_id + "</td>";
				output += "<td>" + items + "</td>"; 
				output += "<td>" + item_price + "</td>"; 
				output += "<td>" + discount + "</td>"; 
				
				

				// button Set
				output += "<td><input name='btnUpdate' id = 'btnUpdate'  type='button' value='Update' class='btnUpdate btn btn-secondary' data-orderid='" + order_id + "'></td>"
						+"<td><input name='btnRemove' id = 'btnRemove'   type='button' value='Remove' class='btnRemove btn btn-danger' data-orderid='" + order_id + "'></td></tr>";
			}

			con.close();

			// Complete the HTML table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the Order.";
			System.err.println(e.getMessage());
		}
		return output;
	}


////insert
	public JsonObject insertOrder( int product_id , int items, double item_price,double discount,int customerid )
	{
		JsonObject result = null;
		try
		{
			Connection con = connect();
			if (con == null)
			{
				result = new JsonObject();
				result.addProperty("status", "error");
				result.addProperty("data", "Error while connecting to the database for inserting.");
				return result;
			}
			// create a prepared statement
			String query = " insert into order_table"+
					"(`product_id`,`items`,`item_price`,`discount`,`customerid`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, product_id);
			preparedStmt.setInt(2, items);
			preparedStmt.setDouble(3, item_price);
			preparedStmt.setDouble(4, discount);
			preparedStmt.setInt(5,customerid);
			
			// execute the statement
			preparedStmt.execute();
			con.close();

			result = new JsonObject();
			result.addProperty("status", "successfull");
			result.addProperty("data", getOrder());
		}
		catch (Exception e)
		{
			result = new JsonObject();
			result.addProperty("status", "exception");
			result.addProperty("data", "exception occured while inserting data.");
			System.err.println(e.getMessage());
		}
		return result;
	} 

	//update order
	public JsonObject updateOrder( int order_id, int product_id , int items, double item_price,double discount,int customerid )
	{
		JsonObject result = null;
		try
		{
			Connection con = connect();
			if (con == null)
			{
				result = new JsonObject();
				result.addProperty("status", "error");
				result.addProperty("data", "Error while connecting to the database for updating.");
				return result;
			}
			// create a prepared statement
			String query = " update order_table set `product_id`= ? ,`items` = ?,`item_price` = ?,`discount` = ?,`customerid` = ? where `order_id` = ?";
PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, product_id);
			preparedStmt.setInt(2, items);
			preparedStmt.setDouble(3, item_price);
			preparedStmt.setDouble(4, discount);
			preparedStmt.setInt(5,customerid);
			preparedStmt.setInt(6,order_id);
			
			// execute the statement
			preparedStmt.execute();
			

			int status =  preparedStmt.executeUpdate();

			con.close();

			result = new JsonObject();

			if(status > 0 ) {
				
				
				result.addProperty("status", "successfull");
				result.addProperty("data" , getOrder());

			}
			else {
				
				result.addProperty("status", "failed");
				result.addProperty("data", getOrder());
			}

		}
		catch (Exception e)
		{
			result = new JsonObject();
			result.addProperty("status", "exception");
			result.addProperty("data", "exception occured while updating Order.");
			System.err.println(e.getMessage());
		}
		return result;
	} 

	//delete order
	public JsonObject deleteOrder( int order_id )
	{
		JsonObject result = null;
		try
		{
			Connection con = connect();

			if (con == null)
			{
				result = new JsonObject();
				result.addProperty("status", "error");
				result.addProperty("data", "Error while connecting to the database for deleting.");
				return result;
				
			}


			// create a prepared statement
			String query = "delete from order_table where order_id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);


			// binding values
			preparedStmt.setInt(1, order_id);

			// execute the statement
			int status =  preparedStmt.executeUpdate();

			con.close();

			result = new JsonObject();

			if(status > 0 ) {


				result.addProperty("status", "successfull");
				result.addProperty("data", getOrder());

			}
			else {
				result.addProperty("status", "failed");
				result.addProperty("data", getOrder());	
			}


		}
		catch (Exception e)
		{
			result = new JsonObject();
			result.addProperty("status", "exception");
			result.addProperty("data", "exception occured while deleting Order.");
			System.err.println(e.getMessage());
		}
		return result;
	} 


	//get single record
	public JsonObject getSingleOrder(int order_id) {
		JsonObject result = null;
		try
		{
			Connection con = connect();

			if (con == null)
			{
				result = new JsonObject();
				result.addProperty("ERROR", "Error while connecting to the database for reading single item."); 
			}


			// create a prepared statement
			String query = " select * from order_table where order_id=?";


			PreparedStatement preparedStmt = con.prepareStatement(query);

			preparedStmt.setInt(1, order_id);
			ResultSet rs = preparedStmt.executeQuery();

			JsonArray resultArray = new JsonArray();
			while(rs.next()) {
				JsonObject order_table_row = new JsonObject();
				order_table_row.addProperty("order_id", rs.getInt("order_id") );
				order_table_row.addProperty("product_id", rs.getInt("product_id") );
				order_table_row.addProperty("items", rs.getString("items") );
				order_table_row.addProperty("item_price", rs.getDouble("item_price") );
				order_table_row.addProperty("discount", rs.getDouble("discount") );
				order_table_row.addProperty("customerid", rs.getString("customerid") );
				resultArray.add(order_table_row);
				
			}
			result = new JsonObject();
			result.add("order", resultArray);

			con.close();
		}
		catch (Exception e)
		{
			result = new JsonObject();
			result.addProperty("EXCEPTION", "Error occured while reading Order");
			System.err.println(e.getMessage());
		}
		return result;
	}
}
