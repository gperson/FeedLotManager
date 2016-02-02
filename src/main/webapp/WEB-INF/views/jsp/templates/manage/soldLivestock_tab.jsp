<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="main_buttons">
	<button type="button" onclick="openSalePopup(this,false);" class="btn btn-info">New Sold Livestock</button>
</div>
<table class="table table-hover table-striped">
	<thead>
		<tr>
			<th>Herds</th>
			<th>Price</th>
			<th>Weight</th>
			<th>Date</th>
			<th>Dressing %</th>
			<th>Shrink %</th>
			<th>Quantity</th>
			<th>Packer</th>
			<th>Options</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="sale" items="${sales}">
    		<tr id="${sale.id}">
				<td id="${sale.groupedHerd.id}" class="saHerds">
					<c:forEach var="herd" items="${sale.groupedHerd.herds}" varStatus="status">
				    		<c:out value="${herd.id}"/><c:if test="${!status.last}">,</c:if>	         					    		 
					</c:forEach>
				</td>
				<td class="saPrice">${sale.salePrice}</td>
				<td class="saWeight">${sale.saleWeight}</td>
				<td class="saDate">${sale.saleDate}</td>
				<td class="saDressing">${sale.dressingPercent}</td>
				<td class="saShrink">${sale.shrinkPercent}</td>
				<td class="saQuantity">${sale.quantity}</td>
				<td data-packer="${sale.packer.id}" class="saPacker">${sale.packer.name}</td>
				<td>
					<button id="${supplier.id}" onclick="openSalePopup(this,true);" type="button" class="btn btn-info btn-xs">Edit</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div id="sale_popup" class="popup"style="display:none;">
	<form name="saleForm" role="form">
		<div class="form-group">
		  <label for="saLocale">Location Sold From:</label>
		  <select class="form-control" id="saLocale">
		  	<option value="0">Select Location</option>
		  	<c:forEach var="group" items="${groupedHerds}">
			    <option value="${group.id}">${group.locale.localeName}</option>
		    </c:forEach>
		  </select>
		</div>
		<div class="form-group">
		  <label for="saPrice">Sale Price:</label>
		  <input class="form-control" id="saPrice" type="number" step="0.1" placeholder="Amount ($)">
		</div>
		<div class="form-group">
		  <label for="saWeight">Sale Weight:</label>
		  <input class="form-control" id="saWeight" type="number" step="0.1" placeholder="Weight (lbs)">
		</div>
		<div class="form-group">
		  <label for="saQuantity">Quantity:</label>
		  <input class="form-control" id="saQuantity" type="number" min="0" step="1" placeholder="Count">
		</div>
		<div class="form-group">
		  <label for="saDressing">Dressing Percent:</label>
		  <input class="form-control" id="saDressing" type="number" max="100" min="0" step="0.1" placeholder="Dressing %">
		</div>
		<div class="form-group">
		  <label for="saShrink">Shrink Percent:</label>
		  <input class="form-control" id="saShrink" type="number" max="100" min="0" step="0.1" placeholder="Shrink %">
		</div>
		<div class="form-group">
		  <label for="saPacker">Packer:</label>
		  <select class="form-control" id="saPacker">
		  	<option value="0">Select Packer</option>
		  	<c:forEach var="packer" items="${packers}">
			    <option value="${packer.id}">${packer.name}</option>
		    </c:forEach>
		  </select>
		</div>
		<label for="saDate">Sale Date:</label>
		<div class="form-group input-group date" data-provide="datepicker">
		    <input type="text" class="form-control" id="saDate" placeholder="Sale Date">
		    <div class="input-group-addon">
		        <span class="glyphicon glyphicon-th"></span>
		    </div>
		</div>		
		<div id="popup_btns">
			<button id="save_sale" onclick="saveSale()" type="button" class="btn btn-primary">Save</button>
			<button id="cancel_sale" onclick="closeSalePopup()" type="button" class="btn btn-default">Cancel</button>
		</div>
	</form>
</div>