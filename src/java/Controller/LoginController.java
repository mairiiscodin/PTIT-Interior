package Controller;

import dal.UserDAO;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        String fullName = request.getParameter("full_name");
        String password = request.getParameter("password");
        
        UserDAO userDAO = new UserDAO();
        User account = userDAO.checkLogin(fullName, password);

        if (account != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user_id", account.getId());
            session.setAttribute("full_name", account.getFullName());
            
            if (fullName.equals("admin") && password.equals("123456")) {
                response.sendRedirect("admin/dashboard");
            } else {
                response.sendRedirect("HomePage.jsp");
            }
        } else {
            request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }
}