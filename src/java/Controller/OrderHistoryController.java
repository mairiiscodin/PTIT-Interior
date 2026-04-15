package Controller;

import Model.Order;
import dal.OrderDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@WebServlet(name = "OrderHistoryController", urlPatterns = {"/history"})
public class OrderHistoryController extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();
    private final UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String fullName = (String) session.getAttribute("full_name");

        if (fullName == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("user_id");
        List<Order> orders = OrderDAO.getOrdersByUserId(userId);
        
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("OrderHistory.jsp").forward(request, response);
    }
}
