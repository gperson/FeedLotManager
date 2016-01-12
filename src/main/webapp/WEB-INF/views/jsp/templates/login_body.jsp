<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
#login-box {
	text-align: center;
	display: table;
    margin: 0 auto;
    padding-top: 20px;
}
#login-box .input-group {
	width: 225px;
	margin-bottom: 10px;
}
#login-box .input-group-addon {
	width: 95px;
}
input:-webkit-autofill {
    -webkit-box-shadow: 0 0 0px 1000px white inset;
}
#login-box alert {
	margin-bottom: 0px;
	margin-top: 20px;
}
</style>

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
			<input name="submit" class="btn btn-primary" type="submit" value="Submit" />
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>
</div>