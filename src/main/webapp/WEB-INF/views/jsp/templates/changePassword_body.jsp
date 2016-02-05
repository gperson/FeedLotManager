<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="content">
	<div id="login-box">
		<c:if test="${not empty error}">
			<div class="alert alert-danger" role="alert">${error}</div>
		</c:if>
		<form name='loginForm' action="<c:url value='/changePassword' />" method='POST'>
			<div class="input-group">
			  <span class="input-group-addon">Password</span>
			  <input type="password" class="form-control" name="password1" placeholder="Password" aria-describedby="usernameInput">
			</div>
			<div class="input-group">
			  <span class="input-group-addon">Verify Password</span>
			  <input type="password" class="form-control" name="password2" placeholder="Password" aria-describedby="passwordInput">
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input name="submit" class="btn btn-primary" type="submit" value="Submit" />
		</form>
	</div>
</div>