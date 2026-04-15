<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Sửa sản phẩm</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/admin_layout.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/products.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>
	<body>

		<c:set var="activeMenu" value="products" />
		<jsp:include page="admin-sidebar.jsp" />

		<div class="main">
			<div class="page-header">
				<a href="${pageContext.request.contextPath}/admin/products" class="back-link">
					<i class="fa-solid fa-arrow-left"></i> Quay lại danh sách
				</a>
			</div>

			<div class="edit-container">

				<form action="${pageContext.request.contextPath}/admin/edit-product" method="post" class="add-product-form">

					<input type="hidden" name="id" value="${product.id}">
					<input type="hidden" name="stock" value="${product.stock}">

					<div class="form-row-flex">
						<div class="form-col-left">
							<h3>Sửa thông tin sản phẩm</h3>
							<p class="sku-display">Mã SKU: <strong class="sku-highlight">${product.sku}</strong></p>
							<div class="form-group">
								<label>Tên sản phẩm</label>
								<input type="text" name="name" value="${product.name}" required>
							</div>

							<div class="form-group">
								<label>Danh mục</label>
								<select name="categoryId" required class="form-control">
									<c:forEach items="${categoryList}" var="cat">
										<option value="${cat.id}" <c:if test="${product.category.id == cat.id}">selected</c:if>>
											${cat.name}
										</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<div class="image-preview-box">
							<img id="imagePreview" src="${pageContext.request.contextPath}/images/${product.url_image}" alt="Ảnh sản phẩm" class="image-preview-img" style="<c:if test='${empty product.url_image}'>display: none;</c:if>">

							<c:if test="${empty product.url_image}">
								<span id="noImageText" class="no-image-text">Chưa có<br>hình ảnh</span>
								</c:if>
						</div>
					</div>

					<div class="form-row-grid-2">
						<div class="form-group">
							<label>Giá bán (VNĐ)</label>
							<input type="number" name="price" value="<fmt:formatNumber value='${product.price}' pattern='#' />" min="0" required>
						</div>

						<div class="form-group">
							<label>Cảnh báo sắp hết</label>
							<input type="number" name="min_stock_level" value="${product.minStockLevel}" min="1" required>
						</div>
					</div>

					<div class="form-group mb-20">
						<label>Link hình ảnh</label>
						<input type="text" name="url_image" id="imageUrlInput" value="${product.url_image}" oninput="updateImagePreview()">
					</div>

					<div class="form-group mb-20">
						<label>Mô tả chi tiết</label>
						<textarea name="description" rows="5">${product.description}</textarea>
					</div>

					<div class="default-info">
						<span>Trạng thái hiển thị:</span>
						<label class="checkbox-label">
							<input type="checkbox" name="status" value="1" <c:if test="${product.status == 1}">checked</c:if>>
								Cho phép hiển thị trên Website
							</label>
						</div>

						<div class="form-actions edit-form-actions">
							<button type="submit" class="btn-save-product">
								<i class="fa-solid fa-floppy-disk"></i> Lưu thay đổi
							</button>
						</div>
					</form>
				</div>
			</div>

			<script>
				function updateImagePreview() {
					const fileName = document.getElementById('imageUrlInput').value;
					const img = document.getElementById('imagePreview');
					const noImageText = document.getElementById('noImageText');

					const contextPath = "${pageContext.request.contextPath}";

					if (fileName && fileName.trim() !== "") {
						img.src = contextPath + "/images/" + fileName;
						img.style.display = 'block';
						if (noImageText) {
							noImageText.style.display = 'none';
						}
					} else {
						img.style.display = 'none';
						if (noImageText) {
							noImageText.style.display = 'block';
						}
					}
				}
		</script>

	</body>
</html>