<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="content">
	<div id="login-box">
		<c:if test="${not empty error}">
			<div class="alert alert-danger" role="alert">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="alert alert-success" role="alert">${msg}</div>
		</c:if>
		<form name='loginForm' action="<c:url value='/login' />" method='POST'>
			<div class="input-group">
			  <span class="input-group-addon" id="usernameInput">Username</span>
			  <input type="text" class="form-control" name="username" placeholder="Username" aria-describedby="usernameInput">
			</div>
			<div class="input-group">
			  <span class="input-group-addon" id="passwordInput">Password</span>
			  <input type="password" class="form-control" name="password" placeholder="Password" aria-describedby="passwordInput">
			</div>
			<div id="forgot_pwd">
				<a href="/resetPassword">Forget username or password?</a>
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input name="submit" class="btn btn-primary" type="submit" value="Submit" />
		</form>
	</div>
</div>