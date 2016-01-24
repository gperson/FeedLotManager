<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="main_buttons">
	<button id="addLocation" type="button" class="btn btn-info" onclick="openLocationPopup(this,false);">Add
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
					<c:forEach var="herd" items="${locale.groupedHerd.herds}" varStatus="status">
				    		<c:out value="${herd.id}"/><c:if test="${!status.last}">,</c:if>	         					    		 
					</c:forEach>
				</td>
				<td class="lCount">${locale.groupedHerd.count}</td>
				<td>
					<button onclick="openLocationPopup(this,true);" type="button" class="btn btn-info btn-xs">Edit</button>
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
		<div id="herd_swapper">
			<table>
				<tr>
					Location's Herds<span style="padding-right: 90px;"></span>Available Herds
					<td>
						<select id="new_group_herds" multiple size="5" name="FromLB">
						</select>
					</td>
					<td align="center" valign="middle"><input type="button"
						onClick="move(this.form.FromLB,this.form.ToLB)" class="btn btn-primary swap_btn" value=">"><br />
						<input type="button" style="margin-top: 3px;"
						onClick="move(this.form.ToLB,this.form.FromLB)" class="btn btn-primary swap_btn" value="<">
					</td>
					<td>
						<select id="orphan_group_herds" multiple size="5" name="ToLB">
							<c:forEach var="herd" items="${orphans.orphans}" varStatus="status">
								<option value="${herd}">${herd}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
		</div>
		<div id="location_popup_btns">
		<button id="save_location" onclick="saveLocation()" type="button" class="btn btn-primary">Save</button>
		<button id="cancel_location" onclick="closeLocationPopup()" type="button" class="btn btn-default">Cancel</button>
		</div>
	</form>
</div>