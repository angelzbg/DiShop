package shop;

import java.util.ArrayList;

public class ShoppingCart {

	public ArrayList<Product> products;
	public ArrayList<Integer> quantity;
	
	public ShoppingCart() {
		products = new ArrayList<Product>();
		quantity = new ArrayList<Integer>();
	}
	
	public void clearCart() {
		this.products.clear();
		this.quantity.clear();
	}
	
}