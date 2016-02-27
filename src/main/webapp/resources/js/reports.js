$(document).ready(function() {
	loadPoundsGainedTab();
	
	$(document).on( 'shown.bs.tab', 'a[data-toggle="tab"]', function (e) {
		var target = $(e.target).attr("href")
		if(target === '#poundsGained'){
			loadPoundsGainedTab();
		} 
	});
	
	google.charts.load('current', {'packages':['corechart']});
});

function loadPoundsGainedTab(){
	$.ajax({
		type : "GET",
		headers: headers,
		url : "/admin/poundsGainedTab",
		success : function(e){
			$('#poundsGainedBody').empty();
			$('#poundsGainedBody').append($(e));
			veiwFeedingHistory(parseInt($($("#gainedTable tbody tr")[0]).attr('id')));
		}
	});		
}

function veiwFeedingHistory(id){ 
    $.ajax({
		type : "GET",
		headers: headers,
		url : "/admin/feedings/"+id,
		success : function(res){
			var his = res.object;

			var dataRows = [];
			var headers = ['Date'];
			for(var i = 0; i < his.feedAmounts.length; i++){
				headers.push(his.feedAmounts[i].type);
			}
			dataRows[0] = headers;
			
			for(var j = 0; j < his.feedAmounts[0].amounts.length; j++){
				var row = [];
				row.push(his.dateLabels[j]);
				for(var i = 0; i < his.feedAmounts.length; i++){
					row.push(his.feedAmounts[i].amounts[j]);
				}
				dataRows.push(row);
			}
   
			google.charts.setOnLoadCallback(drawChart(dataRows));
			
			
			//create trigger to resizeEnd event     
			$(window).resize(function() {
			    if(this.resizeTO) clearTimeout(this.resizeTO);
			    this.resizeTO = setTimeout(function() {
			        $(this).trigger('resizeEnd');
			    }, 500);
			});

			//redraw graph when window resize is completed  
			$(window).on('resizeEnd', function() {
			    drawChart(dataRows);
			});
		}
	});	
}

function drawChart(dataRows) {
    var data = google.visualization.arrayToDataTable(dataRows);
    var options_stacked = {
		title: 'Feeding History - Dried Matter Poundage',
		isStacked: true,
		legend: {position: 'bottom' },
		vAxis: {minValue: 0},
		width: ($('#graph').width()+100),
		height : ($('#graph').width()*.5)
    };

    var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
    chart.draw(data, options_stacked);
}
