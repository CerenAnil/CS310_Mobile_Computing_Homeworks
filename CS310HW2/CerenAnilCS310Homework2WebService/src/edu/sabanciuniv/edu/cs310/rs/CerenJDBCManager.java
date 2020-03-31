package edu.sabanciuniv.edu.cs310.rs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

public class CerenJDBCManager {

	public static void createDB()	//creates the database
	{
		
		String sql = "CREATE TABLE Product (" +
				"productID INT NOT NULL AUTO_INCREMENT, " +
                " productName VARCHAR(255), " + 
                " productPrice DOUBLE, " +
                " productStock INT, " +
                " PRIMARY KEY (productID))";
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/cs310", "root", "1q2w3e4r");
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String save(Product p)	//adds product to the database
	{
		
		try
		{
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/cs310", "root", "1q2w3e4r");		
			
				PreparedStatement ps =  connection.prepareStatement("insert into Product (productName, productPrice, productStock) values (?,?,?) ");
				ps.setString(1, p.getProductName());
				ps.setDouble(2, p.getProductPrice());
				ps.setInt(3, p.getProductStock());
				
				ps.execute();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return p.getProductName() + " is the product name. " + p.getProductPrice() + " is the price. " + p.getProductStock() + " is the stock count.\n";

	}
	
	public static String deleteProductByID (int productID)	//delete product from database
	{
		
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/cs310", "root", "1q2w3e4r");
			PreparedStatement ps =  connection.prepareStatement("DELETE FROM Product WHERE productID = ?");
			ps.setInt(1,productID);
			ps.execute();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return productID + " is the ID of the product we want to delete.\n";
	}
	
	public static String updateProductStock (int productID, Double productPrice, int productStock)	//updates product stock and price
	{

		Connection connection;
		try {
			
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/cs310", "root", "1q2w3e4r");
			PreparedStatement ps =  connection.prepareStatement("UPDATE Product SET productPrice = ? , productStock = ? WHERE productID = ?");
			
			ps.setDouble(1,productPrice);
			ps.setInt(2,productStock);
			ps.setInt(3,productID);
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productID + " is the ID of the product we want to make a stock and price update.\n" + productPrice + " is the new price.\n" + productStock + " is the new stock count.\n";
		
	}
	
	public static void main(String[] args) 
	{		 
				
		CerenJDBCManager.createDB();	//calls the method of creating the database
	}
}
