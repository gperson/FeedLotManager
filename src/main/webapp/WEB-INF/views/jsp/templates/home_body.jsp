<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div id="content">
	<h2 class="home_title center_row">${title}</h2>
	<div class="center_row col-md-12">
		<button type="button" class="btn btn-primary">Start</button>
	</div>
	<div class="center_row col-md-12">
		<button type="button" class="btn btn-default">Edit Existing</button>
	</div>
	<sec:authorize access="hasRole('ADMIN')">
		<div class="center_row col-md-12">
			<button type="button" class="btn btn-default">Manage</button>
		</div>
		<div class="center_row col-md-12">
			<button type="button" class="btn btn-default">Reports</button>
		</div>
	</sec:authorize>
</div>