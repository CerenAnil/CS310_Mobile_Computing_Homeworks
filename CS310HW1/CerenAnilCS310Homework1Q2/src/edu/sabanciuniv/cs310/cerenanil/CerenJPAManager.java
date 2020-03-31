package edu.sabanciuniv.cs310.cerenanil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CerenJPAManager {

	public static ArrayList<Country> readFromFile(String filename) {
		ArrayList<Country> countries = new ArrayList<Country>();
		try {
			FileReader reader = new FileReader("world.txt");
			BufferedReader bfr = new BufferedReader(reader);
			while (true) {
				String line = bfr.readLine();
				if (line == null) {
					break;
				}
				// System.out.println(line);
				String[] arr = line.split(",");
				String name = arr[0];
				String region = arr[1];
				String capital = arr[2];
				String population = arr[3];

				Country s = new Country(name, region, capital, population);

				countries.add(s);
			}
			reader.close();

		} catch (FileNotFoundException e) {
			System.out.println("no file");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("no have rights to read that file");
			e.printStackTrace();
		}
		return countries;
	}

	public static void writeIntoTable(ArrayList<Country> countries) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("cs310");
		EntityManager entityManager = emf.createEntityManager();

		entityManager.getTransaction().begin();
		for (Country c : countries) {
			entityManager.persist(c);
		}
		entityManager.getTransaction().commit();
	}

	public static Country getCountryById(int countryID) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("cs310");
		EntityManager entityManager = emf.createEntityManager();

		Country c = entityManager.find(Country.class, countryID);

		return c;
	}

	public static void deleteCountryByID(int countryID) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("cs310");
		EntityManager entityManager = emf.createEntityManager();

		Country c = entityManager.find(Country.class, countryID);

		entityManager.getTransaction().begin();
		entityManager.remove(c);
		entityManager.getTransaction().commit();
	}

	public static void updateCountryPopulationByID(int countryID, int population) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("cs310");
		EntityManager entityManager = emf.createEntityManager();

		Country c = entityManager.find(Country.class, countryID);

		entityManager.getTransaction().begin();
		c.setPopulation(Integer.toString(population));
		entityManager.getTransaction().commit();

	}

	public static void main(String[] args) 
	{
		ArrayList<Country> countries = CerenJPAManager.readFromFile("world.txt");

		CerenJPAManager.writeIntoTable(countries);
	}

}
