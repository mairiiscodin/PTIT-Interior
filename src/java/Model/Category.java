package Model;

import java.sql.Timestamp;

public class Category {

	private int id;
	private String name;
	private String description;
	private String prefix;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	// Constructor rỗng
	public Category() {
	}

	// Constructor đầy đủ
	public Category(int id, String name, String description, String prefix, Timestamp createdAt, Timestamp updatedAt) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.prefix = prefix;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Constructor thường dùng khi thêm mới
	public Category(int id, String name, String description) {
		this.id= id;
		this.name = name;
		this.description = description;
	}

	// Getter & Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
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
}
