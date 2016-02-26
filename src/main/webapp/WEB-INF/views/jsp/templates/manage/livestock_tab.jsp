<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="main_buttons">
	<button onclick="openBuyLivestockPopup(this,false);" type="button" class="btn btn-info">New Herd</button>
</div>
<table class="table table-hover table-striped">
	<thead>
		<tr>
			<th>Identifier</th>
			<th>Quantity</th>
			<th>Dead</th>
			<th>Start Weight</th>
			<th>Cost</th>
			<th>Tag #</th>
			<th>Sex</th>
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
				<td data-id="${herd.id}" class="hId">${herd.herdLabel}</td>
				<td class="hQuantity">${herd.quantity}</td>
				<td class="hDead">${herd.deadQuantity}</td>
				<td class="hWeight">${herd.weight}</td>
				<td class="hCost">${herd.cost}</td>
				<td class="hTag">${herd.tagNumber}</td>
				<td class="hSex">${herd.sex}</td>
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

<div id="buyLivestock_popup" class="popup" style="display:none; width: 450px !important;">
	<form name="buyLivestockForm" role="form">
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group">
				  <label for="quantity">Identifier/Description:</label>
				  <input class="form-control" id="herdLabel" autocomplete="off" type="text" placeholder="Identifier">
				</div>
				<div class="form-group">
				  <label for="quantity">Quantity:</label>
				  <input class="form-control" id="quantity" autocomplete="off" type="number" min="0" step="1" placeholder="Count">
				</div>
				<div class="form-group">
				  <label for="weight">Weight:</label>
				  <input class="form-control" id="weight" autocomplete="off" type="number" min="0" step="0.1" placeholder="Weight (lbs)">
				</div>
				<div class="form-group">
				  <label for="cost">Cost:</label>
				  <input class="form-control" id="cost" autocomplete="off" type="number" min="0" step="0.1" placeholder="Amount ($)">
				</div>
				<div class="form-group">
				  <label for="tagNumber">Tag Number:</label>
				  <input class="form-control" id="tagNumber" autocomplete="off" placeholder="Tag Number">
				</div>
				<div class="form-group">
				  <label for="dead">Dead Quantity:</label>
				  <input class="form-control" id="dead" autocomplete="off" type="number" min="0" step="1" placeholder="Dead Quantity">
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
				  <label for="sex">Sex:</label>
				  <select class="form-control" id="sex">
				  	<option value="Mix">Mix</option>
				  	<option value="Heifers">Heifers</option>
				  	<option value="Steers">Steers</option>
				  </select>
				</div>
				<label for="estimatedSaleDate">Estimated Sale Date:</label>
				<div class="form-group input-group date" data-provide="datepicker">
				    <input type="text" class="form-control" id="estimatedSaleDate" autocomplete="off" placeholder="Estimated Sale Date">
				    <div class="input-group-addon">
				        <span class="glyphicon glyphicon-th"></span>
				    </div>
				</div>
				<label for="implantDate">Implant Date:</label>
				<div class="form-group input-group date" data-provide="datepicker">
				    <input type="text" class="form-control" id="implantDate" autocomplete="off" placeholder="Implant Date">
				    <div class="input-group-addon">
				        <span class="glyphicon glyphicon-th"></span>
				    </div>
				</div>
				<label for="optiflexDate">Optiflex Date:</label>
				<div class="form-group input-group date" data-provide="datepicker">
				    <input type="text" class="form-control" id="optiflexDate" autocomplete="off" placeholder="Optiflex Date">
				    <div class="input-group-addon">
				        <span class="glyphicon glyphicon-th"></span>
				    </div>
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
			</div>
		</div>		
		<div class="">
			<button id="save_buyLivestock" onclick="saveBuyLivestock()" type="button" class="btn btn-primary">Save</button>
			<button id="cancel_buyLivestock" onclick="closeBuyLivestockPopup()" type="button" class="btn btn-default">Cancel</button>
		</div>
	</form>
</div>