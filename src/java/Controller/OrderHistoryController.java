/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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

/**
 *
 * @author nguye
 */
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

        int userId = userDAO.getUserIdByFullName(fullName);
        List<Order> orders = orderDAO.getOrdersByUserId(userId);
        for (Order o : orders) {
            System.out.println(o.getId());
        }
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("OrderHistory.jsp").forward(request, response);
    }
}
