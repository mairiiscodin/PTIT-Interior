<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Quản lý sản phẩm</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/admin_layout.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/products.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
	</head>
	<body>

		<c:set var="activeMenu" value="products" />
		<jsp:include page="admin-sidebar.jsp" />

		<div class="main">

			<div class="page-header">
				<h2>Quản lý sản phẩm</h2>
			</div>

			<c:if test="${param.success == 'added'}">
				<div class="alert success">Thêm sản phẩm thành công. Mã SKU đã được tự động tạo.</div>
			</c:if>
			<c:if test="${param.success == 'updated'}">
				<div class="alert success">Cập nhật sản phẩm thành công.</div>
			</c:if>
			<c:if test="${param.success == 'categoryAdded'}">
				<div class="alert success">Thêm danh mục mới thành công! Bạn đã có thể chọn danh mục này khi thêm sản phẩm.</div>
			</c:if>
			<c:if test="${param.error == 'invalidData'}">
				<div class="alert error">Dữ liệu nhập sai định dạng, vui lòng nhập lại.</div>
			</c:if>
			<c:if test="${param.error == 'failed'}">
				<div class="alert error">Có lỗi xảy ra, vui lòng thử lại.</div>
			</c:if>
			<c:if test="${param.error == 'systemError'}">
				<div class="alert error">Hệ thống kết nối database có lỗi, kiểm tra lại.</div>
			</c:if>

			<div class="top-actions" style="display: flex; gap: 12px; margin-bottom: 18px;">
				<button type="button" class="btn-add-category" onclick="toggleAddCategoryForm()" >
					<i class="fa-solid fa-tags"></i> Thêm danh mục
				</button>

				<button type="button" class="btn-add-product" onclick="toggleAddProductForm()">
					<i class="fa-solid fa-box-open"></i> Thêm sản phẩm mới
				</button>
			</div>

			<div id="addProductFormBox" class="add-product-box" style="display: none;">
				<h3>Thêm sản phẩm mới</h3>

				<form action="${pageContext.request.contextPath}/admin/add-product" method="post" class="add-product-form">
					<div class="form-grid">
						<div class="form-group full-width">
							<label>Tên sản phẩm</label>
							<input type="text" name="name" placeholder="VD: Ghế Sofa Cao Cấp" required>
						</div>

						<div class="form-group">
							<label>Danh mục</label>
							<select name="categoryId" required class="form-control" >
								<option value="">-- Chọn danh mục --</option>
								<c:forEach items="${categoryList}" var="cat">
									<option value="${cat.id}">${cat.name}</option>
								</c:forEach>
							</select>
						</div>

						<div class="form-group">
							<label>Giá bán (VNĐ)</label>
							<input type="number" name="price" placeholder="VD: 5000000" min="0" required>
						</div>

						<div class="form-group">
							<label>Số lượng</label>
							<input type="number" name="stock" placeholder="Số lượng nhập kho" min="0" required>
						</div>

						<div class="form-group">
							<label>Cảnh báo sắp hết</label>
							<input type="number" name="min_stock_level" value="5" min="1" required>
						</div>

						<div class="form-group full-width">
							<label>Link hình ảnh</label>
							<input type="text" name="url_image" placeholder="URL hình ảnh sản phẩm">
						</div>

						<div class="form-group full-width">
							<label>Mô tả sản phẩm</label>
							<textarea name="description" placeholder="Nhập mô tả chi tiết..." rows="3"></textarea>
						</div>
					</div>

					<div class="default-info">
						<span><i class="fa-solid fa-eye"></i> Trạng thái mặc định: <strong>Hiển thị trên Web</strong></span>
						<input type="hidden" name="status" value="1"> </div>

					<div class="form-actions">
						<button type="submit" class="btn-save-product">
							<i class="fa-solid fa-floppy-disk"></i> Thêm sản phẩm
						</button>
					</div>
				</form>
			</div>

			<div id="addCategoryFormBox" class="add-product-box" style="display: none; margin-bottom: 24px; border-left: 4px solid #3b82f6;">
				<h3 style="color: #1e293b; font-size: 20px; margin-bottom: 16px;">Thêm danh mục mới</h3>

				<form action="${pageContext.request.contextPath}/admin/add-category" method="post" class="add-product-form">
					<div class="form-grid">
						<div class="form-group">
							<label>Tên danh mục</label>
							<input type="text" name="name" placeholder="VD: Bàn làm việc" required>
						</div>

						<div class="form-group">
							<label>Tiền tố SKU</label>
							<input type="text" name="prefix" placeholder="VD: BAN" required style="text-transform: uppercase;">
						</div>

						<div class="form-group full-width">
							<label>Mô tả chi tiết</label>
							<textarea name="description" placeholder="Nhập mô tả về danh mục này..." rows="2"></textarea>
						</div>
					</div>

					<div class="form-actions">
						<button type="submit" class="btn-save-product" style="background: #3b82f6;">
							<i class="fa-solid fa-plus"></i> Lưu danh mục
						</button>
					</div>
				</form>
			</div>		

			<div class="filter-bar">
				<form action="${pageContext.request.contextPath}/admin/products" method="get" class="filter-form">
					<div class="search-box">
						<i class="fa-solid fa-magnifying-glass"></i>
						<input type="text" name="keyword" placeholder="Tìm tên, mã SKU..." value="${param.keyword}">
					</div>

					<select name="categoryId">
						<option value="">Tất cả danh mục</option>
						<c:forEach items="${categoryList}" var="cat">
							<option value="${cat.id}" <c:if test="${param.categoryId == cat.id}">selected</c:if>>${cat.name}</option>
						</c:forEach>
					</select>

					<select name="status">
						<option value="">Tất cả trạng thái</option>
						<option value="1" <c:if test="${param.status == '1'}">selected</c:if>>Đang hiển thị</option>
						<option value="0" <c:if test="${param.status == '0'}">selected</c:if>>Đã ẩn</option>
						</select>

						<button type="submit" class="btn-filter">
							<i class="fa-solid fa-filter"></i> Lọc
						</button>
					</form>
				</div>

				<div class="table-container">
					<table class="product-table">
						<thead>
							<tr>
								<th>Mã SKU</th>
								<th>Sản phẩm</th>
								<th>Danh mục</th>
								<th>Giá bán</th>
								<th>Tồn kho</th>
								<th>Trạng thái</th>
								<th>Ngày tạo</th>
								<th>Ngày cập nhật</th>
								<th style="text-align: center;">Hành động</th>
							</tr>
						</thead>
						<tbody>

						<c:choose>
							<c:when test="${not empty productList}">
								<c:forEach var="p" items="${productList}">
									<tr>
										<td>
											<span class="sku-badge">
												${p.sku}
											</span>
										</td>

										<td>
											<div style="font-weight: 600; color: #1e293b; font-size: 14px;">${p.name}</div>
										</td>

										<td>
											<span class="role-badge user-role" style="background: #f1f5f9; color: #475569;">
												${p.category.name}
											</span>
										</td>

										<td style="font-weight: 600; color: #0f766e;">
											<fmt:formatNumber value="${p.price}" pattern="#,###"/> đ
										</td>

										<td>
											<c:choose>
												<c:when test="${p.stock <= p.minStockLevel}">
													<span style="color: #b91c1c; font-weight: 700;">
														${p.stock} <i class="fa-solid fa-triangle-exclamation" title="Sắp hết hàng"></i>
													</span>
												</c:when>
												<c:otherwise>
													<span style="color: #166534; font-weight: 600;">${p.stock}</span>
												</c:otherwise>
											</c:choose>
										</td>

										<td>
											<span class="status-badge <c:if test='${p.status == 1}'>active-status</c:if> <c:if test='${p.status == 0}'>locked-status</c:if>">
												<i class="fa-solid <c:if test='${p.status == 1}'>fa-eye</c:if><c:if test='${p.status == 0}'>fa-eye-slash</c:if>"></i> 
												<c:choose>
													<c:when test="${p.status == 1}">Đang hiện</c:when>
													<c:otherwise>Đã ẩn</c:otherwise>
												</c:choose>
											</span>
										</td>

										<td style="font-size: 13px; color: #64748b; font-weight: 500;">
											<fmt:formatDate value="${p.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
										</td>

										<td style="font-size: 13px; color: #64748b; font-weight: 500;">
											<fmt:formatDate value="${p.updatedAt}" pattern="dd/MM/yyyy HH:mm"/>
										</td>

										<td>
											<div class="action-group">
												<a href="${pageContext.request.contextPath}/admin/edit-product?id=${p.id}" class="btn-view" style="padding: 7px 12px; font-size: 13px;">
													<i class="fa-solid fa-pen-to-square"></i> Sửa
												</a>
											</div>
										</td>
									</tr>
								</c:forEach>
							</c:when>

							<c:otherwise>
								<tr>
									<td colspan="9" class="empty-row">Không có sản phẩm nào phù hợp</td>
								</tr>
							</c:otherwise>
						</c:choose>

					</tbody>
				</table>
			</div>
		</div>

		<script>
			// 1. Hàm đóng/mở form THÊM SẢN PHẨM
			function toggleAddProductForm() {
				const productForm = document.getElementById("addProductFormBox");
				const categoryForm = document.getElementById("addCategoryFormBox");

				if (categoryForm) {
					categoryForm.style.display = "none";
				}

				if (productForm.style.display === "none" || productForm.style.display === "") {
					productForm.style.display = "block";
				} else {
					productForm.style.display = "none";
				}
			}

			// 2. Hàm đóng/mở form THÊM DANH MỤC
			function toggleAddCategoryForm() {
				const categoryForm = document.getElementById("addCategoryFormBox");
				const productForm = document.getElementById("addProductFormBox");

				if (productForm) {
					productForm.style.display = "none";
				}

				if (categoryForm.style.display === "none" || categoryForm.style.display === "") {
					categoryForm.style.display = "block";
				} else {
					categoryForm.style.display = "none";
				}
			}
		</script>

	</body>
</html>