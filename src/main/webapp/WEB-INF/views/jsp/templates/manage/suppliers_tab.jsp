<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="main_buttons">
	<button onclick="openSupplierPopup(this,false);" type="button" class="btn btn-info">New Supplier</button>
</div>
<table class="table table-hover table-striped">
	<thead>
		<tr>
			<th>Name</th>
			<th>Location</th>
			<th>Action</th>			
		</tr>
	</thead>
	<tbody>
		<c:forEach var="supplier" items="${suppliers}">
    		<tr>
				<td class="sName">${supplier.name}</td>
				<td class="sLocation">${supplier.location}</td>
				<td>
					<button id="${supplier.id}" onclick="openSupplierPopup(this,true);" type="button" class="btn btn-info btn-xs">Edit</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div id="supplier_popup" class="popup"style="display:none;">
	<form name="supplierForm" role="form">
		<div class="form-group">
		  <label for="supplierName">Name:</label>
		  <input type="text" placeholder="Location Name" class="form-control" id="supplierName">
		</div>
		<div class="form-group">
		  <label for="supplierLocation">Location:</label>
		  <input type="text" class="form-control" placeholder="Location of Supplier" id="supplierLocation">
		</div>		
		<div id="popup_btns">
			<button id="save_supplier" onclick="saveSupplier()" type="button" class="btn btn-primary">Save</button>
			<button id="cancel_supplier" onclick="closeSupplierPopup()" type="button" class="btn btn-default">Cancel</button>
		</div>
	</form>
</div>