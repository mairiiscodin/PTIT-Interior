<%-- 
    Document   : Description
    Created on : Apr 12, 2026, 12:46:46 PM
    Author     : Laptop
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <main class="description-content">
            <section id="about-us" class="desc-section">
                <div class="section-header-mini">
                    <span>Câu chuyện của chúng tôi</span>
                </div>
                <h2>Giới thiệu PTIT Interior</h2>
                <div class="desc-text">
                    <p>Được thành lập với sứ mệnh mang nghệ thuật vào không gian sống, **PTIT Interior** không chỉ cung cấp nội thất, chúng tôi kiến tạo phong cách sống. Mỗi sản phẩm tại đây là sự kết tinh giữa tư duy thiết kế hiện đại và bàn tay tài hoa của những nghệ nhân tâm huyết.</p>
                    <p>Chúng tôi tin rằng ngôi nhà là nơi phản chiếu tâm hồn của gia chủ. Vì vậy, phong cách của PTIT Interior luôn hướng tới sự tối giản, sang trọng và bền vững với thời gian.</p>
                </div>
            </section>

            <hr class="separator">

            <section id="corporation" class="desc-section">
                <div class="section-header-mini">
                    <span>Tầm vóc và giá trị</span>
                </div>
                <h2>Về Tổng Công ty</h2>
                <div class="desc-text">
                    <p>PTIT Interior tự hào là thành viên chủ chốt của hệ sinh thái **PTIT Group** – đơn vị dẫn đầu trong lĩnh vực thiết kế và sản xuất nội thất cao cấp tại Việt Nam. Với hệ thống xưởng sản xuất hiện đại quy mô hơn 5.000m² và đội ngũ hơn 200 chuyên gia, chúng tôi đảm bảo quy trình khép kín từ khâu ý tưởng đến khi lắp đặt hoàn thiện.</p>
                    <p>Với hơn 10 năm kinh nghiệm, tổng công ty đã thực hiện hàng ngàn dự án biệt thự, căn hộ cao cấp và văn phòng hạng A trên khắp cả nước.</p>
                </div>
            </section>

            <hr class="separator">

            <section id="careers" class="desc-section">
                <div class="section-header-mini">
                    <span>Gia nhập đội ngũ</span>
                </div>
                <h2>Tuyển dụng</h2>
                <div class="desc-text">
                    <p>Tại PTIT Interior, con người là tài sản quý giá nhất. Chúng tôi luôn tìm kiếm những tâm hồn đồng điệu, yêu cái đẹp và có khát khao chinh phục những tiêu chuẩn mới trong ngành nội thất.</p>
                    <ul class="career-list">
                        <li><strong>Kiến trúc sư nội thất:</strong> Tối thiểu 2 năm kinh nghiệm thực chiến.</li>
                        <li><strong>Chuyên viên tư vấn Concept:</strong> Am hiểu về xu hướng nội thất quốc tế.</li>
                        <li><strong>Thực tập sinh thiết kế:</strong> Dành cho sinh viên năm cuối muốn trải nghiệm môi trường chuyên nghiệp.</li>
                    </ul>
                    <p>Mọi hồ sơ ứng tuyển vui lòng gửi về: <em>tuyendung@ptit-interior.vn</em></p>
                </div>
            </section>

            <hr class="separator">

            <section id="design-contact" class="desc-section">
                <div class="section-header-mini">
                    <span>Dự án riêng biệt</span>
                </div>
                <h2>Liên hệ thiết kế</h2>
                <div class="desc-text">
                    <p>Bạn đang tìm kiếm một giải pháp thiết kế cá nhân hóa cho không gian riêng của mình? Đội ngũ kiến trúc sư của chúng tôi luôn sẵn sàng lắng nghe và biến ý tưởng của bạn thành hiện thực với quy trình tư vấn chuyên sâu.</p>
                    <div class="cta-link">
                        <a href="${pageContext.request.contextPath}/Contact.jsp" class="view-detail-link">
                            Gửi yêu cầu tư vấn ngay &rarr;
                        </a>
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
