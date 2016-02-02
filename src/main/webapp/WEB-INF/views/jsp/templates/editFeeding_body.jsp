<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="content">
	<h2 class="home_title center_row">Edit Existing</h2>
	<div class="center_row col-md-12">
		<c:forEach var="feeding" items="${feedings}">
			<div class="edit_feeding edit_well_selector well well-sm">
				<label><span class="edit_ico glyphicon glyphicon-triangle-right" aria-hidden="true"></span>${feeding.user.firstName} ${feeding.user.lastName}</label> <span>Date entered: <fmt:formatDate value="${feeding.lastUpdated}" pattern="MM/dd/yyyy hh:mm a" />,</span><span> Location: ${feeding.groupedHerd.locale.localeName},</span><span>Delivered: ${feeding.deliveredAmount},</span><span> Types: <c:forEach	var="feed" items="${feeding.feeds}" varStatus="status"><c:out value="${feed.feedType.feedType}" /><c:if test="${!status.last}">, </c:if></c:forEach></span>
				<div id="${feeding.id}" class="edit_feeding_inputs">
					<form name="edit_feeding" role="form">
						<div class="form-group">
							<label for="sel1">Select Location:</label> 
							<select class="form-control locale">
								<c:forEach var="groupedHerd" items="${groupedHerds}">
									<option value="${groupedHerd.id}"><c:out value="${groupedHerd.locale.localeName}" /></option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label for="feeds">Feed Ratio:</label>
							<c:forEach var="feed" items="${feeding.feeds}">
								<div class="form-group feedEdits">
									<label>${feed.feedType.feedType}</label> 
									<input data-feedId="${feed.feedType.id}" type="number" max="1" min="0" step="0.1" placeholder="Ratio (0 - 1)" class="form-control ratios" value="${feed.ratio}">
								</div>
							</c:forEach>
							<c:forEach var="feedType" items="${feeds}">
								<c:set var="contains" value="false" />
								<c:forEach var="usedFeed" items="${feeding.feeds}">
									<c:if test="${usedFeed.feedType.id == feedType.id}">
										<c:set var="contains" value="true" />
									</c:if>
								</c:forEach>
								 <c:if test="${!contains}">
									<div class="form-group feedEdits">
										<label>${feedType.feedType}</label> 
										<input data-feedId="${feedType.id}" type="number" max="1" min="0" step="0.1" placeholder="Ratio (0 - 1)" class="form-control ratios">
									</div>
								</c:if>
							</c:forEach>
						</div>
						<div class="form-group">
							<label for="delivered">Amount Deliver:</label>
							<input type="text" class="form-control delivered" value="${feeding.deliveredAmount}">
						</div>
						<div class="form-group">
							<label for="bunkScore">Score:</label> 
							<select class="form-control bunkScore">
								<option value="0" ${feeding.bunkScore == 0 ? 'selected' : ''}>0</option>
								<option value="1" ${feeding.bunkScore == 1 ? 'selected' : ''}>1</option>
								<option value="2" ${feeding.bunkScore == 2 ? 'selected' : ''}>2</option>
								<option value="3" ${feeding.bunkScore == 3 ? 'selected' : ''}>3</option>
								<option value="4" ${feeding.bunkScore == 4 ? 'selected' : ''}>4</option>
								<option value="5" ${feeding.bunkScore == 5 ? 'selected' : ''}>5</option>
							</select>
						</div>
						<div class="form-group">
							<label for="time">Time:</label> 
							<fmt:formatDate value="${feeding.feedingTime}" pattern="a" var="AMorPM" />
							<select class="form-control time">
								<option value="AM" <c:if test="${AMorPM == 'AM'}">selected</c:if>>AM</option>
								<option value="PM" <c:if test="${AMorPM == 'PM'}">selected</c:if>>PM</option>
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