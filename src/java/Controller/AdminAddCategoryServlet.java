package Controller;

import Model.Category;
import dal.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/add-category")
public class AdminAddCategoryServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		try {
			String name = request.getParameter("name");
			String prefix = request.getParameter("prefix");
			String description = request.getParameter("description");

			if (!name.matches("^[\\p{L} ]+$") || !prefix.matches("^[\\p{L} ]+$")) {
				response.sendRedirect("products?error=invalidData");
				return;
			}

			Category category = new Category();
			category.setName(name);
			category.setPrefix(prefix);
			category.setDescription(description);

			// 4. Gọi DAO để lưu thẳng vào Database
			CategoryDAO dao = new CategoryDAO();
			boolean success = dao.insert(category);

			// 5. Kiểm tra kết quả và chuyển hướng về trang danh sách sản phẩm
			if (success) {
				response.sendRedirect("products?success=categoryAdded");
			} else {
				response.sendRedirect("products?error=failed");
			}

		} catch (Exception e) {
			response.sendRedirect("products?error=systemError");
		}
	}
}
