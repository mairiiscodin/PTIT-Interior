<%-- 
    Document   : login
    Created on : Mar 24, 2026, 9:57:50 AM
    Author     : Laptop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập | PTIT Interior</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;600&family=Playfair+Display:wght@400;700&display=swap" rel="stylesheet">
        
        <link rel ="stylesheet" href ="${pageContext.request.contextPath}/style/LoginStyle.css">
    </head>
    <body>
        <div class="login-container">
            <div class="left-side">
                <h2>PTIT Interior</h2>
                <p>Nâng tầm không gian sống</p>
            </div>

            <div class="right-side">
                <form action="login" method="post">
                    <div class="form-header">
                        <h3>Đăng nhập</h3>
                        <p style="font-size: 0.85rem; color: #888;">Chào mừng bạn quay trở lại</p>
                    </div>

                    <input type="text" name="full_name" placeholder="Tên đăng nhập" required>
                    <input type="password" name="password" placeholder="Mật khẩu" required>

                    <%
                        String error = (String) request.getAttribute("error");
                        if (error != null) {
                    %>
                        <span class="error-msg"><%= error %></span>
                    <%
                        }
                    %>

                    <button type="submit">Đăng nhập</button>

                    <div class="register-link">
                        Bạn chưa có tài khoản? 
                        <a href="${pageContext.request.contextPath}/Register.jsp">Đăng ký ngay</a>
                    </div>
                    
                    <div style="margin-top: 30px; text-align: center;">
                        <a href="${pageContext.request.contextPath}/HomePage.jsp" 
                           style="font-size: 0.75rem; color: #aaa; text-transform: uppercase; letter-spacing: 1px;">
                           ← Quay lại trang chủ
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>