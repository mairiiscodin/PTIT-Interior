<%-- 
    Document   : Contact
    Created on : Apr 12, 2026, 2:06:29 PM
    Author     : Laptop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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
        <main class="contact-page">
            <section class="architects-section">
                <div class="section-title-container">
                    <span>The Minds Behind</span>
                    <h2>Đội ngũ Kiến trúc sư</h2>
                </div>

                <div class="architects-grid">
                    <div class="architect-card">
                        <div class="arch-info">
                            <h3>KTS. Hoàng Minh Đăng</h3>
                            <p class="role">Giám đốc thiết kế (Design Director)</p>
                            <div class="skills">
                                <div class="skill-item"><strong>Mạnh:</strong> Tư duy không gian mở, xử lý ánh sáng tự nhiên và phong cách Zen hiện đại.</div>
                                <div class="skill-item"><strong>Yếu:</strong> Khá khắt khe trong việc lựa chọn vật liệu, đôi khi kéo dài thời gian duyệt mẫu để đạt sự hoàn hảo nhất.</div>
                            </div>
                        </div>
                    </div>

                    <div class="architect-card">
                        <div class="arch-info">
                            <h3>KTS. Lê Khánh An</h3>
                            <p class="role">Kiến trúc sư trưởng (Senior Architect)</p>
                            <div class="skills">
                                <div class="skill-item"><strong>Mạnh:</strong> Am hiểu sâu sắc về gỗ tự nhiên và các giải pháp nội thất thông minh cho căn hộ cao cấp.</div>
                                <div class="skill-item"><strong>Yếu:</strong> Chỉ tập trung vào phân khúc tối giản, ít nhận các dự án theo phong cách cổ điển rườm rà.</div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section class="contact-info-section">
                <div class="contact-box">
                    <div class="contact-details">
                        <h2>Liên hệ với chúng tôi</h2>
                        <p>Hãy để chúng tôi cùng bạn vẽ nên câu chuyện cho ngôi nhà mơ ước.</p>

                        <div class="info-list">
                            <div class="info-item">
                                <strong>Địa chỉ:</strong> Km10, Đường Nguyễn Trãi, Hà Đông, Hà Nội (Học viện Công nghệ Bưu chính Viễn thông)
                            </div>
                            <div class="info-item">
                                <strong>Hotline:</strong> <a href="tel:0912345678">0912 345 678</a> (Tư vấn 24/7)
                            </div>
                            <div class="info-item">
                                <strong>Email:</strong> contact@ptit-interior.vn
                            </div>
                            <div class="info-item">
                                <strong>Giờ làm việc:</strong> Thứ 2 - Thứ 7 | 08:30 - 18:00
                            </div>
                        </div>
                    </div>
                </div>
            </section>
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
