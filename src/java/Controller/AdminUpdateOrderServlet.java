package Controller;

import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/update-order-status")
public class AdminUpdateOrderServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// 2. Lấy dữ liệu từ form (khớp với thuộc tính name="" trong file JSP)
		String orderIdStr = request.getParameter("orderId");
		String newStatus = request.getParameter("status");

		if (orderIdStr != null && !orderIdStr.isEmpty() && newStatus != null) {
			try {
				int orderId = Integer.parseInt(orderIdStr);

				// 3. Gọi hàm update trong DAO
				boolean isUpdated = OrderDAO.updateOrderStatus(orderId, newStatus);

			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		response.sendRedirect(request.getContextPath() + "/admin/orders");
	}
}
