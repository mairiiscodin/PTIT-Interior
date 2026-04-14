package Controller;

import dal.ProductDAO;
import dal.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductsController", urlPatterns = {"/ProductsController"})
public class ProductsController extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		ProductDAO dao = new ProductDAO();
		CategoryDAO cdao = new CategoryDAO();

		String keyword = request.getParameter("keyword");
		String sort = request.getParameter("sort");
		String category = request.getParameter("category");

		request.setAttribute("list", dao.filterProducts(keyword, sort, category));
		request.setAttribute("categories", cdao.getAllCategories());

		request.getRequestDispatcher("Products.jsp").forward(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}

}
