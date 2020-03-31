package edu.sabanciuniv.cs310.cerenanil;

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


public class CerenJDBCManager {

	public static ArrayList<Country> readFromFile(String filename)
	{
		ArrayList<Country> countries = new ArrayList<Country>();
		try 
		{
			FileReader reader = new FileReader("world.txt");
			BufferedReader bfr = new BufferedReader(reader);
			while(true)
			{
				String line = bfr.readLine();
				if (line == null)
				{
					break;
				}
				//System.out.println(line);
				String[] arr = line.split(",");
				String name = arr[0];
				String region = arr[1];
				String capital = arr[2];
				String population = arr[3];
				Country s = new Country(name, region, capital, population);
				
				countries.add(s);
			}
			reader.close();
		
		}
		catch (FileNotFoundException e) {
			System.out.println("no file");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("no have rights to read that file");
			e.printStackTrace();
		}
		return countries;
	}
	
	public static void writeIntoTable(ArrayList<Country> countries) {
		
		try
		{
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/cs310", "root", "1q2w3e4r");		
			for (Country c : countries)
			{
				PreparedStatement ps =  connection.prepareStatement("insert into Country (name, region, capital, population) values (?,?,?,?) ");
				ps.setString(1, c.getName());
				ps.setString(2, c.getRegion());
				ps.setString(3, c.getCapital());
				ps.setString(4, c.getPopulation());
				
				ps.execute();
			}
			
			
			System.out.println("Data inserted!!!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Country getCountryById (int countryID)
	{
		Country co = null;
		Connection connection;
		try {
		connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/cs310", "root", "1q2w3e4r");		
			PreparedStatement ps =  connection.prepareStatement("SELECT name, region, capital, population from Country WHERE idCountry = ?");
			ps.setInt(1,countryID);
			ResultSet rs =	ps.executeQuery();
			
			while(rs.next())
			{
				String n  = rs.getString("name");
				String r  = rs.getString("region");
				String c  = rs.getString("capital");
				String p  = rs.getString("population");
				Country C = new Country(n, r, c, p);
				
				co = C;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return co ;
	
	}
	
	public static void deleteCountryByID (int countryID)
	{
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/cs310", "root", "1q2w3e4r");
			PreparedStatement ps =  connection.prepareStatement("DELETE FROM Country WHERE idCountry = ?");
			ps.setInt(1,countryID);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void updateCountryPopulationByID (int countryID, int population)
	{

		Connection connection;
		try {
			
			String population2 = Integer.toString(population);
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/cs310", "root", "1q2w3e4r");
			PreparedStatement ps =  connection.prepareStatement("UPDATE Country SET population = ? WHERE idCountry = ?");
			
			ps.setString(1,population2);
			ps.setInt(2,countryID);
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) 
	{		 
		ArrayList<Country> countries = CerenJDBCManager.readFromFile("world.txt");
		
		CerenJDBCManager.writeIntoTable(countries);		
	}
}
