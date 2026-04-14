# PTIT-Interior

## Project Structure

### 📁web

| **File** | **Mô tả** |
|----------|-----------|
| HomePage.jsp | Jsp trang chủ |
| Products.jsp | Jsp trang sản phẩm |
| Login.jsp | Jsp trang đăng nhập |
| Register.jsp | Jsp trang đăng ký |
| Description.jsp | Jsp trang mô tả brand |
| Account.jsp | Jsp trang quản lý tài khoản của khách hàng|
| Cart.jsp | Jsp trang giỏ hàng |
| Thank.jsp | Jsp trang cảm ơn sau khi đặt hàng |
| Contact.jsp | Jsp trang liên hệ |

### 📁web/style

| **File** | **Mô tả** |
|----------|-----------|
| style.css | css hiện đang dùng chung cho các trang có topbar |
| LoginStyle.css | css dùng cho Login.jsp và Register.jsp |
| AccountStyle.css | css dùng cho trang Account.jsp |
| CartStyle.css | css dùng cho Cart.jsp|


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
| OrderController.java | servlet xử lý tạo đơn hàng|


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

