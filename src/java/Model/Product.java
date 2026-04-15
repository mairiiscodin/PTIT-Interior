package Model;

import java.sql.Timestamp;
import java.util.Date;

public class Product {

	private int id;
	private Category category;
	private int categoryId;
	private String sku;
	private String name;
	private String description;
	private double price;
	private int stock;
	private int minStockLevel;
	private String urlImage;
	private int status;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	public Product() {
		this.id = 0;
		this.category = null;
		this.sku = "";
		this.name = "";
		this.description = "";
		this.price = 0;
		this.stock = 0;
		this.minStockLevel = 5;
		this.urlImage = "";
		this.status = 0;
		this.createdAt = null;
		this.updatedAt = null;
	}

	public Product(int id, Category category, String sku, String name, String description, double price, int stock, int minStockLevel, String url_image, int status, Timestamp createdAt, Timestamp updatedAt) {
		this.id = id;
		this.category = category;
		this.sku = sku;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.minStockLevel = minStockLevel;
		this.urlImage = url_image;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Product(int id, int categoryId, String name, String description,
			double price, int stock, String urlImage, int status,
			Timestamp createdAt, Timestamp updatedAt) {
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.urlImage = urlImage;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getter & Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getUrl_image() {
		return urlImage;
	}

	public void setUrl_image(String url_image) {
		this.urlImage = url_image;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getMinStockLevel() {
		return minStockLevel;
	}

	public void setMinStockLevel(int minStockLevel) {
		this.minStockLevel = minStockLevel;
	}

}
