<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="main_buttons">
	<button type="button" onclick="openUserPopup(this,false);" class="btn btn-info">New User</button>
</div>
<table class="table table-hover table-striped">
	<thead>
		<tr>
			<th class="col-md-2">Name</th>
			<th class="col-md-2">Username</th>
			<th class="col-md-3">Email</th>
			<th class="col-md-2">Roles</th>
			<th class="col-md-3">Actions</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="user" items="${users}">
    		<tr id="${user.id}">
				<td class="uName">${user.firstName} ${user.lastName}</td>
				<td class="uUsername">${user.username}</td>
				<td class="uEmail">${user.email}</td>
				<td class="uRoles">
					<c:forEach var="role" items="${user.roles}" varStatus="status">
				    		<c:out value="${role}"/><c:if test="${!status.last}">,</c:if>	         					    		 
					</c:forEach>
				</td>
				<td>
					<button onclick="openUserPopup(this,true);" type="button" class="btn btn-info btn-xs">Edit</button>
					<button onclick="resetPassword('${user.username}');" type="button" class="btn btn-warning btn-xs">Reset
						Password</button>
					<c:if test="${!user.enabled}">
						<button onclick="enableDisableUser(${user.id},true);" type="button" class="btn btn-default btn-xs">Enable</button>
					</c:if>
					<c:if test="${user.enabled}">
						<button onclick="enableDisableUser(${user.id},false);"type="button" class="btn btn-danger btn-xs">Disable</button>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div id="user_popup" class="popup"style="display:none;">
	<form name="userForm" role="form">
		<div class="form-group">
		  <label for="userFirstName">First Name:</label>
		  <input type="text" class="form-control" id="userFirstName">
		</div>
		<div class="form-group">
		  <label for="userLastName">Last Name:</label>
		  <input type="text" class="form-control" id="userLastName">
		</div>
		<div class="form-group">
		  <label for="username">Username:</label>
		  <input type="text" class="form-control" id="username">
		</div>
		<div class="form-group">
		  <label for="email">Email:</label>
		  <input type="text" class="form-control" id="email">
		</div>
		<label class="checkbox-inline"><input id="adminRole" type="checkbox" value="">Admin</label>			
		<div id="popup_btns">
			<button id="save_user" onclick="saveUser()" type="button" class="btn btn-primary">Save</button>
			<button id="cancel_user" onclick="closeUserPopup()" type="button" class="btn btn-default">Cancel</button>
		</div>
	</form>
</div>