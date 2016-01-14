$( document ).ready(function() {
	var headers = {};
	headers[$("meta[name='_csrf_header']").attr("content")] = $("meta[name='_csrf']").attr("content");
	headers['Accept'] = 'application/json';
	headers['Content-Type'] = 'application/json';
	
	/*
	 * Click location
	 */
	$(".location_selector").click(function(){
		$.ajax({
			type : "POST",
			headers: headers,
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
//			headers: headers,
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
//			headers: headers,
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