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
import java.util.List;

@WebServlet("/admin/order-detail")
public class AdminOrderDetailServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			int orderId = Integer.parseInt(request.getParameter("id"));

			// 1. Lấy thông tin Order (lúc này order.getUser().getId() đã có giá trị)
			Order order = OrderDAO.getOrderById(orderId);

			if (order != null) {
				// 2. Lấy thông tin User đầy đủ dựa trên ID có sẵn trong Order
				// Bạn có thể gọi trực tiếp UserDAO.getUserById vì nó là phương thức static
				User fullUser = UserDAO.getUserById(order.getUser().getId());

				// 3. Đổ ngược thông tin đầy đủ vào lại Order
				order.setUser(fullUser);

				// 4. Lấy chi tiết các món hàng
				List<OrderItem> orderItems = OrderItemDAO.getItemsByOrderId(orderId);

				// 5. Đẩy dữ liệu sang JSP
				request.setAttribute("order", order);
				request.setAttribute("orderItems", orderItems);
				request.setAttribute("totalAmount", order.getTotalAmount());

				request.getRequestDispatcher("/admin/show-orders.jsp").forward(request, response);
			} else {
				response.sendRedirect("orders?error=notfound");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("orders?error=system");
		}
	}
}
