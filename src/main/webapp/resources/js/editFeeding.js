$(document).ready(function(){
	$(".edit_feeding_inputs").hide();
	
	/*
	 * Click edit feeding
	 */
	$(document).on("click",".edit_ico",function(){
		$(this).parent().parent().find(".edit_feeding_inputs").toggle();
		if($(this).hasClass("glyphicon-triangle-right")){
			$(this).removeClass("glyphicon-triangle-right");
			$(this).addClass("glyphicon-triangle-bottom");
		} else {
			$(this).addClass("glyphicon-triangle-right");
			$(this).removeClass("glyphicon-triangle-bottom");
		}
	});
	
});

function closeEdit(element){
	var ele = $(element).parent().parent().parent();
	ele.hide();
	ele.parent().find(".edit_ico").addClass("glyphicon-triangle-right");
	ele.parent().find(".edit_ico").removeClass("glyphicon-triangle-bottom");
}

function saveEdit(element){
	var ele = $(element).parent().parent().parent();
	var feeds = [];
	ele.find(".ratios").each(function(){
		if(parseFloat($(this).val()) > 0){
			feeds.push({ratio : parseFloat($(this).val()), feedType : { id : parseInt($(this).attr("data-feedId"))} });
		}
	});
	
	var d = new Date();
	var time = (ele.find('.time').val() === "AM" ? d.setHours(8) : d.setHours(16));		
	
	var feeding = {
		id : parseInt(ele.attr('id')),
		bunkScore : parseInt(ele.find('.bunkScore').val()),
		deliveredAmount : parseFloat(ele.find('.delivered').val()),
		feedingTime : time,
		hasLeftovers : false,
		feeds : feeds,
		groupedHerd : {
			id : parseInt(ele.find('.locale').val())
		}
	};
	
	$.ajax({
		type : "POST",
		headers: headers,
		url : "/editedFeeding",
		data : JSON.stringify(feeding),
		dataType : 'json',
		contentType: 'application/json',
		cache: false,
		success : function(result){
			if(result.success === true){
				location.reload();
			} else {
				alert("An error occurred.");
			}
		}
	});
		
	ele.hide();
	ele.parent().find(".edit_ico").addClass("glyphicon-triangle-right");
	ele.parent().find(".edit_ico").removeClass("glyphicon-triangle-bottom");
}