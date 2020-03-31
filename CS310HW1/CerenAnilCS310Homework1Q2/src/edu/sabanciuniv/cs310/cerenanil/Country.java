package edu.sabanciuniv.cs310.cerenanil;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class Country {
	
	@Id @GeneratedValue
	private int id;
	private String name;
	private String region;
	private String capital;
	private String population;

	public Country() {
		super();
	}

	public Country( String name, String region, String capital, String population) {

		super();
	
		this.name = name;
		this.region = region;
		this.capital = capital;
		this.population = population;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

}
