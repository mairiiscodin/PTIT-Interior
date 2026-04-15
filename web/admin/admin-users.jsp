<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Quản lý người dùng</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/admin_layout.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/style/users.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
	</head>
	<body>

		<c:set var="activeMenu" value="users" />
		<jsp:include page="admin-sidebar.jsp" />

		<div class="main">

			<div class="page-header">
				<h2>Quản lý người dùng</h2>
			</div>

			<c:if test="${param.success == 'added'}">
				<div class="alert success">Thêm tài khoản thành công.</div>
			</c:if>

			<c:if test="${param.success == 'deleted'}">
				<div class="alert success">Xóa tài khoản thành công.</div>
			</c:if>

			<c:if test="${param.success == 'updated'}">
				<div class="alert success">Cập nhật trạng thái thành công.</div>
			</c:if>

			<c:if test="${param.error == 'addFailed'}">
				<div class="alert error">Không thể thêm tài khoản. Có thể email đã tồn tại.</div>
			</c:if>

			<c:if test="${param.error == 'deleteFailed'}">
				<div class="alert error">Không thể xóa tài khoản.</div>
			</c:if>

			<c:if test="${param.error == 'updateFailed'}">
				<div class="alert error">Không thể cập nhật trạng thái.</div>
			</c:if>
			<c:if test="${param.error == 'invalidData'}">
				<div class="alert error">Nhập dữ liệu sai định dạng. Vui lòng thử lại</div>
			</c:if>	
				

			<div class="top-actions">
				<button type="button" class="btn-add-user" onclick="toggleAddUserForm()">
					<i class="fa-solid fa-user-plus"></i> Thêm tài khoản
				</button>
			</div>

			<div id="addUserFormBox" class="add-user-box" style="display: none;">
				<h3>Thêm tài khoản admin mới</h3>
				<p class="form-subtitle">Tạo nhanh tài khoản quản trị cho hệ thống</p>

				<form action="${pageContext.request.contextPath}/admin/user-action" method="post" class="add-user-form">
					<input type="hidden" name="action" value="add">

					<input type="hidden" name="role" value="admin">

					<input type="hidden" name="status" value="1">

					<div class="form-grid">
						<div class="form-group">
							<label>Họ tên</label>
							<input type="text" name="fullName" placeholder="Nhập họ tên" required>
						</div>

						<div class="form-group">
							<label>Email</label>
							<input type="email" name="email" placeholder="Nhập email" required>
						</div>

						<div class="form-group">
							<label>Mật khẩu</label>
							<input type="text" name="password" placeholder="Nhập mật khẩu" required>
						</div>

						<div class="form-group">
							<label>Số điện thoại</label>
							<input type="text" name="phone" placeholder="Nhập số điện thoại">
						</div>

						<div class="form-group full-width">
							<label>Địa chỉ</label>
							<input type="text" name="address" placeholder="Nhập địa chỉ">
						</div>
					</div>

					<div class="default-info">
						<span><i class="fa-solid fa-user-shield"></i> Vai trò: <strong>Admin</strong></span>
						<span><i class="fa-solid fa-circle-check"></i> Trạng thái: <strong>Hoạt động</strong></span>
					</div>

					<div class="form-actions">
						<button type="submit" class="btn-save-user">
							<i class="fa-solid fa-floppy-disk"></i> Tạo tài khoản admin
						</button>
					</div>
				</form>
			</div>

			<div class="filter-bar">
				<form action="${pageContext.request.contextPath}/admin/users" method="get" class="filter-form">
					<div class="search-box">
						<i class="fa-solid fa-magnifying-glass"></i>
						<input type="text" name="keyword" placeholder="Tìm tên, email, số điện thoại..." value="${param.keyword}">
					</div>

					<select name="role">
						<option value="">Tất cả vai trò</option>
						<option value="admin" <c:if test="${param.role == 'admin'}">selected</c:if>>Admin</option>
						<option value="user" <c:if test="${param.role == 'user'}">selected</c:if>>Người dùng</option>
						</select>

						<select name="status">
							<option value="">Tất cả trạng thái</option>
							<option value="1" <c:if test="${param.status == '1'}">selected</c:if>>Hoạt động</option>
						<option value="0" <c:if test="${param.status == '0'}">selected</c:if>>Bị khóa</option>
						</select>

						<button type="submit" class="btn-filter">
							<i class="fa-solid fa-filter"></i> Lọc
						</button>
					</form>
				</div>

				<div class="table-container">
					<table class="user-table">
						<thead>
							<tr>
								<th>ID</th>
								<th>Họ tên</th>
								<th>Email</th>
								<th>SĐT</th>
								<th>Vai trò</th>
								<th>Trạng thái</th>
								<th>Ngày tạo</th>
								<th>Ngày cập nhật</th>
								<th>Cập nhật</th>
								<th>Hành động</th>
							</tr>
						</thead>
						<tbody>

						<c:choose>
							<c:when test="${not empty userList}">
								<c:forEach var="u" items="${userList}">
									<tr>
										<td>#${u.id}</td>
										<td>${u.fullName}</td>
										<td>${u.email}</td>
										<td>${u.phone}</td>

										<td>
											<span class="role-badge <c:if test="${u.role == 'admin'}">admin-role</c:if> <c:if test="${u.role == 'user'}">user-role</c:if>">
												${u.role}
											</span>
										</td>

										<td>
											<span class="status-badge <c:if test="${u.status == 1}">active-status</c:if> <c:if test="${u.status == 0}">locked-status</c:if>">
												<c:choose>
													<c:when test="${u.status == 1}">Hoạt động</c:when>
													<c:otherwise>Bị khóa</c:otherwise>
												</c:choose>
											</span>
										</td>

										<td style="font-size: 13px; color: #64748b; font-weight: 500;">
											<fmt:formatDate value="${u.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
										</td>

										<td style="font-size: 13px; color: #64748b; font-weight: 500;">
											<fmt:formatDate value="${u.updatedAt}" pattern="dd/MM/yyyy HH:mm"/>
										</td>

										<td>
											<form action="${pageContext.request.contextPath}/admin/user-action" method="post" class="status-form">
												<input type="hidden" name="action" value="updateStatus">
												<input type="hidden" name="userId" value="${u.id}">

												<select name="status">
													<option value="1" <c:if test="${u.status == 1}">selected</c:if>>Hoạt động</option>
													<option value="0" <c:if test="${u.status == 0}">selected</c:if>>Bị khóa</option>
													</select>

													<button type="submit" class="btn-update">Lưu</button>
												</form>
											</td>

											<td>
												<div class="action-group">	
													<form action="${pageContext.request.contextPath}/admin/user-action" method="post"
													  onsubmit="return confirm('Bạn có chắc muốn xóa tài khoản này?');">
													<input type="hidden" name="action" value="delete">
													<input type="hidden" name="userId" value="${u.id}">
														<button type="submit" class="btn-delete"
															<c:if test="${user_id == u.id}"> disabled="disabled" </c:if>>
																<i class="fa-solid fa-trash"></i> Xóa
														</button>	
													</form>
												</div>
											</td>
										</tr>
								</c:forEach>
							</c:when>

							<c:otherwise>
								<tr>
									<td colspan="10" class="empty-row">Không có người dùng nào</td>
								</tr>
							</c:otherwise>
						</c:choose>

					</tbody>
				</table>
			</div>
		</div>

		<script>
			function toggleAddUserForm() {
				const formBox = document.getElementById("addUserFormBox");
				if (formBox.style.display === "none" || formBox.style.display === "") {
					formBox.style.display = "block";
				} else {
					formBox.style.display = "none";
				}
			}
		</script>

	</body>
</html>