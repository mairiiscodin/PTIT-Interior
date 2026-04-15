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
import jakarta.servlet.http.HttpSession;
import java.util.*;

@WebServlet("/admin/products")
public class AdminProductServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			String fullName = (String) session.getAttribute("full_name");
			request.setAttribute("userName", fullName);
		}

		String keyword = request.getParameter("keyword");
		String categoryIdStr = request.getParameter("categoryId");
		String statusStr = request.getParameter("status");

		// 2. Chuyển đổi kiểu dữ liệu cho Filter (xử lý null)
		Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty())
				? Integer.parseInt(categoryIdStr) : null;

		Integer status = (statusStr != null && !statusStr.isEmpty())
				? Integer.parseInt(statusStr) : null;

		// 3. Gọi DAO để lấy danh sách dữ liệu
		List<Product> productList = ProductDAO.getAllProduct(keyword, categoryId, status, "");

		List<Category> categoryList = CategoryDAO.getAllCategories();

		// 4. Giữ lại giá trị filter để hiển thị lại trên giao diện (giúp thẻ <select> giữ được trạng thái)
		request.setAttribute("keyword", keyword);

		// 5. Gửi dữ liệu sang JSP
		request.setAttribute("productList", productList);
		request.setAttribute("categoryList", categoryList);

		// 6. Forward sang trang JSP quản lý sản phẩm
		request.getRequestDispatcher("/admin/admin-products.jsp").forward(request, response);
	}
}
