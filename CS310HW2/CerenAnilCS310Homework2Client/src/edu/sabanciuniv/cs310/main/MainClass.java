package edu.sabanciuniv.cs310.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

//Client Part

public class MainClass {

	public static void main(String[] args) {
		try 
		{
			URL addNewProductURL  =  new URL("http://localhost:8080/CerenAnilCS310Homework2WebService/rest/ProductWebService/addNewProduct/Apple/5.0/3000");
					
			InputStreamReader reader = new InputStreamReader(  addNewProductURL.openStream());
			
			BufferedReader rd = new BufferedReader(reader);
			
			while(true)
			{
				String line = rd.readLine();
				if(line==null)
					break;
				System.out.println(line);
			}
			
			
			URL updateProductStockURL  =  new URL("http://localhost:8080/CerenAnilCS310Homework2WebService/rest/ProductWebService/updateProductStock/4/25.0/554");
			
			reader = new InputStreamReader(  updateProductStockURL.openStream());
			
			rd = new BufferedReader(reader);
			
			while(true)
			{
				String line = rd.readLine();
				if(line==null)
					break;
				System.out.println(line);
			}
				
			
			URL deleteProductURL  =  new URL("http://localhost:8080/CerenAnilCS310Homework2WebService/rest/ProductWebService/deleteProduct/3");
			
			reader = new InputStreamReader(  deleteProductURL.openStream());
			
			rd = new BufferedReader(reader);
			
			while(true)
			{
				String line = rd.readLine();
				if(line==null)
					break;
				System.out.println(line);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

}
