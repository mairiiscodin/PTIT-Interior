/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.CartItem;
import dal.CartDAO;
import dal.OrderDAO;
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

/**
 *
 * @author nguye
 */
@WebServlet(name = "OrderController", urlPatterns = {"/order"})
public class OrderController extends HttpServlet {

    private final OrderDAO orderDAO = new OrderDAO();
    private final CartDAO cartDAO = new CartDAO();
    private final UserDAO userDAO = new UserDAO();
    
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String fullName = (String) session.getAttribute("full_name");
        
        if (fullName == null) { response.sendRedirect("login.jsp"); return; }
        
        int userId = userDAO.getUserIdByFullName(fullName);
        String address = request.getParameter("address");
        String method = request.getParameter("payment_method");
        double totalAmount = Double.parseDouble(request.getParameter("total_amount"));

        // 1. Lấy danh sách sản phẩm hiện tại trong giỏ
        List<CartItem> items = cartDAO.getCartItems(userId);
        
        if (items != null && !items.isEmpty()) {
            // 2. Tạo Order mới và lấy ID vừa tạo (Dùng Transaction trong SQL)
            int orderId = orderDAO.createOrder(userId, totalAmount, "Chờ xác nhận", method, address, items);
            
            if (orderId > 0) {
                // 3. Thanh toán thành công -> Xóa sạch giỏ hàng của User
                cartDAO.clearCart(userId);
                response.sendRedirect(request.getContextPath() + "/Thank.jsp"); // Trang thông báo thành công
            } else {
                response.sendRedirect("Cart.jsp?error=order_failed");
            }
        }
    }

   
}
