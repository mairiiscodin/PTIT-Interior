package Model;

import java.sql.Timestamp;
import Model.*;

public class  InventoryTransaction {

	private int id;

	private Product product;
	private User user;

	private String transactionType; 
	private int quantityChanged;
	private String note;
	private Timestamp createdAt;

	// --- Constructor rỗng ---
	public InventoryTransaction() {
	}

	// --- Constructor đầy đủ ---
	public InventoryTransaction(int id, Product product, User user, String transactionType,
			int quantityChanged, String note, Timestamp createdAt) {
		this.id = id;
		this.product = product;
		this.user = user;
		this.transactionType = transactionType;
		this.quantityChanged = quantityChanged;
		this.note = note;
		this.createdAt = createdAt;
	}

	// --- GETTER & SETTER ---
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public int getQuantityChanged() {
		return quantityChanged;
	}

	public void setQuantityChanged(int quantityChanged) {
		this.quantityChanged = quantityChanged;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}
