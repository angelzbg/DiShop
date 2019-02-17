package shop;

import java.util.ArrayList;

public class Category {

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public ArrayList<Subcategory> subcategories;
	
	public Category() {
		subcategories = new ArrayList<Subcategory>();
	}
	
}
