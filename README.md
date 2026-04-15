# PTIT-Interior

## Project Structure

### 📁web

| **File** | **Mô tả** |
|----------|-----------|
| HomePage.jsp | Jsp trang chủ |
| Products.jsp | Jsp trang sản phẩm |
| ProductDetail.jsp | Jsp trang chi tiết sản phẩm |
| Login.jsp | Jsp trang đăng nhập |
| Register.jsp | Jsp trang đăng ký |
| Description.jsp | Jsp trang mô tả brand |
| Account.jsp | Jsp trang quản lý tài khoản của khách hàng|
| Cart.jsp | Jsp trang giỏ hàng |
| Thank.jsp | Jsp trang cảm ơn sau khi đặt hàng |
| Contact.jsp | Jsp trang liên hệ |
| OrderDetail.jsp | Jsp trang xem chi tiết đơn hàng đã đặt |
| OrderHistory.jsp | Jsp trang xem lịch sử đặt hàng |


### 📁web/admin

| **File** | **Mô tả** |
|----------|-----------|
| admin-sidebar.jsp | Jsp trang thanh bên chứa các nút điều hướng |
| admin-dashboard.jsp | Jsp trang thống kê chi tiết |
| admin-products.jsp | Jsp trang danh sách sản phẩm |
| edit-product.jsp | Jsp trang CRUD sản phẩm |
| admin-orders.jsp | Jsp trang danh sách hóa đơn |
| show-orders.jsp | Jsp trang hiển thị hóa đơn |
| admin-users.jsp | Jsp trang danh sách và các tính năng CRUD với tài khoản người dùng |
| admin-inventory.jsp | Jsp trang danh sách các giao dịch xuất/ nhập hàng |

### 📁web/style

| **File** | **Mô tả** |
|----------|-----------|
| style.css | css hiện đang dùng chung cho các trang có topbar |
| LoginStyle.css | css dùng cho Login.jsp và Register.jsp |
| AccountStyle.css | css dùng cho trang Account.jsp |
| CartStyle.css | css dùng cho Cart.jsp|
| admin_layout.css | css dùng cho admin-sidebar.jsp |
| dashboard.css | css dùng cho admin-dashboard.jsp |
| inventory.css | css dùng cho admin-inventory.jsp |
| orders.css | css dùng cho admin-orders.jsp |
| products.css | css dùng cho admin-products.jsp |
| user.css | css dùng cho admin-users.jsp |

### 📁src/java/Controller

| **File** | **Mô tả** |
|----------|-----------|
| DBConnect.java | java class kết nối db bằng mysql |
| LoginController.java | servlet để login chuyển hướng qua homepage, admin và register |
| LogoutController.java | servlet log out tài khoản, xóa session |
| ProductController.java | servlet lấy product từ db và thực hiện lọc theo yêu cầu, hiện dữ liệu ra Product.jsp |
| RegisterController.java | servlet lấy dữ liệu từ register.jsp lưu vào db để đăng kí |
| CartController.java | servlet xử lý việc lấy dữ liệu cho Cart.jsp hiện thị và xử lý việc thay đổi số lượng sản phẩm trong giỏ hàng|
| AddToCartController.java | servlet xử lý việc thêm sản phẩm vào giỏ hàng|
| AccountController.java | servlet xử lý trang quản lý tài khoản|
| OrderController.java | servlet xử lý tạo đơn hàng|
| OrderDetailController.java | servlet xử lý xem chi tiết đơn hàng đã đặt|
| OrderHistoryController.java | servlet xử lý xem lịch sử đặt hàng|
| AdminAddCategoryServlet.java | servlet xử lý thêm danh mục |
| AdminAddProductServlet.java | servlet xử lý thêm sản phẩm |
| AdminDashboardServlet.java | servlet xử lý thông tin thống kê ở trang chủ |
| AdminEditProductServlet.java | servlet xử lý sửa thông tin sản phẩm |
| AdminInventoryServlet.java | servlet xử lý thông tin các giao dịch xuất/nhập hàng |
| AdminOrderDetailServlet.java | servlet xử lý hiển thị thông tin đơn hàng |
| AdminOrdersServlet.java | servlet xử lý hiển thị danh sách hóa đơn |
| AdminProductServlet.java | servlet xử lý hiển thị danh sách sản phẩm |
| AdminUpdateOrderServlet.java | servlet xử lý cập nhật trạng thái đơn |
| AdminUserActionServlet.java | servlet xử lý thêm sửa xóa tài khoản người dùng |
| AdminUsersServlet.java | servlet xử lý hiển thị danh sách tài khoản người dùng |

### 📁src/java/Model

| **File** | **Mô tả** |
|----------|-----------|
| Category.java | model java class các loại sản phẩm để thực hiện lọc sản phẩm trong Product.jsp |
| Product.java | model java class các sản phẩm lấy từ db ra hiển thị trong Product.jsp |
| Cart.java | model java class thể hiện các thuộc tính và phương thức của giỏ hàng|
| CartItem.java | model java class thể hiện 1 dòng trong giỏ hàng |
| Order.java | model java class thể hiện các thuộc tính và phương thức của đơn hàng|
| OrderItem.java | model java class thể hiện 1 dòng trong đơn hàng |
| User.java | model java class thể hiện thông tin của 1 người dùng|
| InventoryTransaction.java | model java class thể hiện thông tin của 1 giao dịch nhập/xuất hàng và xuất bán|

### 📁src/java/dal

| **File** | **Mô tả** |
|----------|-----------|
| CategoryDAO.java | file lấy dữ liệu catagory từ db ra |
| ProductDAO.java | file lấy dữ liệu từ product từ db ra |
| CartDAO.java | kết nối với db để thực hiện crud với bảng cart |
| OrderDAO.java | kết nối với db để thực hiện crud với bảng order |
| UserDAO.java | kết nối với db để thực hiện crud với bảng user  |
| DashboardDAO.java | kết nối với db để thực hiện các lệnh thống kê  |
| InventoryTransactionDAO.java | kết nối với db để thực hiện thêm các giao dịch với bảng inventory_transactions  |
| OrderItemDAO.java | kết nối với db để thực hiện tìm kiếm với bảng order_items |


