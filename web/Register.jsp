<%-- 
    Document   : Register
    Created on : Apr 7, 2026, 7:16:27 PM
    Author     : Laptop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng ký thành viên | PTIT Interior</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;600&family=Playfair+Display:wght@400;700&display=swap" rel="stylesheet">
        
        <link rel ="stylesheet" href ="${pageContext.request.contextPath}/style/LoginStyle.css">
    </head>
    <body>
        <div class="login-container"> <div class="left-side" style="background-image: linear-gradient(rgba(0,0,0,0.3), rgba(0,0,0,0.3)), url('https://images.unsplash.com/photo-1586023492125-27b2c045efd7?ixlib=rb-4.0.3&auto=format&fit=crop&w=1200&q=80');">
                <h2>Tham gia PTIT Interior</h2>
                <p>Khám phá đặc quyền dành riêng cho thành viên</p>
            </div>

            <div class="right-side">
                <form action="register" method="post" class="register-form">
                    <div class="form-header register-header">
                        <h3>Đăng ký</h3>
                        <p style="font-size: 0.85rem; color: #888;">Tạo tài khoản để trải nghiệm dịch vụ tốt nhất</p>
                    </div>

                    <input type="text" name="full_name" placeholder="Họ và tên" required>
                    
                    <div class="form-row">
                        <input type="email" name="email" placeholder="Email" required>
                        <input type="text" name="phone" placeholder="Số điện thoại">
                    </div>

                    <input type="password" name="password" placeholder="Mật khẩu" required>
                    <input type="text" name="address" placeholder="Địa chỉ">

                    <button type="submit">Đăng ký tài khoản</button>

                    <div class="register-link">
                        Đã có tài khoản? 
                        <a href="${pageContext.request.contextPath}/Login.jsp">Đăng nhập tại đây</a>
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