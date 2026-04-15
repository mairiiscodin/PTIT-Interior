
package Controller;

import Model.Order;
import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;


@WebServlet("/admin/orders")
public class AdminOrdersServlet extends HttpServlet {

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

		// Lấy dữ liệu từ form tìm kiếm / lọc
		String keyword = request.getParameter("keyword");
		String status = request.getParameter("status");

		List<String> statusList = Arrays.asList(
				"Chờ xác nhận",
				"Đã xác nhận",
				"Đang giao",
				"Hoàn thành",
				"Đã hủy"
		);
		request.setAttribute("statusList", statusList);

		List<Order> orderList;

		// Nếu không nhập gì thì lấy toàn bộ
		if ((keyword == null || keyword.trim().isEmpty())
				&& (status == null || status.trim().isEmpty())) {

			orderList = OrderDAO.getAllOrders();

		} else {
			orderList = OrderDAO.searchOrders(keyword, status);
		}

		// Giữ lại dữ liệu filter để hiển thị lại trên form
		request.setAttribute("keyword", keyword);
		request.setAttribute("status", status);

		// Gửi dữ liệu sang JSP
		request.setAttribute("orderList", orderList);

		// Forward sang trang quản lý đơn
		request.getRequestDispatcher("/admin/admin-orders.jsp")
				.forward(request, response);
	}
}
