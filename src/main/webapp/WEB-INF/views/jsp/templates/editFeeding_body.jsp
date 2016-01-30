<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div id="content">
	<h2 class="home_title center_row">Edit Existing</h2>
	<div class="center_row col-md-12">
		<c:forEach var="feeding" items="${feedings}">
			<div id="${feeding.id}" class="edit_feeding well_selector well well-sm">
				<label>${feeding.user.firstName} ${feeding.user.lastName}</label> 
				<span>Date entered: <fmt:formatDate value="${feeding.lastUpdated}" pattern="MM/dd/yyyy hh:mm a"/>,</span><span> Location: ${feeding.groupedHerd.locale.localeName},</span><span>Delivered: ${feeding.deliveredAmount},</span><span> Types: <c:forEach var="feed" items="${feeding.feeds}" varStatus="status"><c:out value="${feed.feedType.feedType}"/><c:if test="${!status.last}">, </c:if></c:forEach></span>
				<div class="edit_input">
					<form name="edit_feeding" role="form">
						<div class="form-group">
						  <label for="location">Location:</label>
						  <select id="location">
						  	<option>Select Location</option>
						  </select>
						</div>
						<div class="form-group">
						  <label for="feeds">Feed:</label>
						  <div id="feeds">
						  	<input type="text" placeholder="Type"/>
						  	<input type="text" placeholder="Ratio"/>
						  </div>
						  <!-- Others amount, score, time -->
						</div>		
						<div>
							<button id="" onclick="" type="button" class="btn btn-primary">Save</button>
							<button id="" onclick="" type="button" class="btn btn-default">Cancel</button>
						</div>
					</form>
				</div>
			</div>
		</c:forEach>
	</div>
</div>