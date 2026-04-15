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
import java.util.List;

@WebServlet("/admin/users")
public class AdminUsersServlet extends HttpServlet {

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
		Integer userId = (session != null) ? (Integer) session.getAttribute("user_id") : null;
		if (userId == null) {
			response.sendRedirect(request.getContextPath() + "/Login.jsp");
			return;
		}
		request.setAttribute("user_id", userId);

		String keyword = request.getParameter("keyword");
		String role = request.getParameter("role");
		String status = request.getParameter("status");

		List<User> userList = UserDAO.getAllUsers(keyword, role, status);

		request.setAttribute("userList", userList);
		request.getRequestDispatcher("/admin/admin-users.jsp").forward(request, response);
	}
}
