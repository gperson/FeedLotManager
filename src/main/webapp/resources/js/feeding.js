$(document).ready(function(){
	/*
	 * Click location
	 */
	$(".location_selector").click(function(){
		$.ajax({
			type : "POST",
			headers: headers,
			url : "/pickedLocation",
			cache: false,
			data : JSON.stringify({ 
				id : 0,
				groupedHerd : {
					id : parseInt($(this).attr("data-group"))
				}
			}),
			dataType : 'json',
			contentType: 'application/json',
			success : function(result){
				if(result.success === true){
					window.location.href = window.location.protocol + "//"+ window.location.host + "/feedMix/"+result.object.groupedHerd.id+"/"+result.object.id;
				} else {
					alert("An error occurred.");
				}

			}
		});
	});

	/*
	 * Click 'Next' on feed mix
	 */
	$("#next_feedMix").click(function(){
		var feeds = [];
		$( ".feed_amount" ).each(function() {
			if(!($(this).val() === "")){
				var feed = { feedType : { id : parseInt($(this).attr("id")) }, amount : parseFloat($(this).val()) };
				feeds.push(feed);
			}
		});
		var leftovers = [];
		$( ".leftovers" ).each(function() {
			if($(this).find('.useLeftover').is(":checked")){
				var leftover = { feedingId : parseInt($(this).attr("id")) };
				leftovers.push(leftover);
			}
		});
		var feedingId = parseInt(window.location.pathname.substr(window.location.pathname.lastIndexOf('/') + 1));
		$.ajax({
			type : "POST",
			headers: headers,
			url : "/feedMixed",
			data : JSON.stringify({ 
				id : feedingId,
				feeds : feeds,
				leftovers : leftovers
			}),
			cache: false,
			dataType : 'json',
			contentType: 'application/json',
			success : function(result){
				if(result.success === true){
					window.location.href = window.location.protocol + "//"+ window.location.host + "/deliver/"+result.object.id;
				} else {
					alert("An error occurred.");
				}

			}
		});
	});
	
	/*
	 * Handles 'All Remaining' check box
	 */
	$("#hasLeftovers").click(function(){
		if($("#hasLeftovers").is(":checked")){
			$("#amount").val("");
			$("#amount").prop('disabled',true);
		} else {
			$("#amount").prop('disabled',false);
		}
	});

	/*
	 *  Click 'Finish'
	 */
	$("#finish").click(function(){
		saveDeliver("home");
	});
	
	/*
	 *  Click 'Continue Feeding'
	 */
	$("#continueFeeding").click(function(){
		saveDeliver("pickLocation");
	});
});

function saveDeliver(to){
	var feedingId = parseInt(window.location.pathname.substr(window.location.pathname.lastIndexOf('/') + 1));
	var d = new Date();
	var time = ($("#time").val() === "AM" ? d.setHours(8) : d.setHours(16));		
	$.ajax({
		type : "POST",
		headers: headers,
		url : "/delivered",
		data : JSON.stringify({ 
			id : feedingId,
			bunkScore : parseInt($("#bunk_score").val()),
			deliveredAmount : parseFloat($("#amount").val()),
			feedingTime : time,
			hasLeftovers : (!$("#hasLeftovers").is(":checked"))
		}),
		dataType : 'json',
		cache: false,
		contentType: 'application/json',
		success : function(result){
			if(result.success === true){
				window.location.href = window.location.protocol + "//"+ window.location.host + "/" + to;
			} else {
				alert("An error occurred.");
			}

		}
	});
}