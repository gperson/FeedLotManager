$(document).ready(function(){
	$(".edit_input").hide();
	
	/*
	 * Click edit feeding
	 */
	$(".edit_feeding").click(function(){
		$(this).find(".edit_input").toggle();
	});
	
});