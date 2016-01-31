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
	$(element).parent().parent().parent().hide();
	$(this).addClass("glyphicon-triangle-right");
	$(this).removeClass("glyphicon-triangle-bottom");
}

function saveEdit(element){
	$(element).parent().parent().parent().hide();
	$(this).addClass("glyphicon-triangle-right");
	$(this).removeClass("glyphicon-triangle-bottom");
}