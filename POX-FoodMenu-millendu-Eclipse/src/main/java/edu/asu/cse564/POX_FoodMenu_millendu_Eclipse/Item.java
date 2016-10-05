package edu.asu.cse564.POX_FoodMenu_millendu_Eclipse;

public class Item {

	private int id;
	private String country;
	private String category;
	private String name;
	private String description;
	private double price;
	
	//setters
	public void setId(int id) {
		this.id = id;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	//getters
	public int getId() {
		return id;
	}
	public String getCountry() {
		return country;
	}
	public String getCategory() {
		return category;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public double getPrice() {
		return price;
	}
	
}
