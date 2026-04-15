<%-- 
    Document   : Thank
    Created on : Apr 12, 2026, 6:33:29 PM
    Author     : nguye
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đặt hàng thành công | PTIT Interior</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;600&family=Playfair+Display:wght@700&display=swap" rel="stylesheet">
        <style>
            .thank-you-container {
                max-width: 800px;
                margin: 100px auto;
                text-align: center;
                padding: 40px 20px;
            }
            .success-icon {
                width: 80px;
                height: 80px;
                background: #27ae60;
                color: white;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 40px;
                margin: 0 auto 30px;
            }
            h1 {
                font-family: 'Playfair Display', serif;
                font-size: 2.5rem;
                margin-bottom: 20px;
                color: #1a1a1a;
            }
            p {
                font-size: 1.1rem;
                color: #666;
                line-height: 1.6;
                margin-bottom: 40px;
            }
            .order-id {
                font-weight: 600;
                color: #000;
                background: #f9f9f9;
                padding: 5px 10px;
                border-radius: 4px;
            }
            .actions {
                display: flex;
                justify-content: center;
                gap: 20px;
            }
            .btn-primary {
                background: #000;
                color: #fff;
                padding: 15px 30px;
                text-decoration: none;
                text-transform: uppercase;
                font-size: 0.9rem;
                letter-spacing: 1px;
                transition: 0.3s;
            }
            .btn-secondary {
                border: 1px solid #000;
                color: #000;
                padding: 15px 30px;
                text-decoration: none;
                text-transform: uppercase;
                font-size: 0.9rem;
                letter-spacing: 1px;
                transition: 0.3s;
            }
            .btn-primary:hover {
    background: #333;
    }
            .btn-secondary:hover {
    background: #f5f5f5;
    }
        </style>
    </head>
    <body>

        <div class="thank-you-container">
            <div class="success-icon">✓</div>
            <h1>Cảm ơn bạn đã đặt hàng!</h1>
            <p>
                Đơn hàng của bạn đã được tiếp nhận thành công.<br>
                Chúng tôi sẽ sớm liên hệ với bạn qua số điện thoại để xác nhận thông tin giao hàng.
            </p>

            <div class="actions">
                <a href="${pageContext.request.contextPath}/ProductsController" class="btn-primary">Tiếp tục mua sắm</a>
                <a href="${pageContext.request.contextPath}/HomePage.jsp" class="btn-secondary">Về trang chủ</a>
            </div>
        </div>

    </body>
</html>