package Model;

import java.sql.Timestamp;

public class User {

	private int id;
	private String fullName;
	private String email;
	private String password;
	private String phone;
	private String address;
	private String avatar;
	private String role;
	private int status;
	private Timestamp createdAt;
	private Timestamp updatedAt;

	// Constructor mặc định
	public User() {
		this.id = 0;
		this.fullName = "";
		this.email = "";
		this.password = "";
		this.phone = "";
		this.address = "";
		this.avatar = "";
		this.role = "user";
		this.status = 1;
		this.createdAt = null;
		this.updatedAt = null;
	}

    public User(int id, String fullName, String email, String phone, String address) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

	// Constructor đầy đủ
	public User(int id, String fullName, String email, String password,
			String phone, String address, String avatar,
			String role, int status, Timestamp createdAt, Timestamp updatedAt) {
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.avatar = avatar;
		this.role = role;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// ===== Getter & Setter =====
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
}
