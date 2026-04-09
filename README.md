# PTIT-Interior

## Project Structure

### 📁web

| **File** | **Mô tả** |
|----------|-----------|
| HomePage.jsp | Jsp trang chủ |
| Products.jsp | Jsp trang sản phẩm |
| Login.jsp | Jsp trang đăng nhập |
| Register.jsp | Jsp trang đăng ký |

### 📁web/style

| **File** | **Mô tả** |
|----------|-----------|
| style.css | css hiện đang dùng cho HomePage.jsp và Products.jsp |
| LoginStyle.css | css dùng cho Login.jsp và Register.jsp nhưng chưa làm xong |

### 📁src/java/Controller

| **File** | **Mô tả** |
|----------|-----------|
| DBConnect.java | java class kết nối db bằng mysql |
| LoginController.java | servlet để login chuyển hướng qua homepage và register |
| LogoutController.java | servlet log out tài khoản, xóa session |
| ProductController.java | servlet lấy product từ db và thực hiện lọc theo yêu cầu, hiện dữ liệu ra Product.jsp |
| RegisterController.java | servlet lấy dữ liệu từ register.jsp lưu vào db để đăng kí |

### 📁src/java/Model

| **File** | **Mô tả** |
|----------|-----------|
| Category.java | model java class các loại sản phẩm để thực hiện lọc sản phẩm trong Product.jsp |
| Product.java | model java class các sản phẩm lấy từ db ra hiển thị trong Product.jsp |

### 📁src/java/dal

| **File** | **Mô tả** |
|----------|-----------|
| CategoryDAO.java | file lấy dữ liệu catagory từ db ra |
| ProductDAO.java | file lấy dữ liệu từ product từ db ra |
