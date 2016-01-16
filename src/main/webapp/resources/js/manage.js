var headers = {};

$(document).ready(function() {
	$("#fade").hide();
	headers[$("meta[name='_csrf_header']").attr("content")] = $("meta[name='_csrf']").attr("content");
	headers['Accept'] = 'application/json';
	headers['Content-Type'] = 'application/json';

	loadLocationsTab();
	
	$(document).on( 'shown.bs.tab', 'a[data-toggle="tab"]', function (e) {
		var target = $(e.target).attr("href")
		if(target === '#locations'){
			loadLocationsTab();
		} else if(target === '#packers'){
			loadPackersTab();
		} else if(target === '#livestock'){
			loadLivestockTab();
		} else if(target === '#feed'){
			loadFeedTab();
		} else if(target === '#suppliers'){
			loadSuppliersTab();
		} else if(target === '#users'){
			loadUsersTab();
		} else if(target === '#soldLivestock'){
			loadSoldLivestockTab();
		}
		
	});
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

function loadLocationsTab(){
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
			$('#locations').empty();
			$('#locations').append($(e));
		}
	});		
}

function saveLocation(){
	$("#fade").hide();
	$("#location_popup").hide();
}

function closeLocationPopup(){
	$("#fade").hide();
	$("#location_popup").hide();
}

function loadFeedTab(){
	$.ajax({
		type : "GET",
		headers: headers,
		url : "/admin/feedTab",
		success : function(e){
			$('#feed').empty();
			$('#feed').append($(e));
		}
	});		
}

function loadLivestockTab(){
	$.ajax({
		type : "GET",
		headers: headers,
		url : "/admin/livestockTab",
		success : function(e){
			$('#livestock').empty();
			$('#livestock').append($(e));
		}
	});		
}

function loadPackersTab(){
	$.ajax({
		type : "GET",
		headers: headers,
		url : "/admin/packersTab",
		success : function(e){
			$('#packers').empty();
			$('#packers').append($(e));
		}
	});		
}

function loadSoldLivestockTab(){
	$.ajax({
		type : "GET",
		headers: headers,
		url : "/admin/soldLivestockTab",
		success : function(e){
			$('#soldLivestock').empty();
			$('#soldLivestock').append($(e));
		}
	});		
}

function loadSuppliersTab(){
	$.ajax({
		type : "GET",
		headers: headers,
		url : "/admin/suppliersTab",
		success : function(e){
			$('#suppliers').empty();
			$('#suppliers').append($(e));
		}
	});		
}

function openSupplierPopup(e,edit){
	var id = $(e).attr("id");
	
	if(edit === true){
		$("#save_location").attr("data-id",id);
		$("#supplierName").val($(e).parent().parent().find(".sName").text());
		$("#supplierLocation").val($(e).parent().parent().find(".sLocation").text());
	} else {
		$("#save_location").attr("data-id",0);
		$("#supplierName").val("");
		$("#supplierLocation").val("");
	}

	$("#fade").show();
	$("#supplier_popup").show();
};

function saveSupplier(){
	$.ajax({
		type : "POST",
		headers: headers,
		url : "/admin/saveSupplier",
		data : JSON.stringify({ 
			id : parseInt($("#save_location").attr("data-id")), 
			name : $("#supplierName").val(),
			location : $("#supplierLocation").val()
		}),
		dataType : 'json',
		contentType: 'application/json',
		success : function(result){
			if(result.success === true){
				loadSuppliersTab();
			} else {
				alert("An error occurred.");
			}
			
		},
		error: function (xhr, ajaxOptions, thrownError) {
			alert(xhr.status);
			alert(thrownError);
		}
	});
	$("#fade").hide();
	$("#supplier_popup").hide();
}

function closeSupplierPopup(){
	$("#fade").hide();
	$("#supplier_popup").hide();
}

function loadUsersTab(){
	$.ajax({
		type : "GET",
		headers: headers,
		url : "/admin/usersTab",
		success : function(e){
			$('#users').empty();
			$('#users').append($(e));
		}
	});		
}
