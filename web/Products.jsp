<%-- 
    Document   : Products
    Created on : Mar 26, 2026, 4:07:30 PM
    Author     : Laptop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sản Phẩm - PTIT Interior</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600&family=Playfair+Display:wght@400;700&display=swap" rel="stylesheet">
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    </head>
    <body>
        
        <header>
            <div class="top-bar">
                <%
                    String userName = (String) session.getAttribute("full_name");
                    if(userName == null){
                %>
                    <a href="${pageContext.request.contextPath}/Login.jsp">Đăng nhập</a>
                <%
                    } else {
                %>
                    <span>Xin chào, <strong><a href="${pageContext.request.contextPath}/account"><%= userName %></a></strong></span>
                    <span>|</span>
                    <a href="${pageContext.request.contextPath}/LogoutController">Đăng xuất</a>
                <%
                    }
                %>
            </div>

            <div class="nav-container">
                <a href="${pageContext.request.contextPath}/HomePage.jsp" class="logo">PTIT Interior</a>
                <nav>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/HomePage.jsp">Trang chủ</a></li>
                        <li><a href="${pageContext.request.contextPath}/ProductsController">Sản phẩm</a></li>
                        <li><a href="${pageContext.request.contextPath}/Cart.jsp">Giỏ hàng</a></li>
                        <li><a href="${pageContext.request.contextPath}/Description.jsp">Mô tả</a></li>
                        <li><a href="${pageContext.request.contextPath}/Contact.jsp">Liên hệ</a></li>
                    </ul>
                </nav>
            </div>
        </header>

        <main>
            <div class="page-header">
                <h1>Bộ Sưu Tập</h1>
            </div>

            <div class="filter-container">
                <form action="ProductsController" method="get" class="search-box">
                    <input type="text" name="keyword" placeholder="Tìm tên sản phẩm...">
                    <select name="sort">
                        <option value="asc">Giá thấp đến cao</option>
                        <option value="desc">Giá cao đến thấp</option>
                    </select>
                    <button type="submit">Tìm</button>
                </form>

                <div class="productCatalog">
                    <a href="ProductsController" class="${empty param.category ? 'active' : ''}">Tất cả sản phẩm</a>
                    <c:forEach var="c" items="${categories}">
                        <a href="ProductsController?category=${c.id}" 
                           class="${param.category == c.id ? 'active' : ''}">
                           ${c.name}
                        </a>
                    </c:forEach>
                </div>
            </div>

            <div class="product-container">
                <c:forEach var="p" items="${list}">
                    <div class="product-card">
                        <div class="product-image-wrapper">
                            <img src="${pageContext.request.contextPath}/image/${p.urlImage}" alt="${p.name}">
                        </div>

                        <div class="product-info">
                            <h3>${p.name}</h3>
                            <p>${p.price} VNĐ</p>
                            <a href="${pageContext.request.contextPath}/ProductDetailController?id=${p.id}" class="view-detail">
                                Xem chi tiết
                            </a>
                        </div>
                    </div>
                </c:forEach>

                <c:if test="${empty list}">
                    <div style="grid-column: 1/-1; text-align: center; padding: 100px 0;">
                        <p>Không tìm thấy sản phẩm phù hợp.</p>
                    </div>
                </c:if>
            </div>
        </main>

        <footer>
            <a href="${pageContext.request.contextPath}/HomePage.jsp" class="logo">PTIT Interior</a>
            <ul>
                <li><a href="${pageContext.request.contextPath}/Description.jsp">Giới thiệu</a></li>
                <li><a href="${pageContext.request.contextPath}/Description.jsp">Tổng công ty</a></li>
                <li><a href="${pageContext.request.contextPath}/Description.jsp">Tuyển dụng</a></li>
                <li><a href="${pageContext.request.contextPath}/Contact.jsp">Liên hệ thiết kế</a></li>
            </ul>
            <div class="copyright">
                &copy; 2026 PTIT Interior. Bảo lưu mọi quyền.
            </div>
        </footer>

    </body>
</html>