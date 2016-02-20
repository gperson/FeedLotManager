<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="main_buttons">
	<button onclick="openPackerPopup(this,false);" type="button" class="btn btn-info">New Packer</button>
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
		<c:forEach var="packer" items="${packers}">
    		<tr>
				<td class="pName">${packer.name}</td>
				<td class="pLocation">${packer.location}</td>
				<td>
					<button id="${packer.id}" onclick="openPackerPopup(this,true);" type="button" class="btn btn-info btn-xs">Edit</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<div id="packer_popup" class="popup"style="display:none;">
	<form name="packerForm" role="form">
		<div class="form-group">
		  <label for="packerName">Name:</label>
		  <input type="text" class="form-control" id="packerName" autocomplete="off" placeholder="Packer Name">
		</div>
		<div class="form-group">
		  <label for="packerLocation">Location:</label>
		  <input type="text" class="form-control" id="packerLocation" autocomplete="off" placeholder="Packer Location">
		</div>		
		<div id="popup_btns">
			<button id="save_packer" onclick="savePacker()" type="button" class="btn btn-primary">Save</button>
			<button id="cancel_packer" onclick="closePackerPopup()" type="button" class="btn btn-default">Cancel</button>
		</div>
	</form>
</div>