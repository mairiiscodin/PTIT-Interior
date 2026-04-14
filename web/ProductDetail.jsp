<%-- 
    Document   : ProductDetail
    Created on : Apr 12, 2026, 3:02:53 PM
    Author     : Laptop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PTIT Interior - Không Gian Sống Tinh Tế</title>
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
                        <li><a href="${pageContext.request.contextPath}/cart">Giỏ hàng</a></li>
                        <li><a href="${pageContext.request.contextPath}/Description.jsp">Mô tả</a></li>
                        <li><a href="${pageContext.request.contextPath}/Contact.jsp">Liên hệ</a></li>
                    </ul>
                </nav>
            </div>
        </header>
        <main class="product-detail-container">
            <div class="detail-wrapper">
                <div class="detail-left">
                    <img src="${pageContext.request.contextPath}/image/${detail.urlImage}" alt="${detail.name}">
                </div>

                <div class="detail-right">
                    <nav class="breadcrumb">
                        <a href="ProductsController">Sản phẩm</a> / ${detail.name}
                    </nav>
                    <h1>${detail.name}</h1>
                    <p class="price">${detail.price} VNĐ</p>
                    <div class="description">
                        <p>${detail.description}</p>
                    </div>
                    
                        <form action="AddToCartController" method="post">
                            <input type="hidden" name="productId" value="${detail.id}">
                        <div class="quantity-select">
                            <label>Số lượng:</label>
                            <input type="number" name="quantity" value="1" min="1" max="${detail.stock}">
                        </div>
                        <button type="submit" class="btn-add-cart">Thêm vào giỏ hàng</button>
                    </form>
                    
                    <p class="stock-status">Kho: ${detail.stock} sản phẩm có sẵn</p>
                </div>
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
