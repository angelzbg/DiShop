package shop;

import java.util.ArrayList;

public class Subcategory {

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public ArrayList<Product> products;
	
	public Subcategory() {
		products = new ArrayList<Product>();
	}
}
