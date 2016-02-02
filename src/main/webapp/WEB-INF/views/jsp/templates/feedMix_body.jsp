<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="content">
	<div class="center_row col-md-12">
		<h2 class="center_row">Feed Mix</h2>		
		<c:forEach var="leftover" items="${leftovers}">
			<div id="${leftover.feedingId}" class="leftovers input-group">
		      <span class="input-group-addon">
		        <input class="useLeftover" type="checkbox">
		      </span>
		       <input type="text" class="form-control" disabled value="Leftover - ${leftover.amount} <c:forEach var="feed" items="${leftover.feeds}" varStatus="status"><c:out value="${feed.feedType.feedType}"/><c:if test="${!status.last}">, </c:if></c:forEach>"/>
		    </div>
	    </c:forEach>
	    <c:forEach var="feed" items="${feeds}">
			<div class="input-group">
		  		<span class="input-group-addon" id="sizing-addon2">${feed.feedType}</span>
		  		<input id="${feed.id}" min="0" step="0.1" type="number" class="form-control feed_amount" placeholder="Amount (lbs)" aria-describedby="sizing-addon2">
			</div>
		</c:forEach>
		<div class="center_row col-md-12">
			<input id="next_feedMix" class="btn btn-primary" type="button" value="Next"></input>
		</div>
	</div>
</div>