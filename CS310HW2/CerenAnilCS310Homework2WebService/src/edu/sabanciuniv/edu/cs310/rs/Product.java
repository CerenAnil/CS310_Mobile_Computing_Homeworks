package edu.sabanciuniv.edu.cs310.rs;

public class Product {

	private int productID;
	private String productName;
	private Double productPrice;
	private Integer productStock;

	public Product() {
		super();
	}

	public Product( String productName, Double productPrice, Integer productStock) {

		super();
		this.productName = productName;
		this.productPrice = productPrice;
		this.productStock = productStock;
	}
	
	public int getProductID() {
		return productID;
	}

	public void setProductID(Integer id) {
		this.productID = id;
	}

	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String name) {
		this.productName = name;
	}

	
	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double price) {
		this.productPrice = price;
	}

	
	public Integer getProductStock() {
		return productStock;
	}

	public void setProductStock(Integer stock) {
		this.productStock = stock;
	}

}
