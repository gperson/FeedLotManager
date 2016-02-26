<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div id="content">
	<h4 class="center_row">Password Reset</h4>
	<div id="change-password">
		<c:if test="${not empty msg}">
			<div class="alert alert-success" role="alert">${msg}</div>
		</c:if>
		<form:form method="POST"  action="/resetPassword">
			<div class="input-group">
			  <span class="input-group-addon" id="">Email:</span>
			  <form:input type="text" class="form-control" path="email" placeholder="Email Address" aria-describedby="email"/>
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input name="submit" class="btn btn-primary" type="submit" value="Submit" />
		</form:form>
	</div>
</div>