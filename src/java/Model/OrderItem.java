package Model;

import java.sql.Timestamp;

public class OrderItem {

	private int id;
	private Order order;
	private Product product;
	private double price;
	private int quantity;
	private double subtotal;
	private Timestamp createdAt;

	// Constructor rỗng
	public OrderItem() {
	}

	// Constructor đầy đủ
	public OrderItem(int id, Order order, Product product, double price, int quantity, double subtotal,
			Timestamp createdAt) {
		this.id = id;
		this.order = order;
		this.product = product;
		this.price = price;
		this.quantity = quantity;
		this.subtotal = subtotal;
		this.createdAt = createdAt;
	}

	// Constructor thường dùng
	public OrderItem(int id, Order order, Product product, double price, int quantity, double subtotal) {
		this.id = id;
		this.order = order;
		this.product = product;
		this.price = price;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}

	// Getter & Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
