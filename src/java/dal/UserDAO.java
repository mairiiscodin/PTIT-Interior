package dal;

import Controller.DBConnect;
import Model.User;
import java.sql.*;
import java.util.*;

public class UserDAO {

	// Phương thức kiểm tra đăng nhập
	public User checkLogin(String fullName, String password) {
		String sql = "SELECT * FROM users WHERE full_name=? AND password=?";

		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, fullName);
			ps.setString(2, password);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setId(rs.getInt("id"));
					user.setFullName(rs.getString("full_name"));
					user.setEmail(rs.getString("email"));
					user.setPassword(rs.getString("password"));
					user.setPhone(rs.getString("phone"));
					user.setAddress(rs.getString("address"));
					return user;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // Trả về null nếu sai thông tin hoặc có lỗi
	}

	// Phương thức đăng ký
	public boolean register(String fullName, String email, String password, String phone, String address) {
		String sql = "INSERT INTO users(full_name,email,password,phone,address) VALUES(?,?,?,?,?)";

		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, fullName);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setString(4, phone);
			ps.setString(5, address);

			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0; // Trả về true nếu insert thành công

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public int getUserIdByFullName(String fullName) {
		String sql = "SELECT id FROM users WHERE full_name = ?";
		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, fullName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static User getUserById(int id) {
		User user = null;
		String sql = "SELECT * FROM users WHERE id = ?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setFullName(rs.getString("full_name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setAvatar(rs.getString("avatar"));
				user.setRole(rs.getString("role"));
				user.setStatus(rs.getInt("status"));
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public boolean updateUser(User user) {
		String sql = "UPDATE users SET full_name=?, email=?, phone=?, address=? WHERE id=?";
		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, user.getFullName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPhone());
			ps.setString(4, user.getAddress());
			ps.setInt(5, user.getId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// =========================
	// LẤY DANH SÁCH USER (ADMIN)
	// =========================
	public static List<User> getAllUsers(String keyword, String role, String status) {
		List<User> list = new ArrayList<>();

		String sql = "SELECT * FROM users WHERE 1=1";

		if (keyword != null && !keyword.trim().isEmpty()) {
			sql += " AND (full_name LIKE ? OR email LIKE ? OR phone LIKE ?)";
		}
		if (role != null && !role.trim().isEmpty()) {
			sql += " AND role = ?";
		}
		if (status != null && !status.trim().isEmpty()) {
			sql += " AND status = ?";
		}

		sql += " ORDER BY id ASC";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			int index = 1;

			if (keyword != null && !keyword.trim().isEmpty()) {
				ps.setString(index++, "%" + keyword + "%");
				ps.setString(index++, "%" + keyword + "%");
				ps.setString(index++, "%" + keyword + "%");
			}

			if (role != null && !role.trim().isEmpty()) {
				ps.setString(index++, role);
			}

			if (status != null && !status.trim().isEmpty()) {
				ps.setInt(index++, Integer.parseInt(status));
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setFullName(rs.getString("full_name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setAvatar(rs.getString("avatar"));
				user.setRole(rs.getString("role"));
				user.setStatus(rs.getInt("status"));
				user.setCreatedAt(rs.getTimestamp("created_at"));
				user.setUpdatedAt(rs.getTimestamp("updated_at"));

				list.add(user);
			}

			rs.close();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// =========================
	// CẬP NHẬT TRẠNG THÁI USER
	// =========================
	public static boolean updateUserStatus(int userId, int status) {
		String sql = "UPDATE users SET status = ? WHERE id = ?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, status);
			ps.setInt(2, userId);

			int rows = ps.executeUpdate();

			ps.close();
			conn.close();

			return rows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// =========================
	// KIỂM TRA EMAIL TỒN TẠI
	// =========================
	public static boolean emailExists(String email) {
		String sql = "SELECT * FROM users WHERE email = ?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			boolean exists = rs.next();

			rs.close();
			ps.close();
			conn.close();

			return exists;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// =========================
	// THÊM USER (ADMIN)
	// =========================
	public static boolean addUser(String fullName, String email, String password,
			String phone, String address, String role, int status) {

		// kiểm tra email đã tồn tại chưa
		if (emailExists(email)) {
			return false;
		}

		String sql = "INSERT INTO users (full_name, email, password, phone, address, role, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, fullName);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setString(4, phone);
			ps.setString(5, address);
			ps.setString(6, role);
			ps.setInt(7, status);

			int rows = ps.executeUpdate();

			ps.close();
			conn.close();

			return rows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	// =========================
	// XÓA USER
	// =========================

	public static boolean deleteUserById(int id) {
		String sql = "DELETE FROM users WHERE id = ?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);

			int rows = ps.executeUpdate();

			ps.close();
			conn.close();

			return rows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
