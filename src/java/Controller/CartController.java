/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Cart;
import Model.CartItem;
import Model.Product;
import Model.User;
import dal.CartDAO;
import dal.ProductDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nguye
 */
@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

   private final CartDAO cartDAO = new CartDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final UserDAO userDAO = new UserDAO();

    /**
     * doGet: Xử lý hiển thị giỏ hàng và thêm sản phẩm thông qua URL
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String fullName = (String) session.getAttribute("full_name");
        Integer userId = (Integer) session.getAttribute("user_id");
        // 1. Kiểm tra đăng nhập
        if (fullName == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        String action = request.getParameter("action");
        
        
        if ("add".equals(action)) {
            try {
                int productId = Integer.parseInt(request.getParameter("id"));
                // Mặc định tăng thêm 1 sản phẩm
                cartDAO.updateCartItem(userId, productId, 1); 
                response.sendRedirect("cart"); 
                return; // Kết thúc hàm ở đây để không chạy xuống phần forward bên dưới
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // 3. Lấy dữ liệu từ Database để hiển thị lên JSP
        List<CartItem> items = cartDAO.getCartItems(userId);
        for(CartItem i : items){
            System.out.println(i);
        }
        double total = cartDAO.calculateTotal(userId);

        // Đẩy dữ liệu sang request attribute
        request.setAttribute("items", items);
        request.setAttribute("total", total);

        // Chuyển hướng sang trang cart.jsp
        request.getRequestDispatcher("Cart.jsp").forward(request, response);
    }

    /**
     * doPost: Chuyên trách xử lý các yêu cầu AJAX từ giao diện
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String fullName = (String) session.getAttribute("full_name");
        Integer userId = (Integer) session.getAttribute("user_id");

        // Trả về lỗi 401 nếu chưa đăng nhập (AJAX sẽ bắt lỗi này)
        if (fullName == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String action = request.getParameter("action");
        if ("updateAjax".equals(action)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            try (PrintWriter out = response.getWriter()) {
                int productId = Integer.parseInt(request.getParameter("id"));
                int qty = Integer.parseInt(request.getParameter("qty"));

                // 1. Cập nhật vào Database
                if(qty <= 0){
                    System.out.println("DEBUG: Đang gọi hàm xóa cho Product ID: " + productId);
                    cartDAO.deleteCartItem(userId, productId);
                } else{
                    cartDAO.setCartItemQuantity(userId, productId, qty);
                }
                // 2. Tính toán các con số mới để trả về cho AJAX
                double itemSubTotal = 0;
                Product p = productDAO.getProductByID(Integer.toString(productId));
                if (p != null) {
                    itemSubTotal = p.getPrice() * qty;
                }
                double cartTotal = cartDAO.calculateTotal(userId);

                // 3. Tạo chuỗi JSON trả về cho Client
                // Lưu ý: Sử dụng Locale.US để đảm bảo dấu thập phân là dấu chấm, không bị lỗi JSON
                String jsonResponse = String.format(java.util.Locale.US,
                    "{\"subTotal\": %.0f, \"cartTotal\": %.0f}", 
                    itemSubTotal, cartTotal
                );
                
                out.print(jsonResponse);
                out.flush();
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }
}
