<div id="content">
	<div class="center_row col-md-12">
		<h2 class="center_row">Feed Mix</h2>
	</div>
	<div class="center_div">
		<form role="form">
			<div class="deliver_row">
				<div class="bold_label" style="padding-left: 46px;">Bunk Score</div>
				<div class="">
					<select class="form-control" id="bunk_score" style="width: 55px;">
						<option value="0">0</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>
				</div>
			</div>
			<div class="deliver_row">
				<div class="bold_label" style="padding-left: 90px;">Time</div>
				<div>
					<select class="form-control" id="time" style="width: 55px;">
						<option value="AM">AM</option>
						<option value="PM">PM</option>
					</select>
				</div>
			</div>
			<div class="deliver_row">
				<div class="bold_label" style="padding-left: 70px;">Amount</div>
				<div>
					<input type="number" autocomplete="off" step="0.1" min="0" class="form-control" id="amount" placeholder="Lbs" style="width: 75px;">
				</div>
			</div>
			<div class="deliver_row">
				<div>
					<div>
						<label class="bold_label" style="padding-left: 33px; padding-right: 5px;">All Remaining</label> 
						<input id="hasLeftovers" type="checkbox">
					</div>
				</div>
			</div>
			<div class="center_row col-md-12">
				<input id="continueFeeding" class="btn btn-info" type="button" value="Continue Feeding"></input>
				<input id="finish" class="btn btn-primary" type="button" value="Finish"></input>
			</div>
		</form>
	</div>
</div>