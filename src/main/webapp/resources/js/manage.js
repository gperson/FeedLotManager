$(document).ready(function() {
	$("#fade").hide();

	loadLocations();
	$("#locationsTab").click(function(){loadLocations});
	
});

/*
 * http://www.developergeekresources.com/examples/javascript/javascript-listbox_to_listbox.php
 */
function move(tbFrom, tbTo) 
{
	var arrFrom = new Array(); 
	var arrTo = new Array(); 
	var arrLU = new Array();
	var i;
	for (i = 0; i < tbTo.options.length; i++) 
	{
		arrLU[tbTo.options[i].text] = tbTo.options[i].value;
		arrTo[i] = tbTo.options[i].text;
	}
	var fLength = 0;
	var tLength = arrTo.length;
	for(i = 0; i < tbFrom.options.length; i++) 
	{
		arrLU[tbFrom.options[i].text] = tbFrom.options[i].value;
		if (tbFrom.options[i].selected && tbFrom.options[i].value != "") 
		{
			arrTo[tLength] = tbFrom.options[i].text;
			tLength++;
		}
		else 
		{
			arrFrom[fLength] = tbFrom.options[i].text;
			fLength++;
		}
	}

	tbFrom.length = 0;
	tbTo.length = 0;
	var ii;

	for(ii = 0; ii < arrFrom.length; ii++) 
	{
		var no = new Option();
		no.value = arrLU[arrFrom[ii]];
		no.text = arrFrom[ii];
		tbFrom[ii] = no;
	}

	for(ii = 0; ii < arrTo.length; ii++) 
	{
		var no = new Option();
		no.value = arrLU[arrTo[ii]];
		no.text = arrTo[ii];
		tbTo[ii] = no;
	}
}

function loadLocations(){
	var headers = {};
	headers[$("meta[name='_csrf_header']").attr("content")] = $("meta[name='_csrf']").attr("content");

	/*
	 * Click location
	 */
	$.ajax({
		type : "GET",
		headers: headers,
		url : "/admin/locationsTab",
		success : function(e){
			$('#locations').append($(e));
		}
	});		
}

function openLocationPopup(e,edit){
	//TODO Dont open when edit/delete is clicked

	var id = $(this).attr("id");

	//AJAX LOAD DATA
	$("#fade").show();
	$("#location_popup").show();
};

function saveLocation(){
	$("#fade").hide();
	$("#location_popup").hide();
}

function closeLocationPopup(){
	$("#fade").hide();
	$("#location_popup").hide();
}