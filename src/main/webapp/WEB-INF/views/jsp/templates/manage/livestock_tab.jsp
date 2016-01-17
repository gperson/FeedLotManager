<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<td class="hTag">${hered.tagNumber}</td>
				<td class="hSale">${hered.estimatedSaleDate}</td>
				<td class="hImplant">${herd.implantDate}</td>
				<td class="hOptiflex">${herd.optiflexDate}</td>
				<td class="hSupplier" data-supplier="${herd.supplier.id}">${herd.supplier.name}</td>
				<td class="hEntered">${herd.dateEntered}</td>
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
		  <input type="text" class="form-control" id="quantity">
		</div>
		<div class="form-group">
		  <label for="weight">Weight:</label>
		  <input type="text" class="form-control" id="weight">
		</div>
		<div class="form-group">
		  <label for="cost">Cost:</label>
		  <input type="text" class="form-control" id="cost">
		</div>
		<div class="form-group">
		  <label for="tagNumber">Tag Number:</label>
		  <input type="text" class="form-control" id="tagNumber">
		</div>
		<div class="form-group">
		  <label for="estimatedSaleDate">Estimated Sale Date:</label>
		  <input type="text" class="form-control" id="estimatedSaleDate">
		</div>
		<div class="form-group">
		  <label for="implantDate">Implant Date:</label>
		  <input type="text" class="form-control" id="implantDate">
		</div>
		<div class="form-group">
		  <label for="optiflexDate">Optiflex Date:</label>
		  <input type="text" class="form-control" id="optiflexDate">
		</div>
		<div class="form-group">
		  <label for="supplier">Supplier:</label>
		  <input type="text" class="form-control" id="supplier">
		</div>		
		<div id="popup_btns">
			<button id="save_buyLivestock" onclick="saveBuyLivestock()" type="button" class="btn btn-primary">Save</button>
			<button id="cancel_buyLivestock" onclick="closeBuyLivestockPopup()" type="button" class="btn btn-default">Cancel</button>
		</div>
	</form>
</div>