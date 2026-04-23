<%-- 
    Document   : HomePage
    Created on : Mar 24, 2026, 12:12:11 AM
    Author     : Laptop
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>PTIT Interior - Không Gian Sống Tinh Tế</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500&family=Playfair+Display:ital,wght@0,400;0,700;1,400&display=swap" rel="stylesheet">
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
    </head>
    <body>
        
        <header>
            <div class="top-bar">
                <c:choose>
                    <c:when test="${empty sessionScope.full_name}">
                        <a href="${pageContext.request.contextPath}/Login.jsp">Đăng nhập</a>
                    </c:when>
                    <c:otherwise>
                        <span>Xin chào, <strong><a href="${pageContext.request.contextPath}/account">${sessionScope.full_name}</a></strong></span>
                        <span>|</span>
                        <a href="${pageContext.request.contextPath}/LogoutController">Đăng xuất</a>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="nav-container">
                <a href="${pageContext.request.contextPath}/HomePageController" class="logo">PTIT Interior</a>
                <nav>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/HomePageController">Trang chủ</a></li>
                        <li><a href="${pageContext.request.contextPath}/ProductsController">Sản phẩm</a></li>
                        <li><a href="${pageContext.request.contextPath}/cart">Giỏ hàng</a></li>
                        <li><a href="${pageContext.request.contextPath}/Description.jsp">Mô tả</a></li>
                        <li><a href="${pageContext.request.contextPath}/Contact.jsp">Liên hệ</a></li>
                    </ul>
                </nav>
            </div>
        </header>

        <div class="banner">
            <div class="banner-content">
                <h1>Nét Đẹp Tối Giản</h1>
                <p>Khơi nguồn cảm hứng cho không gian sống mang đậm dấu ấn cá nhân</p>
                <a href="${pageContext.request.contextPath}/ProductsController" class="btn">Khám phá bộ sưu tập</a>
            </div>
        </div>

        <section class="featured-products">
            <h2 class="section-title">Sản Phẩm Nổi Bật</h2>
            <div class="products-grid">
                <c:choose>
                    <c:when test="${not empty topSellers}">
                        <c:forEach var="p" items="${topSellers}">
                            <div class="product-card">
                                <a href="${pageContext.request.contextPath}/ProductsController?action=detail&id=${p.id}">
                                    <img src="${pageContext.request.contextPath}/image/${p.urlImage}" alt="${p.name}">
                                </a>
                                <div style="text-align: center; margin-top: 15px;">
                                    <h3 style="font-size: 1.1rem; color: #333;">${p.name}</h3>
                                    <p style="color: #666; font-weight: 500;">
                                        <fmt:formatNumber value="${p.price}" type="number" maxFractionDigits="0"/> VNĐ
                                    </p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p style="text-align: center; width: 100%;">Chưa có sản phẩm nổi bật nào được ghi nhận.</p>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/ProductsController" class="btn-outline">Xem tất cả sản phẩm</a>
            </div>
        </section>

        <section class="info-section">
            <div class="info-block">
                <h2>Câu Chuyện Thương Hiệu</h2>
                <p>PTIT Interior ra đời vào năm 1999, là một trong những thương hiệu tiên phong trong ngành nội thất. Mang trong mình nguồn cảm hứng từ văn hóa đương đại và gu thẩm mỹ tinh tế, chúng tôi đề cao sự tối giản nhưng không kém phần sang trọng. Qua nhiều năm phát triển, sự hoàn mỹ trong từng chi tiết luôn là tôn chỉ hoạt động của chúng tôi.</p>
                <a href="${pageContext.request.contextPath}/Description.jsp" class="btn-outline">Tìm hiểu thêm</a>
            </div>
            
            <div class="info-block">
                <h2>Dịch Vụ Thiết Kế</h2>
                <p>Mỗi ngôi nhà là một tác phẩm nghệ thuật. Với kinh nghiệm dày dặn cùng đội ngũ kiến trúc sư tận tâm, chúng tôi mang đến những giải pháp thiết kế toàn diện. Từ việc lắng nghe nhu cầu đến khâu hoàn thiện thi công, mọi quy trình đều được chăm chút để kiến tạo nên không gian sống lý tưởng dành riêng cho bạn.</p>
                <a href="${pageContext.request.contextPath}/Contact.jsp" class="btn-outline">Liên hệ tư vấn</a>
            </div>
        </section>

        <footer>
            <a href="${pageContext.request.contextPath}/HomePageController" class="logo">PTIT Interior</a>
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
