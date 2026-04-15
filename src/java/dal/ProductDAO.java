package dal;

import Controller.DBConnect;
import Model.*;
import java.sql.*;
import java.util.*;

public class ProductDAO {

	public List<Product> getAllProducts() {
		List<Product> list = new ArrayList<>();

		String sql = "SELECT * FROM products";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product(
						rs.getInt("id"),
						rs.getInt("category_id"),
						rs.getString("name"),
						rs.getString("description"),
						rs.getDouble("price"),
						rs.getInt("stock"),
						rs.getString("url_image"),
						rs.getInt("status"),
						rs.getTimestamp("created_at"),
						rs.getTimestamp("updated_at")
				);

				list.add(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Product> filterProducts(String keyword, String sort, String category) {
		List<Product> list = new ArrayList<>();

		String sql = "SELECT * FROM products WHERE 1=1";

		if (keyword != null && !keyword.isEmpty()) {
			sql += " AND name LIKE ?";
		}

		if (category != null && !category.isEmpty()) {
			sql += " AND category_id = ?";
		}

		if ("asc".equals(sort)) {
			sql += " ORDER BY price ASC";
		} else if ("desc".equals(sort)) {
			sql += " ORDER BY price DESC";
		}

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			int index = 1;

			if (keyword != null && !keyword.isEmpty()) {
				ps.setString(index++, "%" + keyword + "%");
			}

			if (category != null && !category.isEmpty()) {
				ps.setInt(index++, Integer.parseInt(category));
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product(
						rs.getInt("id"),
						rs.getInt("category_id"),
						rs.getString("name"),
						rs.getString("description"),
						rs.getDouble("price"),
						rs.getInt("stock"),
						rs.getString("url_image"),
						rs.getInt("status"),
						rs.getTimestamp("created_at"),
						rs.getTimestamp("updated_at")
				);

				list.add(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public Product getProductByID(String id) {
		String sql = "SELECT * FROM products WHERE id = ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Product(
						rs.getInt("id"),
						rs.getInt("category_id"),
						rs.getString("name"),
						rs.getString("description"),
						rs.getDouble("price"),
						rs.getInt("stock"),
						rs.getString("url_image"),
						rs.getInt("status"),
						rs.getTimestamp("created_at"),
						rs.getTimestamp("updated_at")
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ======================
	// Hàm map ResultSet -> Product
	// ======================
	private static Product mapProduct(ResultSet rs) throws SQLException {
		Category c = new Category();
		c.setId(rs.getInt("c_id"));
		c.setName(rs.getString("c_name"));
		c.setDescription(rs.getString("c_desc"));
		c.setPrefix(rs.getString("c_prefix"));

		Product p = new Product();
		p.setId(rs.getInt("id"));
		p.setSku(rs.getString("sku"));
		p.setCategory(c);
		p.setName(rs.getString("name"));
		p.setDescription(rs.getString("description"));
		p.setPrice(rs.getDouble("price"));
		p.setStock(rs.getInt("stock"));
		p.setMinStockLevel(rs.getInt("min_stock_level"));
		p.setUrl_image(rs.getString("url_image"));
		p.setStatus(rs.getInt("status"));
		p.setCreatedAt(rs.getTimestamp("created_at"));
		p.setUpdatedAt(rs.getTimestamp("updated_at"));

		return p;
	}

	// =============================
	// 1. LẤY DANH SÁCH sản phẩm
	// =============================
	public static List<Product> getAllProduct(String keyword, Integer categoryId, Integer status, String sort) {
		List<Product> list = new ArrayList<>();

		String sql = "SELECT p.*, c.id AS c_id, c.prefix AS c_prefix, c.name AS c_name, c.description AS c_desc "
				+ "FROM products p "
				+ "JOIN categories c ON p.category_id = c.id "
				+ "WHERE 1=1";

		// A. TÌM KIẾM THEO TỪ KHÓA (Tên hoặc Mã SKU)
		if (keyword != null && !keyword.trim().isEmpty()) {
			sql += " AND (p.name LIKE ? OR p.sku LIKE ?)";
		}

		// B. LỌC THEO DANH MỤC
		if (categoryId != null) {
			sql += " AND p.category_id = ?";
		}

		// C. LỌC THEO TRẠNG THÁI (Mới thêm để khớp với UI)
		if (status != null) {
			sql += " AND p.status = ?";
		}

		// D. SẮP XẾP (SORT)
		if ("priceAsc".equals(sort)) {
			sql += " ORDER BY p.price ASC";
		} else if ("priceDesc".equals(sort)) {
			sql += " ORDER BY p.price DESC";
		} else {
			sql += " ORDER BY p.id DESC"; // Mặc định hiển thị sản phẩm mới nhất lên đầu
		}

		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			int paramIndex = 1;

			// Gán tham số động theo đúng thứ tự đã nối chuỗi ở trên
			if (keyword != null && !keyword.trim().isEmpty()) {
				String key = "%" + keyword.trim() + "%";
				ps.setString(paramIndex++, key); // Cho p.name
				ps.setString(paramIndex++, key); // Cho p.sku
			}

			if (categoryId != null) {
				ps.setInt(paramIndex++, categoryId);
			}

			if (status != null) {
				ps.setInt(paramIndex++, status);
			}

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(mapProduct(rs));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// ======================
	// 3. Lấy sản phẩm theo ID
	// ======================
	public static Product getById(int id) {
		String sql = "SELECT p.*, c.id AS c_id, c.prefix AS c_prefix, c.name AS c_name, c.description AS c_desc "
				+ "FROM products p "
				+ "JOIN categories c ON p.category_id = c.id "
				+ "WHERE p.id = ?";

		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return mapProduct(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Product getBySku(String sku) {
		Product product = null;
		String sql = "SELECT * FROM products WHERE sku = ?"; // Sửa lại tên bảng cho khớp DB của bạn

		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, sku);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					product = new Product();
					product.setId(rs.getInt("id"));
					product.setSku(rs.getString("sku"));
					product.setName(rs.getString("name"));
					// ... set các thuộc tính khác (price, stock, v.v.)
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	// ======================
	// 4. Lấy sản phẩm theo Category
	// ======================
	public static List<Product> getByCategory(int categoryId) {
		String sql = "SELECT p.*, c.id AS c_id, c.prefix AS c_prefix, c.name AS c_name, c.description AS c_desc "
				+ "FROM products p "
				+ "JOIN categories c ON p.category_id = c.id "
				+ "WHERE p.category_id = ? "
				+ "ORDER BY p.id DESC";

		List<Product> list = new ArrayList<>();
		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, categoryId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(mapProduct(rs));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ======================
	// 5. Thêm sản phẩm mới
	// ======================
	public static boolean insert(Product p) {
		String sqlInsert = "INSERT INTO products (category_id, name, description, price, stock, min_stock_level, url_image, status) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		String sqlUpdateSku = "UPDATE products p "
				+ "JOIN categories c ON p.category_id = c.id "
				+ "SET p.sku = CONCAT(c.prefix, '-', LPAD(p.id, 3, '0')) "
				+ "WHERE p.id = ?";

		try (Connection conn = DBConnect.getConnection(); PreparedStatement psInsert = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {

			psInsert.setInt(1, p.getCategory().getId());
			psInsert.setString(2, p.getName());
			psInsert.setString(3, p.getDescription());
			psInsert.setDouble(4, p.getPrice());
			psInsert.setInt(5, p.getStock());
			psInsert.setInt(6, p.getMinStockLevel());
			psInsert.setString(7, p.getUrl_image());
			psInsert.setInt(8, p.getStatus());

			int affectedRows = psInsert.executeUpdate();

			if (affectedRows > 0) {
				try (ResultSet rs = psInsert.getGeneratedKeys()) {
					if (rs.next()) {
						int newlyInsertedId = rs.getInt(1);
						try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateSku)) {
							psUpdate.setInt(1, newlyInsertedId);
							psUpdate.executeUpdate();
						}
					}
				}
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// ======================
	// 6. Cập nhật sản phẩm
	// ======================
	public static boolean update(Product p) {
		String sql = "UPDATE products "
				+ "SET category_id = ?, name = ?, description = ?, price = ?, stock = ?, min_stock_level = ?, url_image = ?, status = ? "
				+ "WHERE id = ?";

		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, p.getCategory().getId());
			ps.setString(2, p.getName());
			ps.setString(3, p.getDescription());
			ps.setDouble(4, p.getPrice());
			ps.setInt(5, p.getStock());
			ps.setInt(6, p.getMinStockLevel());
			ps.setString(7, p.getUrl_image());
			ps.setInt(8, p.getStatus());
			ps.setInt(9, p.getId());

			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// ======================
	// 7. Xóa sản phẩm
	// ======================
	public static boolean delete(int id) {
		String sql = "DELETE FROM products WHERE id = ?";

		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
