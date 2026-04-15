
package Controller;

import Model.OrderItem;
import dal.OrderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author nguye
 */
@WebServlet(name = "OrderDetailController", urlPatterns = {"/OrderDetailController"})
public class OrderDetailController extends HttpServlet {
    
    private final OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("id"));
           
            List<OrderItem> details = orderDAO.getOrderDetails(orderId);

            request.setAttribute("details", details);
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("OrderDetail.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("history");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
