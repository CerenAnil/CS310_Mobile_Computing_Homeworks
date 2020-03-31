package edu.sabanciuniv.edu.cs310.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

//Server Part

@Path("ProductWebService")
public class ProductWebService
{
	@GET
	@Path("addNewProduct/{name}/{price}/{stock}")
	public String addNewProduct (@PathParam("name") String productName, @PathParam("price") Double productPrice, @PathParam("stock") Integer productStock)
	{
		Product prod = new Product( productName, productPrice, productStock);
		return CerenJDBCManager.save(prod);
		
	}
	
	@GET
	@Path("deleteProduct/{id}")
	public String deleteProduct (@PathParam("id") int productID)
	{
		return CerenJDBCManager.deleteProductByID(productID);
		
	}
	
	@GET
	@Path("updateProductStock/{id}/{price}/{stock}")
	public String updateProductStock (@PathParam("id") int productID, @PathParam("price") Double productPrice, @PathParam("stock") Integer productStock)
	{
		return CerenJDBCManager.updateProductStock(productID, productPrice, productStock);
		
	}
		
}
