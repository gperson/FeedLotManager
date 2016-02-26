<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="main_buttons">
	<button onclick="openFeedPopup(this,false);" type="button" class="btn btn-info">Add Type</button>
</div>
<table class="table table-hover table-striped">
	<thead>
		<tr>
			<th>Feed Type</th>
			<th>Dried Matter Percentage</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="feed" items="${feeds}">
    		<tr id="${feed.id}">
				<td class="fFeedType">${feed.feedType}</td>
				<td class="fDMP">${feed.driedMatterPercentage}</td>
				<td>
					<button onclick="openFeedPopup(this,true);" type="button" class="btn btn-info btn-xs">Edit</button>
					<c:if test="${!feed.enabled}">
						<button onclick="enableDisableFeed(${feed.id},true);" type="button" class="btn btn-default btn-xs">Enable</button>
					</c:if>
					<c:if test="${feed.enabled}">
						<button onclick="enableDisableFeed(${feed.id},false);"type="button" class="btn btn-danger btn-xs">Disable</button>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div id="feed_popup" class="popup"style="display:none;">
	<form name="feedForm" role="form">
		<div class="form-group">
		  <label for="feedType">Feed Description:</label>
		  <input type="text" autocomplete="off" class="form-control" id="feedType" placeholder="Feed Description">
		</div>
		<div class="form-group">
		  <label for="dmp">Dried Matter Percentage:</label>
		  <input class="form-control" id="dmp" autocomplete="off" type="number" max="100" min="0" step="0.1" placeholder="Dried Matter %">
		</div>		
		<div class="popup_btns">
			<button id="save_feed" onclick="saveFeed()" type="button" class="btn btn-primary">Save</button>
			<button id="cancel_feed" onclick="closeFeedPopup()" type="button" class="btn btn-default">Cancel</button>
		</div>
	</form>
</div>