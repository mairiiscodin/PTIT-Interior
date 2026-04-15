package Controller;

import Model.CartItem;
import Model.Product;
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
import java.util.List;

/**
 *
 * @author nguye
 */
@WebServlet(name = "CartController", urlPatterns = {"/cart"})
public class CartController extends HttpServlet {

    private final CartDAO cartDAO = new CartDAO();
    private final ProductDAO productDAO = new ProductDAO();
    private final UserDAO userDAO = new UserDAO();

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

        List<CartItem> items = cartDAO.getCartItems(userId);
        double total = cartDAO.calculateTotal(userId);

        request.setAttribute("items", items);
        request.setAttribute("total", total);

        request.getRequestDispatcher("Cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String fullName = (String) session.getAttribute("full_name");
        Integer userId = (Integer) session.getAttribute("user_id");

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
                if (qty <= 0) {
                    cartDAO.deleteCartItem(userId, productId);
                } else {
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
