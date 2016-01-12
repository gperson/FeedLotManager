$( document ).ready(function() {
	
	/*
	 * Click location
	 */
	$(".location_selector").click(function(){
		$.ajax({
			type : "POST",
			headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			url : "/pickedLocation",
			data : JSON.stringify({ 'localeId' : parseInt($(this).data("locale"))}),
			dataType : 'json',
			success : function(result){
				window.location.href = window.location.protocol + "//"+ window.location.host + "/feedMix";
			}
		});		
	});
	
	/*
	 * Click 'Next' on feed mix
	 */
	$("#next_feedMix").click(function(){
//		$.ajax({
//			type : "POST",
//			headers: { 
//		        'Accept': 'application/json',
//		        'Content-Type': 'application/json' 
//		    },
//			url : "/pickedLocation",
//			data : JSON.stringify({ 'localeId' : parseInt($(this).data("locale"))}),
//			dataType : 'json',
//			success : function(result){
//				window.location.href = window.location.protocol + "//"+ window.location.host + "/feedMix";
//			}
//		});
		window.location.href = window.location.protocol + "//"+ window.location.host + "/deliver";
	});
	
	/*
	 *  Click 'Finish'
	 */
	$("#finish").click(function(){
//		$.ajax({
//			type : "POST",
//			headers: { 
//		        'Accept': 'application/json',
//		        'Content-Type': 'application/json' 
//		    },
//			url : "/pickedLocation",
//			data : JSON.stringify({ 'localeId' : parseInt($(this).data("locale"))}),
//			dataType : 'json',
//			success : function(result){
//				window.location.href = window.location.protocol + "//"+ window.location.host + "/feedMix";
//			}
//		});
		window.location.href = window.location.protocol + "//"+ window.location.host + "/home";
	});
});