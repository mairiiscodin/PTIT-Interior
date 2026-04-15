<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Tài khoản của tôi | PTIT Interior</title>
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
                            <a href="account" class="menu-item active">Thông tin cá nhân</a>
                            <a href="history" class="menu-item">Lịch sử đơn hàng</a>
                            <a href="LogoutController" class="menu-item" style="color: #e74c3c;">Đăng xuất</a>
                        </aside>

            <main class="profile-content">
                <h3>Thông tin chi tiết ${user.fullName}</h3>

                <c:if test="${not empty message}">
                    <div class="success-msg">${message}</div>
                </c:if>

                    <form action="account" method="POST">
                        <input type="hidden" name="id" value="${user.id}">

                    <div class="form-group">
                        <label>Họ và tên khách hàng</label>
                        <input type="text" name="full_name" value="${user.fullName}" required>
                    </div>

                        <div class="form-row">
                            <div class="form-group">
                                <label>Địa chỉ Email</label>
                                <input type="email" name="email" value="${user.email}" placeholder="example@gmail.com">
                            </div>
                            <div class="form-group">
                                <label>Số điện thoại liên hệ</label>
                                <input type="text" name="phone" value="${user.phone}" placeholder="09xx xxx xxx">
                            </div>
                        </div>

                            <div class="form-group">
                                <label>Địa chỉ giao hàng mặc định</label>
                                <textarea name="address" rows="3" placeholder="Số nhà, tên đường, phường/xã...">${user.address}</textarea>
                            </div>

                    <button type="submit" class="btn-update">Lưu thay đổi</button>
                </form>
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