$( document ).ready(function() {
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
				console.log(window.location.href = window.location.protocol + "// "+ window.location.hostname + "/mixFeed");
				window.location.href = window.location.protocol + "//"+ window.location.host + "/mixFeed";
			}
		});
		
	})
});