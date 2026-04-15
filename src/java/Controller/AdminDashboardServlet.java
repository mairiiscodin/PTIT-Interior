package Controller;

import Model.User;
import dal.DashboardDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession(false);
		if (session != null) {
			String fullName = (String) session.getAttribute("full_name");
			request.setAttribute("userName", fullName);
		}
		// 1. Lấy dữ liệu 5 thẻ (Cards)
		request.setAttribute("totalProducts", DashboardDAO.getCount("products"));
		request.setAttribute("totalOrders", DashboardDAO.getCount("orders"));
		request.setAttribute("pendingOrders", DashboardDAO.getPendingOrders());
		request.setAttribute("totalUsers", DashboardDAO.getCount("users"));
		request.setAttribute("totalStock", DashboardDAO.getTotalStock());

		request.setAttribute("totalRevenue", DashboardDAO.getTotalRevenue());
		request.setAttribute("pendingOrders", DashboardDAO.getPendingOrders());

		// 2. Lấy dữ liệu 2 bảng Top
		request.setAttribute("topProducts", DashboardDAO.getTopBestSellers(5));
		request.setAttribute("topUsers", DashboardDAO.getTopCustomers(5));

		request.getRequestDispatcher("/admin/admin-dashboard.jsp")
				.forward(request, response);
	}
}
