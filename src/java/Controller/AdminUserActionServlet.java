package Controller;

import Model.User;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/user-action")
public class AdminUserActionServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String action = request.getParameter("action");

		try {
			if ("add".equals(action)) {
				handleAddUser(request, response);

			} else if ("delete".equals(action)) {
				handleDeleteUser(request, response);

			} else if ("updateStatus".equals(action)) {
				handleUpdateStatus(request, response);

			} else {
				response.sendRedirect(request.getContextPath() + "/admin/users?error=invalidAction");
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/admin/users?error=serverError");
		}
	}

	// =========================
	// THÊM USER
	// =========================
	private void handleAddUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String fullName = request.getParameter("fullName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String role = request.getParameter("role");
		String statusStr = request.getParameter("status");

		int status = 1;
		if (statusStr != null && !statusStr.isEmpty()) {
			status = Integer.parseInt(statusStr);
		}

		if (!fullName.matches("^[\\p{L} ]+$")) {
			response.sendRedirect("users?error=invalidData");
			return;
		}

		boolean success = UserDAO.addUser(fullName, email, password, phone, address, role, status);

		if (success) {
			response.sendRedirect(request.getContextPath() + "/admin/users?success=added");
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/users?error=addFailed");
		}
	}

	// =========================
	// XÓA USER
	// =========================
	private void handleDeleteUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		int userId = Integer.parseInt(request.getParameter("userId"));

		boolean success = UserDAO.deleteUserById(userId);

		if (success) {
			response.sendRedirect(request.getContextPath() + "/admin/users?success=deleted");
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/users?error=deleteFailed");
		}
	}

	// =========================
	// CẬP NHẬT TRẠNG THÁI USER
	// =========================
	private void handleUpdateStatus(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		int userId = Integer.parseInt(request.getParameter("userId"));
		int status = Integer.parseInt(request.getParameter("status"));

		boolean success = UserDAO.updateUserStatus(userId, status);

		if (success) {
			response.sendRedirect(request.getContextPath() + "/admin/users?success=updated");
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/users?error=updateFailed");
		}
	}
}
