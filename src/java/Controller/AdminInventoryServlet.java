package Controller;

import Model.*;
import dal.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/inventory", "/admin/inventory-action"})
public class AdminInventoryServlet extends HttpServlet {

	// ==========================================
	// XỬ LÝ KHI MỞ TRANG (GET) - TÍCH HỢP BỘ LỌC
	// ==========================================
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

		// 1. Lấy tham số tìm kiếm từ URL 
		String searchSku = request.getParameter("sku");
		String searchType = request.getParameter("type");

		// 2. Gọi hàm gộp (vừa lọc vừa lấy tất cả dựa vào tham số truyền vào)
		List<InventoryTransaction> transactionList = InventoryTransactionDAO.getAllTransactions(searchSku, searchType);

		// 3. Đẩy dữ liệu sang JSP
		request.setAttribute("transactionList", transactionList);
		request.getRequestDispatcher("/admin/admin-inventory.jsp").forward(request, response);
	}

	// ==========================================
	// XỬ LÝ KHI BẤM NÚT TẠO GIAO DỊCH (POST)
	// ==========================================
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Cấu hình UTF-8 để nhận ghi chú tiếng Việt không bị lỗi font
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");

		// Xử lý chung cho cả Nhập kho và Xuất kho
		if ("transaction".equals(action)) {
			try {
				// 1. Lấy các dữ liệu từ Form gửi lên
				String sku = request.getParameter("sku");
				String transactionType = request.getParameter("transaction_type"); 
				int quantityChanged = Integer.parseInt(request.getParameter("quantity_changed"));
				String note = request.getParameter("note");

				// 2. Tìm Product theo SKU từ Database
				Product p = ProductDAO.getBySku(sku);

				// Nếu mã SKU không tồn tại trong hệ thống, báo lỗi và dừng lại
				if (p == null) {
					response.sendRedirect(request.getContextPath() + "/admin/inventory?error=not_found");
					return;
				}

				// 3. Tạo đối tượng Transaction và gán dữ liệu
				InventoryTransaction tx = new InventoryTransaction();
				tx.setProduct(p);
				tx.setQuantityChanged(quantityChanged);
				tx.setNote(note);
				tx.setTransactionType(transactionType); // Lưu loại giao dịch vào DB

				// Gán User đang đăng nhập
				HttpSession session = request.getSession();
				Integer adminId = (Integer) session.getAttribute("user_id"); // Lấy ID ra dưới dạng số nguyên

				if (adminId != null) {
					User adminUser = new User();   // Tạo đối tượng User mới
					adminUser.setId(adminId);      // Gán ID cho User đó
					tx.setUser(adminUser);         // Đưa User vào giao
				} else {
					// Nếu chưa đăng nhập thì đẩy về trang lỗi
					response.sendRedirect(request.getContextPath() + "/admin/inventory?error=unauthorized");
					return;
				}

				// 4. Phân nhánh lưu vào DAO dựa trên loại giao dịch
				boolean isSuccess = false;

				if ("Nhập kho".equals(transactionType)) {
					// Nếu là Nhập kho -> Gọi hàm cộng số lượng
					isSuccess = InventoryTransactionDAO.addImportTransaction(tx);

				} else if ("Xuất kho".equals(transactionType)) {
					isSuccess = InventoryTransactionDAO.addExportTransaction(tx);
				}

				// 5. Trả về kết quả cho JSP
				if (isSuccess) {
					response.sendRedirect(request.getContextPath() + "/admin/inventory?success=true");
				} else {
					// Thất bại (VD: Xuất kho nhưng tồn kho không đủ)
					response.sendRedirect(request.getContextPath() + "/admin/inventory?error=failed");
				}

			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/admin/inventory?error=invalidData");
			}
		}
	}
}
