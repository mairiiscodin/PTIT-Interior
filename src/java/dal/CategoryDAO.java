package dal;

import Model.Category;
import Controller.DBConnect;
import java.sql.*;
import java.util.*;

public class CategoryDAO {

	// ==========================================
	// 1. LẤY DANH SÁCH TẤT CẢ DANH MỤC
	// ==========================================
	public static List<Category> getAllCategories() {
		List<Category> list = new ArrayList<>();

		String sql = "SELECT * FROM categories ORDER BY id ASC";

		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setDescription(rs.getString("description"));
				c.setPrefix(rs.getString("prefix"));
				c.setCreatedAt(rs.getTimestamp("created_at"));
				c.setUpdatedAt(rs.getTimestamp("updated_at"));

				list.add(c);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// ==========================================
	// 2. THÊM MỚI DANH MỤC (INSERT)
	// ==========================================
	public static boolean insert(Category category) {

		String sql = "INSERT INTO categories (name, prefix, description) VALUES (?, ?, ?)";
		boolean isSuccess = false;

		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			String prefix = category.getPrefix() != null ? category.getPrefix().toUpperCase().trim() : "";
			
			ps.setString(1, category.getName());
			ps.setString(2, prefix);
			ps.setString(3, category.getDescription());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				isSuccess = true;
			}

		} catch (Exception e) {
			System.out.println("Lỗi khi thêm danh mục: " + e.getMessage());
			e.printStackTrace();
		}

		return isSuccess;
	}
}
