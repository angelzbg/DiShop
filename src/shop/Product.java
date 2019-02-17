package shop;

public class Product {

	private String title;
	private double price;
	private String imgPath;
	
	public Product(String title, double price, String imgPath) {
		this.title = title;
		this.price = price;
		this.imgPath = imgPath;
	}

	public String getTitle() {
		return title;
	}

	public double getPrice() {
		return price;
	}

	public String getImgPath() {
		return imgPath;
	}
	
	
	
}
