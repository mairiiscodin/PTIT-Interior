package Controller;

import Model.*;
import dal.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/add-product")
public class AdminAddProductServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		try {
			String name = request.getParameter("name");
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			double price = Double.parseDouble(request.getParameter("price"));
			int stock = Integer.parseInt(request.getParameter("stock"));
			int minStock = Integer.parseInt(request.getParameter("min_stock_level"));
			String urlImage = request.getParameter("url_image");
			String description = request.getParameter("description");

			// Xử lý trạng thái (mặc định lấy từ thẻ ẩn value="1" trên form)
			int status = 1;
			if (request.getParameter("status") != null) {
				status = Integer.parseInt(request.getParameter("status"));
			}
			
			if (!name.matches("^[\\p{L} ]+$")) {
				response.sendRedirect("products?error=invalidData");
				return;
			}

			Product p = new Product();
			p.setName(name);
			p.setPrice(price);
			p.setStock(stock);
			p.setMinStockLevel(minStock);
			p.setUrl_image(urlImage);
			p.setDescription(description);
			p.setStatus(status);

			Category cat = new Category();
			cat.setId(categoryId);
			p.setCategory(cat);
			
			ProductDAO dao = new ProductDAO();
			boolean success = dao.insert(p);

			// 4. Chuyển hướng kèm thông báo
			if (success) {
				response.sendRedirect("products?success=added");
			} else {
				response.sendRedirect("products?error=failed");
			}

		}  catch (Exception e) {
			// Bắt các lỗi hệ thống khác
			e.printStackTrace();
			response.sendRedirect("products?error=systemError");
		}
	}
}
