<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="main_buttons">
	<button onclick="openBuyLivestockPopup(this,false);" type="button" class="btn btn-info">New Herd</button>
</div>
<table class="table table-hover table-striped">
	<thead>
		<tr>
			<th>Id</th>
			<th>Quantity</th>
			<th>Start Weight</th>
			<th>Cost</th>
			<th>Tag #</th>
			<th>Estimated Sale</th>
			<th>Implant</th>
			<th>Optiflex</th>
			<th>Supplier</th>
			<th>Entered</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="herd" items="${herds}">
    		<tr>
				<td class="hId">${herd.id}</td>
				<td class="hQuantity">${herd.quantity}</td>
				<td class="hWeight">${herd.weight}</td>
				<td class="hCost">${herd.cost}</td>
				<td class="hTag">${herd.tagNumber}</td>
				<td class="hSale"><fmt:formatDate value="${herd.estimatedSaleDate}" pattern="MM/dd/yyyy"/></td>
				<td class="hImplant"><fmt:formatDate value="${herd.implantDate}" pattern="MM/dd/yyyy"/></td>
				<td class="hOptiflex"><fmt:formatDate value="${herd.optiflexDate}" pattern="MM/dd/yyyy"/></td>
				<td class="hSupplier" data-supplier="${herd.supplier.id}">${herd.supplier.name}</td>
				<td class="hEntered"><fmt:formatDate value="${herd.dateEntered}" pattern="MM/dd/yyyy"/></td>
				<td>
					<button id="${buyLivestock.id}" onclick="openBuyLivestockPopup(this,true);" type="button" class="btn btn-info btn-xs">Edit</button>
				</td>
			</tr>
		</c:forEach>
</table>

<div id="buyLivestock_popup" class="popup"style="display:none;">
	<form name="buyLivestockForm" role="form">
		<div class="form-group">
		  <label for="quantity">Quantity:</label>
		  <input class="form-control" id="quantity" type="number" min="0" step="1" placeholder="Count">
		</div>
		<div class="form-group">
		  <label for="weight">Weight:</label>
		  <input class="form-control" id="weight" type="number" min="0" step="0.1" placeholder="Weight (lbs)">
		</div>
		<div class="form-group">
		  <label for="cost">Cost:</label>
		  <input class="form-control" id="cost" type="number" min="0" step="0.1" placeholder="Amount ($)">
		</div>
		<div class="form-group">
		  <label for="tagNumber">Tag Number:</label>
		  <input class="form-control" id="tagNumber" placeholder="Tag Number">
		</div>
		<div class="form-group">
		  <label for="estimatedSaleDate">Estimated Sale Date:</label>
		  <input type="text" class="form-control" id="estimatedSaleDate" placeholder="Estimated Sale Date">
		</div>
		<div class="form-group">
		  <label for="implantDate">Implant Date:</label>
		  <input type="text" class="form-control" id="implantDate" placeholder="Implant Date">
		</div>
		<div class="form-group">
		  <label for="optiflexDate">Optiflex Date:</label>
		  <input type="text" class="form-control" id="optiflexDate" placeholder="Optiflex Date">
		</div>
		<div class="form-group">
		  <label for="supplier">Supplier:</label>
		  <select class="form-control" id="supplier">
		  	<option value="0">Select Supplier</option>
		  	<c:forEach var="supplier" items="${suppliers}">
			    <option value="${supplier.id}">${supplier.name}</option>
		    </c:forEach>
		  </select>
		</div>		
		<div id="popup_btns">
			<button id="save_buyLivestock" onclick="saveBuyLivestock()" type="button" class="btn btn-primary">Save</button>
			<button id="cancel_buyLivestock" onclick="closeBuyLivestockPopup()" type="button" class="btn btn-default">Cancel</button>
		</div>
	</form>
</div>