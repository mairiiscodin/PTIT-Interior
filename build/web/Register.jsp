<%-- 
    Document   : Register
    Created on : Apr 7, 2026, 7:16:27 PM
    Author     : Laptop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PTITInterior</title>
    </head>
    <body>
        <div class="register-container">
            <h2>Đăng ký tài khoản</h2>

            <form action="register" method="post">
                <input type="text" name="full_name" placeholder="Họ và tên" required>

                <input type="email" name="email" placeholder="Email" required>

                <input type="password" name="password" placeholder="Mật khẩu" required>

                <input type="text" name="phone" placeholder="Số điện thoại">

                <input type="text" name="address" placeholder="Địa chỉ">

                <button type="submit">Đăng ký</button>
            </form>

            <p>
                Đã có tài khoản?
                <a href="${pageContext.request.contextPath}/Login.jsp">Đăng nhập</a>
            </p>
        </div>

    </body>
</html>
