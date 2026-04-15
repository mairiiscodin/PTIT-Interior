package Controller;

import dal.*;
import Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.*;

@WebServlet("/admin/update-order-status")
public class AdminUpdateOrderServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String orderIdStr = request.getParameter("orderId");
		String newStatus = request.getParameter("status");

		String redirectUrl = request.getContextPath() + "/admin/orders";

		if (orderIdStr != null && !orderIdStr.isEmpty() && newStatus != null) {
			try {
				int orderId = Integer.parseInt(orderIdStr);
				Order currentOrder = OrderDAO.getOrderById(orderId);

				boolean isConfirmedAction = false;

				if (currentOrder != null) {
					String oldStatus = currentOrder.getStatus();

					if ("Chờ xác nhận".equals(oldStatus) && "Đã xác nhận".equals(newStatus)) {
						isConfirmedAction = true;

						List<OrderItem> orderItems = OrderItemDAO.getItemsByOrderId(orderId);

						HttpSession session = request.getSession();
						Integer adminId = (Integer) session.getAttribute("user_id");

						if (adminId != null) {
							User adminUser = new User();
							adminUser.setId(adminId);

							if (orderItems != null && !orderItems.isEmpty()) {
								for (OrderItem item : orderItems) {
									InventoryTransaction tx = new InventoryTransaction();

									tx.setProduct(item.getProduct());
									tx.setUser(adminUser);
									tx.setTransactionType("Xuất bán");
									tx.setQuantityChanged(item.getQuantity());
									tx.setNote("Xuất bán cho đơn hàng #" + orderId);

									InventoryTransactionDAO.addExportTransaction(tx);
								}
							}
						} else {
							System.out.println("Lỗi: Không tìm thấy ID người dùng trong Session!");
						}
					}
				}

				boolean isUpdated = OrderDAO.updateOrderStatus(orderId, newStatus);

				if (isUpdated) {
					if (isConfirmedAction) {
						redirectUrl += "?success=confirmed";
					} else {
						redirectUrl += "?success=updated";
					}
				} else {
					redirectUrl += "?error=failed";
				}

			} catch (Exception e) {
				e.printStackTrace();
				redirectUrl += "?error=failed";
			}
		} else {
			redirectUrl += "?error=failed";
		}

		response.sendRedirect(redirectUrl);
	}
}
