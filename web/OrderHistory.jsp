<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lịch sử mua hàng</title>
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
                <h3>Đơn hàng của tôi</h3>

                <c:choose>
                    <c:when test="${not empty orders}">
                        <table class="order-table">
                            <thead>
                                <tr>
                                    <th>Mã ĐH</th>
                                    <th>Ngày đặt</th>
                                    <th>Tổng tiền</th>
                                    <th>Phương thức</th>
                                    <th>Trạng thái</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="o" items="${orders}">
                                    <tr>
                                        <td style="font-weight: 600;"><a href="OrderDetailController?id=${o.id}" style="color: var(--accent-color); text-decoration: underline;">
                                                #${o.id}
                                            </a></td>
                                        <td><fmt:formatDate value="${o.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                        <td style="color: var(--accent-color); font-weight: 500;">
                                            <fmt:formatNumber value="${o.totalAmount}" type="currency" currencyCode="VND"/>
                                        </td>
                                        <td>${o.paymentMethod}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${o.status == 'Chờ xác nhận'}">
                                                    <span class="badge status-choxacnhan">Chờ xác nhận</span>
                                                </c:when>
                                                <c:when test="${o.status == 'Đang giao'}">
                                                    <span class="badge status-danggiao">Đang giao</span>
                                                </c:when>
                                                <c:when test="${o.status == 'Hoàn thành'}">
                                                    <span class="badge status-hoanthanh">Hoàn thành</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge" style="background:#eee; color:#333;">${o.status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div style="text-align: center; padding: 40px 0;">
                            <p style="color: #888; margin-bottom: 20px;">Bạn chưa có đơn hàng nào.</p>
                            <a href="${pageContext.request.contextPath}/ProductsController"
                               style="background: var(--primary-color); color: white; padding: 12px 30px; text-decoration: none; text-transform: uppercase; font-size: 0.8rem; font-weight: 600;">
                                Mua sắm ngay
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
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
