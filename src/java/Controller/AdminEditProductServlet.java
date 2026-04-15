
package Controller;

import Model.*;
import dal.*;
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/admin/edit-product")
public class AdminEditProductServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String idStr = request.getParameter("id");
		if (idStr == null || idStr.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/admin/products");
			return;
		}

		int productId = Integer.parseInt(idStr);

		// Lấy thông tin sản phẩm cần sửa
		ProductDAO productDAO = new ProductDAO();
		Product product = productDAO.getById(productId);

		if (product == null) {
			response.sendRedirect(request.getContextPath() + "/admin/products?error=notfound");
			return;
		}

		// Lấy danh sách danh mục để đổ vào thẻ <select>
		CategoryDAO categoryDAO = new CategoryDAO();
		List<Category> categoryList = categoryDAO.getAllCategories();

		// Đẩy dữ liệu sang JSP
		request.setAttribute("product", product);
		request.setAttribute("categoryList", categoryList);

		// Chuyển hướng sang trang giao diện Sửa
		request.getRequestDispatcher("/admin/edit-product.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		try {
			// Lấy ID sản phẩm
			int id = Integer.parseInt(request.getParameter("id"));

			// Đọc các thông tin cho phép sửa
			String name = request.getParameter("name");
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			double price = Double.parseDouble(request.getParameter("price"));
			int stock = Integer.parseInt(request.getParameter("stock"));
			int minStock = Integer.parseInt(request.getParameter("min_stock_level"));
			String urlImage = request.getParameter("url_image");
			String description = request.getParameter("description");
			int status = request.getParameter("status") != null ? 1 : 0;
					
			if (!name.matches("^[\\p{L} ]+$")) {
				response.sendRedirect("products?error=invalidData");
				return;
			}

			// Đóng gói vào đối tượng Product
			Product p = new Product();
			p.setId(id);
			p.setName(name);
			p.setPrice(price);
			p.setStock(stock);
			p.setMinStockLevel(minStock);
			p.setUrl_image(urlImage);
			p.setDescription(description);
			p.setStatus(status);

			// Gán Category
			Category cat = new Category();
			cat.setId(categoryId);
			p.setCategory(cat);

			ProductDAO dao = new ProductDAO();
			boolean success = dao.update(p);

			// Chuyển hướng về trang danh sách kèm thông báo
			if (success) {
				response.sendRedirect(request.getContextPath() + "/products?success=updated");
			} else {
				response.sendRedirect(request.getContextPath() + "/products?error=failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/products?error=systemError");
		}
	}
}