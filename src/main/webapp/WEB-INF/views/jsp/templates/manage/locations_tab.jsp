<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="main_buttons">
	<button id="addLocation" type="button" class="btn btn-info" onclick="openLocationPopup(this,true)">Add
		Location</button>
</div>
<table class="table table-hover table-striped">
	<thead>
		<tr>
			<th class="col-md-3">Location</th>
			<th class="col-md-6">Herds</th>
			<th class="col-md-1">Quantity</th>
			<th class="col-md-2">Actions</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="locale" items="${locales}">
    		<tr class="" id="${locale.id}">
				<td class="lName">${locale.localeName}</td>
				<td class="lHerds">
					<c:forEach var="herd" items="${locale.herds}" varStatus="status">
				    		<c:out value="${herd.id}"/><c:if test="${!status.last}">,</c:if>	         					    		 
					</c:forEach>
				</td>
				<td class="lCount">${locale.livestockCount}</td>
				<td>
					<button onclick="openLocationPopup(this,false)" type="button" class="btn btn-info btn-xs">Edit</button>
					<c:if test="${!locale.enabled}">
						<button onclick="enableDisableLocale(${locale.id},true);" type="button" class="btn btn-default btn-xs">Enable</button>
					</c:if>
					<c:if test="${locale.enabled}">
						<button onclick="enableDisableLocale(${locale.id},false);"type="button" class="btn btn-danger btn-xs">Disable</button>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<!-- Add/Edit Location popup -->
<div id="location_popup" class="popup" style="display:none;">
	<form name="combo_box" class="swapper" role="form">
		<div class="form-group">
		  <label for="name">Location Name:</label>
		  <input type="text" class="form-control" id="name">
		</div>
		<table>
			<tr>
				Location's Herds<span style="padding-right: 90px;"></span>Available Herds
				<td><select multiple size="5" name="FromLB">
						<option value="23">23</option>
						<option value="45">45</option>
						<option value="356">356</option>
				</select></td>
				<td align="center" valign="middle"><input type="button"
					onClick="move(this.form.FromLB,this.form.ToLB)" class="btn btn-primary swap_btn" value=">"><br />
					<input type="button" style="margin-top: 3px;"
					onClick="move(this.form.ToLB,this.form.FromLB)" class="btn btn-primary swap_btn" value="<">
				</td>
				<td>
				<select multiple size="5" name="ToLB">
					<option value="2">2</option>
					<option value="49">49</option>
				</select></td>
			</tr>
		</table>
		<div id="location_popup_btns">
		<button id="save_location" onclick="saveLocation()" type="button" class="btn btn-primary">Save</button>
		<button id="cancel_location" onclick="closeLocationPopup()" type="button" class="btn btn-default">Cancel</button>
		</div>
	</form>
</div>