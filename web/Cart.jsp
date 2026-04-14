<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Giỏ Hàng | PTITInterior</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/CartStyle.css">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@300;400;500;700&family=Playfair+Display:wght@400;700&display=swap" rel="stylesheet">


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
                <span>Xin chào, <strong><a href="${pageContext.request.contextPath}/account"><%= userName%></a></strong></span>
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

        <main class="cart-container">
            <h1 style="font-family: var(--font-heading); margin-bottom: 40px; font-weight: 400;">Giỏ hàng của bạn</h1>

            <c:choose>
                <c:when test="${not empty items}">
                    <div class="cart-lay out">
                        <div class="cart-main">
                            <table class="cart-table">
                                <thead>
                                    <tr>
                                        <th>Sản phẩm</th>
                                        <th>Số lượng</th>
                                        <th>Thành tiền</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${items}">
                                        <tr class="cart-item" id="item-row-${item.product.id}">
                                            <td>
                                                <div class="product-info">
                                                    <img src="${pageContext.request.contextPath}/image/${item.product.urlImage}" alt="${item.product.name}">
                                                    <div>
                                                        <span class="product-name">${item.product.name}</span>
                                                        <span style="font-size: 0.8rem; color: #666;">
                                                            <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="₫"/>
                                                        </span>
                                                            <br>
                                                            <span class="product-stock">Số lượng sản phẩm còn: ${item.product.stock}</span><br>
                                                            <a href="javascript:void(0)" onclick="updateQuantityAjax(${item.product.id}, 0)" class="remove-link">Gỡ bỏ</a>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>
                                                <input type="number" value="${item.quantity}" min="1" max="${item.product.stock}"
                                                       class="qty-input"
                                                       onchange="updateQuantityAjax(${item.product.id}, this.value, ${item.product.stock})">
                                            </td>
                                            <td>
                                                <span class="item-subtotal" id="subtotal-${item.product.id}" style="font-weight: 500;">
                                                    <fmt:formatNumber value="${item.product.price * item.quantity}" type="currency" currencySymbol="₫"/>
                                                </span>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <div style="margin-top: 30px;">
                                <a href="${pageContext.request.contextPath}/ProductsController" style="font-size: 0.8rem; text-decoration: underline; color: #666;">← Tiếp tục mua sắm</a>
                            </div>
                        </div>

                        <aside class="cart-summary">
                            <h3 class="summary-title">Tạm tính</h3>
                            <div class="summary-row">
                                <span>Tổng tiền hàng</span>
                                <span id="summary-subtotal"><fmt:formatNumber value="${total}" type="currency" currencySymbol="₫"/></span>
                            </div>
                            <div class="summary-row">
                                <span>Phí vận chuyển</span>
                                <span>Miễn phí</span>
                            </div>
                            <div class="summary-row summary-total">
                                <span>Tổng cộng</span>
                                <span id="summary-total"><fmt:formatNumber value="${total}" type="currency" currencySymbol="₫"/></span>
                            </div>
                            <button class="checkout-btn" onclick="openOrderModal()">Tiến hành thanh toán</button>

                            <div id="orderModal" class="modal">
                                <div class="modal-content">
                                    <span class="close" onclick="closeOrderModal()">&times;</span>
                                    <h2>Thông tin nhận hàng</h2>
                                    <form id="orderForm" action="order" method="POST">
                                        <div class="form-group">
                                            <label>Tên người nhận:</label>
                                            <input type="text" name="receiver_name" value="${sessionScope.full_name}" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Địa chỉ giao hàng:</label>
                                            <textarea name="address" required></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label>Phương thức thanh toán:</label>
                                            <select name="payment_method" id="paymentMethod" onchange="toggleBankInfo()" required
                                                    style="width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px;">
                                                <option value="COD">Thanh toán khi nhận hàng (COD)</option>
                                                <option value="BANK_TRANSFER">Chuyển khoản ngân hàng</option>
                                            </select>
                                        </div>

                                            <div id="bankInfo" style="display: none; background: #f9f9f9; padding: 15px; border-radius: 4px; margin-top: 10px; border: 1px dashed #ccc;">
                                                <p style="font-size: 0.85rem; margin: 0;">
                                                    <strong>Thông tin chuyển khoản:</strong><br>
                                                    Ngân hàng: MB Bank<br>
                                                    STK: 0123456789<br>
                                                    Chủ TK: NGUYEN VAN A<br>
                                                    Nội dung: [Tên_SốĐT] thanh toan don hang
                                                </p>
                                            </div>
                                            <input type="hidden" name="total_amount" value="${total}">
                                            <button type="submit" class="confirm-btn">Xác nhận đặt hàng</button>
                                    </form>
                                </div>
                            </div>

                                        <style>
                                /* CSS cho Modal sang trọng */
                                .modal {
                                    display: none;
                                    position: fixed;
                                    z-index: 1000;
                                    left: 0;
                                    top: 0;
                                    width: 100%;
                                    height: 100%;
                                    background-color: rgba(0,0,0,0.5);
                                }
                                .modal-content {
                                    background-color: #fff;
                                    margin: 10% auto;
                                    padding: 30px;
                                    border-radius: 8px;
                                    width: 450px;
                                    position: relative;
                                    font-family: 'Montserrat', sans-serif;
                                }
                                .close {
                                    position: absolute;
                                    right: 20px;
                                    top: 10px;
                                    font-size: 28px;
                                    cursor: pointer;
                                }
                                .form-group {
                                    margin-bottom: 15px;
                                }
                                .form-group label {
                                    display: block;
                                    margin-bottom: 5px;
                                    font-weight: 600;
                                }
                                .form-group input, .form-group textarea {
                                    width: 100%;
                                    padding: 10px;
                                    border: 1px solid #ddd;
                                    border-radius: 4px;
                                }
                                .confirm-btn {
                                    width: 100%;
                                    background: #000;
                                    color: #fff;
                                    padding: 12px;
                                    border: none;
                                    cursor: pointer;
                                    text-transform: uppercase;
                                    margin-top: 10px;
                                }
                            </style>

                            <script>
                                function openOrderModal() {
                                    document.getElementById('orderModal').style.display = 'block';
                                }
                                function closeOrderModal() {
                                    document.getElementById('orderModal').style.display = 'none';
                                }
                                // Đóng modal khi click ra ngoài
                                window.onclick = function (event) {
                                    if (event.target == document.getElementById('orderModal'))
                                        closeOrderModal();
                                }
                            </script>
                            <script>
                                function toggleBankInfo() {
                                    const method = document.getElementById('paymentMethod').value;
                                    const bankInfo = document.getElementById('bankInfo');
                                    bankInfo.style.display = (method === 'BANK_TRANSFER') ? 'block' : 'none';
                                }
                            </script>
                        </aside>
                    </div>
                </c:when>

                <c:otherwise>
                    <div style="text-align: center; padding: 100px 0;">
                        <p style="margin-bottom: 25px; color: #888; letter-spacing: 1px;">Giỏ hàng của bạn đang trống.</p>
                        <a href="${pageContext.request.contextPath}/ProductsController" class="btn-outline" style="padding: 15px 40px; display: inline-block;">Khám phá sản phẩm</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>

        <footer style="margin-top: 100px; padding: 60px 5%; border-top: 1px solid var(--border-color); text-align: center;">
            <div class="logo" style="margin-bottom: 20px;">PTITInterior</div>
            <p class="copyright">&copy; 2026 PTIT Interior Design. All rights reserved.</p>
        </footer>

        <script>
            const formatter = new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND',
                minimumFractionDigits: 0
            });

            function updateQuantityAjax(productId, newQty, maxStock) {
                // Nếu người dùng nhấn "Gỡ bỏ", confirm trước khi xóa
                if (newQty == 0) {
                    console.log("Đang gửi yêu cầu xóa ID: " + productId + " với số lượng: " + newQty);
                    if (!confirm("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?"))
                        return;
                }
                if (newQty > maxStock) {
                    alert("Rất tiếc, PTIT Interior chỉ còn " + maxStock + " sản phẩm này trong kho.");
                    // Đưa ô nhập liệu về số lượng tối đa
                    document.querySelector("#item-row-" + productId + " .qty-input").value = maxStock;
                    newQty = maxStock; // Gán lại để gửi lên server đúng số max
                }
                const params = new URLSearchParams();
                params.append('action', 'updateAjax');
                params.append('id', productId);
                params.append('qty', newQty);

                fetch('cart', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: params
                })
                        .then(response => {
                            if (response.status === 401) {
                                alert("Vui lòng đăng nhập để thực hiện thao tác này.");
                                window.location.href = "Login.jsp";
                                return;
                            }
                            return response.json();
                        })
                        .then(data => {
                            if (newQty == 0) {
                                // Xóa dòng sản phẩm khỏi giao diện
                                const row = document.getElementById("item-row-" + productId);
                                if (row)
                                    row.remove();

                                // Nếu không còn sản phẩm nào, tải lại trang để hiện trạng thái "Trống"
                                if (data.cartTotal == 0) {
                                    location.reload();
                                }
                            } else {
                                // Cập nhật giá trị thành tiền của item
                                const subtotalEl = document.getElementById("subtotal-" + productId);
                                if (subtotalEl)
                                    subtotalEl.innerText = formatter.format(data.subTotal);
                            }

                            // Luôn cập nhật tổng tiền giỏ hàng
                            document.getElementById('summary-subtotal').innerText = formatter.format(data.cartTotal);
                            document.getElementById('summary-total').innerText = formatter.format(data.cartTotal);
                        })
                        .catch(error => console.error('Error:', error));
            }
        </script>
    </body>
</html>