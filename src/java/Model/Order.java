package Model;

import java.sql.Timestamp;
import java.util.List;

public class Order {

	private int id;
	private User user;
	private double totalAmount;
	private String status;
	private String paymentMethod;
	private String shippingAddress;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	// Có thể dùng khi lấy chi tiết đơn hàng
	private List<OrderItem> items;

	// Constructor rỗng

	public Order() {
		this.id = 0;
		this.user = null;
		this.totalAmount =0;
		this.status = "";
		this.paymentMethod = "";
		this.shippingAddress = "";
		this.createdAt = null;
		this.updatedAt = null;
		this.items = null;
	}
	

	// Constructor đầy đủ
	public Order(int id, User user, double totalAmount, String status, String paymentMethod,
			String shippingAddress, Timestamp createdAt, Timestamp updatedAt) {
		this.id = id;
		this.user = user;
		this.totalAmount = totalAmount;
		this.status = status;
		this.paymentMethod = paymentMethod;
		this.shippingAddress = shippingAddress;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Constructor thường dùng
	public Order(int id, User user, double totalAmount, String status, String paymentMethod, String shippingAddress) {
		this.id = id;
		this.user = user;
		this.totalAmount = totalAmount;
		this.status = status;
		this.paymentMethod = paymentMethod;
		this.shippingAddress = shippingAddress;
	}

	// Getter & Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
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

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
}
