$(document).ready(function() {
	$("#fade").hide();
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
	var locationId = parseInt($("#save_location").attr("data-id"));
	$.ajax({
		type : "POST",
		headers: headers,
		url : "/admin/saveLocale",
		data : JSON.stringify({ 
			id : locationId, 
			localeName : $("#name").val(),
			enabled : true
		}),
		dataType : 'json',
		contentType: 'application/json',
		success : function(result){
			if(result.success === true){
				var herdIds = $("#new_group_herds").find("option");
				var orphansIds = $("#orphan_group_herds").find("option");
				var newHerds = [], orphanHerds = [];
				var index = 0;
				for(i = 0; i < herdIds.length;i++){
					newHerds[i] = $(herdIds[i]).val();
				}
				for(i = 0; i < orphansIds.length;i++){
					orphanHerds[i] = $(orphansIds[i]).val();
				}
				$.ajax({
					type : "POST",
					headers: headers,
					url : "/admin/saveGroupedHerd",
					data : JSON.stringify({
						localeId : locationId,
						current : newHerds,
						orphanIds : orphanHerds
					}),
					dataType : 'json',
					contentType: 'application/json',
					success : function(result){
						if(result.success === true){
							loadLocationsTab();
						} else {
							alert("An error updating new herds occurred.");
						}

					}
				});
			} else {
				alert("An error saving location information occurred.");
			}

		}
	});
	$("#fade").hide();
	$("#location_popup").hide();
}

function closeLocationPopup(){
	$("#fade").hide();
	$("#location_popup").hide();
}

function openLocationPopup(e,edit){
	var row = $(e).parent().parent();
	var id = row.attr("id");
	var groupId = row.attr('data-group');

	if(edit === true){
		$("#save_location").attr("data-id",id);
		$("#name").val(row.find(".lName").text());
		var ids = row.find('.herdIds').text().trim();
		var labels = row.find('.herdLabels').text().trim();
		$("#new_group_herds option").remove();
		if(!(ids === "")){
			$("#new_group_herds").append($('<option id="current_group" value="'+ids+'">'+labels+'</option>'));
		}
	} else {
		$("#save_location").attr("data-id",0);
		$("#name").val('');
		$("#new_group_herds option").remove();
	}

	$("#fade").show();
	$("#location_popup").show();
};

function enableDisableLocale(id,enable,ele){
	var text = $(ele).parent().parent().find('.lCount').text();
	var count = parseInt(text);

	if(count < 1 || text === ""){
		$.ajax({
			type : "POST",
			headers: headers,
			url : "/admin/enableDisableLocale",
			data : JSON.stringify({ 
				id : id,
				enabled : enable, 			
			}),
			dataType : 'json',
			contentType: 'application/json',
			success : function(result){
				if(result.success === true){
					loadLocationsTab();
				} else {
					alert("An error occurred.");
				}		
			}
		});
	} else {
		alert("Can't disable, there is currently livestock at this location.")
	}
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

function openFeedPopup(e,edit){
	var row = $(e).parent().parent();
	var id = row.attr("id");

	if(edit === true){
		$("#save_feed").attr("data-id",id);
		$("#feedType").val(row.find(".fFeedType").text());
		$("#dmp").val(row.find(".fDMP").text());
	} else {
		$("#save_feed").attr("data-id",0);
		$("#feedType").val("");
		$("#dmp").val("");
	}

	$("#fade").show();
	$("#feed_popup").show();
};

function saveFeed(){
	$.ajax({
		type : "POST",
		headers: headers,
		url : "/admin/saveFeed",
		data : JSON.stringify({ 
			id : parseInt($("#save_feed").attr("data-id")), 
			feedType : $("#feedType").val(),
			driedMatterPercentage : $("#dmp").val(),
			enabled : true
		}),
		dataType : 'json',
		contentType: 'application/json',
		success : function(result){
			if(result.success === true){
				loadFeedTab();
			} else {
				alert("An error occurred.");
			}

		}
	});
	$("#fade").hide();
	$("#feed_popup").hide();
}

function closeFeedPopup(){
	$("#fade").hide();
	$("#feed_popup").hide();
}

function enableDisableFeed(id,enable){
	$.ajax({
		type : "POST",
		headers: headers,
		url : "/admin/enableDisableFeed",
		data : JSON.stringify({ 
			id : id,
			enabled : enable, 			
		}),
		dataType : 'json',
		contentType: 'application/json',
		success : function(result){
			if(result.success === true){
				loadFeedTab();
			} else {
				alert("An error occurred.");
			}		
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

function openBuyLivestockPopup(e,edit){
	var row = $(e).parent().parent();
	var id = row.find(".hId").attr('data-id');

	if(edit === true){
		$("#save_buyLivestock").attr("data-id",id);
		$("#supplier").val(row.find(".hSupplier").attr("data-supplier"));
		$("#quantity").val(row.find(".hQuantity").text());
		$("#weight").val(row.find(".hWeight").text());
		$("#cost").val(row.find(".hCost").text());
		$("#tagNumber").val(row.find(".hTag").text());
		$("#estimatedSaleDate").val(row.find(".hSale").text());
		$("#implantDate").val(row.find(".hImplant").text());
		$("#optiflexDate").val(row.find(".hOptiflex").text());
		$("#herdLabel").val(row.find(".hId").text());
		$("#sex").val(row.find(".hSex").text());
		$("#dead").val(row.find(".hDead").text());
	} else {
		$("#save_buyLivestock").attr("data-id",0);
		$("#quantity").val("");
		$("#weight").val("");
		$("#cost").val("");
		$("#tagNumber").val("");
		$("#estimatedSaleDate").val("");
		$("#implantDate").val("");
		$("#optiflexDate").val("");
		$("#supplier").val(0);
		$("#herdLabel").val("");
		$("#sex").val("Mix");
		$("#dead").val(0);
	}

	$("#fade").show();
	$("#buyLivestock_popup").show();
};

function saveBuyLivestock(){
	$.ajax({
		type : "POST",
		headers: headers,
		url : "/admin/saveBuyLivestock",
		data : JSON.stringify({ 
			id : parseInt($("#save_buyLivestock").attr("data-id")), 
			quantity : parseInt($("#quantity").val()),
			weight : parseFloat($("#weight").val()),
			cost : parseFloat($("#cost").val()),
			tagNumber : $("#tagNumber").val(),
			estimatedSaleDate : Date.parse($("#estimatedSaleDate").val()),
			implantDate : Date.parse($("#implantDate").val()),
			optiflexDate : Date.parse($("#optiflexDate").val()),
			supplier : {
				id : parseInt($("#supplier").val())
			},
			sex : $("#sex").val(),
			herdLabel : $("#herdLabel").val(),
			deadQuantity : parseInt($("#dead").val())
		}),
		dataType : 'json',
		contentType: 'application/json',
		success : function(result){
			if(result.success === true){
				loadLivestockTab();
			} else {
				alert("An error occurred.");
			}

		}
	});
	$("#fade").hide();
	$("#buyLivestock_popup").hide();
}

function closeBuyLivestockPopup(){
	$("#fade").hide();
	$("#buyLivestock_popup").hide();
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

function openPackerPopup(e,edit){
	var id = $(e).attr("id"), row = $(e).parent().parent();
	if(edit === true){
		$("#save_packer").attr("data-id",id);
		$("#packerName").val(row.find(".pName").text());
		$("#packerLocation").val(row.find(".pLocation").text());
	} else {
		$("#save_packer").attr("data-id",0);
		$("#packerName").val("");
		$("#packerLocation").val("");
	}

	$("#fade").show();
	$("#packer_popup").show();
};

function savePacker(){
	$.ajax({
		type : "POST",
		headers: headers,
		url : "/admin/savePacker",
		data : JSON.stringify({ 
			id : parseInt($("#save_packer").attr("data-id")), 
			name : $("#packerName").val(),
			location : $("#packerLocation").val()
		}),
		dataType : 'json',
		contentType: 'application/json',
		success : function(result){
			if(result.success === true){
				loadPackersTab();
			} else {
				alert("An error occurred.");
			}

		}
	});
	$("#fade").hide();
	$("#packer_popup").hide();
}

function closePackerPopup(){
	$("#fade").hide();
	$("#packer_popup").hide();
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

function openSalePopup(e,edit){
	var row = $(e).parent().parent();
	var id = row.attr("id");

	if(edit === true){
		$("#save_sale").attr("data-id",id);
		$("#saPrice").val(row.find(".saPrice").text());
		$("#saWeight").val(row.find(".saWeight").text());
		$("#saDate").val(row.find(".saDate").text());
		$("#saDressing").val(row.find(".saDressing").text());
		$("#saShrink").val(row.find(".saShrink").text());
		$("#saQuantity").val(row.find(".saQuantity").text());
		$("#saPacker").val(row.find(".saPacker").attr("data-packer"));
		$("#saLocale").val(row.find(".saHerds").attr("id"));		
	} else {
		$("#save_sale").attr("data-id",0);
		$("#saPrice").val("");
		$("#saWeight").val("");
		$("#saDate").val("");
		$("#saDressing").val("");
		$("#saShrink").val("");
		$("#saQuantity").val("");
		$("#saPacker").val(0);
		$("#saLocale").val(0);
	}

	$("#fade").show();
	$("#sale_popup").show();
};

function validateSale(){
	$("#sale_popup").hide();
	$("#continue_sale_alert").show();
	$("#validateMessage").removeClass("alert-warning");
	$("#validateMessage").removeClass("alert-danger");
	$.ajax({
		type : "POST",
		headers: headers,
		url : "/admin/isValidSale",
		data : JSON.stringify({ 
			id : parseInt($("#save_sale").attr("data-id")),
			quantity : parseInt($("#saQuantity").val()),
			groupedHerd : { id : parseInt($("#saLocale").val()) }
		}),
		success : function(e){
			if(e.success === true && e.message === null){
				$("#validateSale_popup").hide();
				saveSale();				
			} else if(e.success === true && !(e.message === null)){
				$("#validateMessage").html(e.message);
				$("#validateMessage").addClass("alert-warning");
			} else if(e.success === false && !(e.message === null)){
				$("#validateMessage").html(e.message);
				$("#validateMessage").addClass("alert-danger");
				$("#continue_sale_alert").hide();
			}
			$("#validateSale_popup").show();
		},
		error : function(){
			alert("An error occured");
		}
			
	})
}

function discardSaleAlert(){
	$("#validateSale_popup").hide();
	$("#sale_popup").show();
}

function saveSale(){
	$("#validateSale_popup").hide();
	$.ajax({
		type : "POST",
		headers: headers,
		url : "/admin/saveSoldLivestock",
		data : JSON.stringify({ 
			id : parseInt($("#save_sale").attr("data-id")), 
			packer : { id : parseInt($("#saPacker").val()) },
			quantity : parseInt($("#saQuantity").val()),
			saleWeight : parseFloat($("#saWeight").val()),
			dressingPercent : parseFloat($("#saDressing").val()),
			saleDate : Date.parse($("#saDate").val()),
			salePrice : parseFloat($("#saPrice").val()),
			shrinkPercent : parseFloat($("#saShrink").val()),
			groupedHerd : { id : parseInt($("#saLocale").val()) }
		}),
		dataType : 'json',
		contentType: 'application/json',
		success : function(result){
			if(result.success === true){
				loadSoldLivestockTab();
			} else {
				alert("An error occurred.");
			}

		}
	});
	$("#fade").hide();
	$("#sale_popup").hide();
}

function closeSalePopup(){
	$("#fade").hide();
	$("#sale_popup").hide();
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
	var id = $(e).attr("id"), row = $(e).parent().parent();

	if(edit === true){
		$("#save_supplier").attr("data-id",id);
		$("#supplierName").val(row.find(".sName").text());
		$("#supplierLocation").val(row.find(".sLocation").text());
	} else {
		$("#save_supplier").attr("data-id",0);
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
			id : parseInt($("#save_supplier").attr("data-id")), 
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

function openUserPopup(e,edit){
	var row = $(e).parent().parent();
	var id = row.attr("id");

	if(edit === true){
		$("#save_user").attr("data-id",id);
		$("#username").val(row.find(".uUsername").text());
		$("#username").prop("disabled",true);
		$("#userFirstName").val(row.find(".uName").text().split(" ")[0]);
		$("#userLastName").val(row.find(".uName").text().split(" ")[1]);
		$("#email").val(row.find(".uEmail").text());
		$("#adminRole").prop('checked',row.find(".uRoles").text().indexOf("Admin") > -1 ? true : false);
	} else {
		$("#save_user").attr("data-id",0);
		$("#username").val("");
		$("#userFirstName").val("");
		$("#userLastName").val("");
		$("#email").val("");
		$("#username").prop("disabled",false);
		$("#adminRole").prop('checked', false);
	}

	$("#fade").show();
	$("#user_popup").show();
};

function saveUser(){
	var uRoles =  [ 'Default' ];
	if($("#adminRole").prop("checked") === true){
		uRoles[1] = "Admin";
	}
	$.ajax({
		type : "POST",
		headers: headers,
		url : "/admin/saveUser",
		data : JSON.stringify({ 
			id : parseInt($("#save_user").attr("data-id")), 
			firstName : $("#userFirstName").val(),
			lastName : $("#userLastName").val(),
			email : $("#email").val(),
			username : $("#username").val(),
			roles : uRoles,
			enabled : true,
			password : ''
		}),
		dataType : 'json',
		contentType: 'application/json',
		success : function(result){
			if(result.success === true){
				loadUsersTab();
			} else {
				alert("An error occurred.");
			}

		}
	});
	$("#fade").hide();
	$("#user_popup").hide();
}

function resetPassword(username){	
	$.ajax({
		type : "POST",
		headers: headers,
		url : "/admin/resetPassword",
		data : JSON.stringify({ 
			username : username, 			
		}),
		dataType : 'json',
		contentType: 'application/json',
		success : function(result){
			if(result.success === true){
				loadUsersTab();
			} else {
				alert("An error occurred.");
			}		
		}
	});
}

function enableDisableUser(id,enable){
	$.ajax({
		type : "POST",
		headers: headers,
		url : "/admin/enableDisableUser",
		data : JSON.stringify({ 
			id : id,
			enabled : enable, 			
		}),
		dataType : 'json',
		contentType: 'application/json',
		success : function(result){
			if(result.success === true){
				loadUsersTab();
			} else {
				alert("An error occurred.");
			}		
		}
	});
}

function closeUserPopup(){
	$("#fade").hide();
	$("#user_popup").hide();
}
