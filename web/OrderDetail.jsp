<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;600&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/AccountStyle.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    </head>
    <body>
        <header>
            <div class="top-bar">
                <%
                    String userName = (String) session.getAttribute("full_name");
                    if (userName == null) {
                %>
                <a href="${pageContext.request.contextPath}/Login.jsp">Đăng nhập</a>
                <%
                } else {
                %>
                <span>Xin chào, <strong><%= userName%></strong></span>
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
                        <li><a href="${pageContext.request.contextPath}/cart">Giỏ hàng</a></li>
                        <li><a href="${pageContext.request.contextPath}/Description.jsp">Mô tả</a></li>
                        <li><a href="${pageContext.request.contextPath}/Contact.jsp">Liên hệ</a></li>
                    </ul>
                </nav>
            </div>
        </header>
        <div class="profile-container">
            <aside class="profile-sidebar">
                <h2>Hồ sơ</h2>
                <a href="account" class="menu-item">Thông tin cá nhân</a>
                <a href="history" class="menu-item active">Lịch sử đơn hàng</a>
                <a href="LogoutController" class="menu-item" style="color: #e74c3c;">Đăng xuất</a>
            </aside>

            <main class="profile-content">
                <h3>Chi tiết đơn hàng #${orderId}</h3>
                <table class="order-table">
                    <thead>
                        <tr>
                            <th>Sản phẩm</th>
                            <th>Đơn giá</th>
                            <th>Số lượng</th>
                            <th>Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${details}">
                            <tr>
                                <td>
                                    <div style="display: flex; align-items: center; gap: 15px;">
                                        <img src="image/${item.product.urlImage}" width="50">
                                        <span><a href="${pageContext.request.contextPath}/ProductDetailController?id=${item.product.id}">
                                                ${item.product.name}
                                            </a>
                                        </span>
                                    </div>
                                </td>
                                <td><fmt:formatNumber value="${item.price}" type="currency" currencyCode="VND"/></td>
                                <td>${item.quantity}</td>
                                <td style="font-weight: 600;">
                                    <fmt:formatNumber value="${item.price * item.quantity}" type="currency" currencySymbol="₫"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div style="margin-top: 30px;">
                    <a href="history" class="menu-item">← Quay lại lịch sử mua hàng</a>
                </div>
            </main>
        </div>
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
