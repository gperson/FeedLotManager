<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div id="content">
	<h2 class="center_row">${title}</h2>
	<div class="center_row col-md-12">
		<a href="/pickLocation" class="btn btn-primary" role="button">Start</a>
	</div>
	<div class="center_row col-md-12">
		<a href="/editFeeding" class="btn btn-default" role="button">Edit Existing</a>
	</div>
	<sec:authorize access="hasRole('ADMIN')">
		<div class="center_row col-md-12">
			<a href="/manage" class="btn btn-default" role="button">Manage</a>
		</div>
		<div class="center_row col-md-12">
			<a href="/reports" class="btn btn-default" role="button">Reports</a>
		</div>
	</sec:authorize>
</div>