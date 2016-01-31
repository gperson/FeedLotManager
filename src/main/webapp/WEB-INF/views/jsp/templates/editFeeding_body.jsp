<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="content">
	<h2 class="home_title center_row">Edit Existing</h2>
	<div class="center_row col-md-12">
		<c:forEach var="feeding" items="${feedings}">
			<div id="${feeding.id}" class="edit_feeding edit_well_selector well well-sm">
				<label><span class="edit_ico glyphicon glyphicon-triangle-right" aria-hidden="true"></span>${feeding.user.firstName} ${feeding.user.lastName}</label> <span>Date entered: <fmt:formatDate value="${feeding.lastUpdated}" pattern="MM/dd/yyyy hh:mm a" />,</span><span> Location: ${feeding.groupedHerd.locale.localeName},</span><span>Delivered: ${feeding.deliveredAmount},</span><span> Types: <c:forEach	var="feed" items="${feeding.feeds}" varStatus="status"><c:out value="${feed.feedType.feedType}" /><c:if test="${!status.last}">, </c:if></c:forEach></span>
				<div class="edit_feeding_inputs">
					<form name="edit_feeding" role="form">
						<div class="form-group">
							<label for="sel1">Select Location:</label> 
							<select class="form-control" id="sel1">
								<c:forEach var="groupedHerd" items="${groupedHerds}">
									<option value="${groupedHerd.id}"><c:out value="${groupedHerd.locale.localeName}" /></option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label for="feeds">Feed:</label>
							<!-- Existing feeds -->
							<c:forEach var="feed" items="${feeds}">
								<div id="${feed.id}" class="form-group feedEdits">
									<label>${feed.feedType}</label> 
									<input type="text" class="form-control"> 
									<input type="text" class="form-control ratios">
								</div>
							</c:forEach>
						</div>
						<!-- Others amount, score, time -->
						<div class="form-group">
							<label for="usr">Amount:</label>
							<input type="text" class="form-control" id="usr">
						</div>
						<div class="form-group">
							<label for="sel1">Score:</label> 
							<select class="form-control" id="sel1">
								<option value="">1</option>
							</select>
						</div>
						<div class="form-group">
							<label for="sel1">Time:</label> 
							<select class="form-control" id="sel1">
								<option value="">AM</option>
							</select>
						</div>
						<div>
							<button id="" onclick="saveEdit(this);" type="button" class="btn btn-primary">Save</button>
							<button id="" onclick="closeEdit(this);" type="button" class="btn btn-default">Cancel</button>
						</div>
					</form>
				</div>
			</div>
		</c:forEach>
	</div>
</div>