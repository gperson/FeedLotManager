<div id="content">
	<div class="container">

		<!-- Nav tabs -->
		<ul class="nav nav-tabs" role="tablist">
			<li class="active"><a href="#locations" role="tab"
				data-toggle="tab"> Locations </a></li>
			<li><a href="#livestock" role="tab" data-toggle="tab">Livestock</a></li>
			<li><a href="#feed" role="tab" data-toggle="tab">Feed </a></li>
			<li><a href="#users" role="tab" data-toggle="tab"> Users </a></li>
		</ul>

		<!-- Tab panes -->
		<div class="tab-content">
			<div class="tab-pane fade active in" id="locations">
				<div class="main_buttons">
					<button type="button" class="btn btn-info">Add Location</button>
					<button type="button" class="btn btn-info">Delete
						Location</button>
				</div>
				<table class="table table-hover table-striped">
					<thead>
						<tr>
							<th class="col-md-3">Location</th>
							<th class="col-md-6">Herds</th>
							<th class="col-md-1">Quantity</th>
							<th class="col-md-2"></th>
						</tr>
					</thead>
					<tbody>
						<tr id="1">
							<td>SE Lot</td>
							<td>34, 67, 78</td>
							<td>200</td>
							<td>
								<button type="button" class="btn btn-info btn-xs">Edit</button>
								<button type="button" class="btn btn-warning btn-xs">Delete</button>
							</td>
						</tr>
						<tr id="2">
							<td>NW Back Lot</td>
							<td>56, 800, 8</td>
							<td>250</td>
							<td>
								<button type="button" class="btn btn-info btn-xs">Edit</button>
								<button type="button" class="btn btn-warning btn-xs">Delete</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="tab-pane fade" id="livestock">
				<h2>Add, delete, view and edit livestock here</h2>
			</div>
			<div class="tab-pane fade" id="feed">
				<h2>Add, delete, view and edit feed stock here</h2>
			</div>
			<div class="tab-pane fade" id="users">
				<h2>Add, delete, view and edit users here</h2>
			</div>
		</div>
	</div>
</div>
<div id="location_popup">
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
		<button id="save_location" type="button" class="btn btn-primary">Save</button>
		<button id="cancel_location" type="button" class="btn btn-default">Cancel</button>
		</div>
	</form>
</div>
<div id="fade" class="black_overlay"></div>