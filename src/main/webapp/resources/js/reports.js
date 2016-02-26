$(document).ready(function() {
	loadPoundsGainedTab();
	
	$(document).on( 'shown.bs.tab', 'a[data-toggle="tab"]', function (e) {
		var target = $(e.target).attr("href")
		if(target === '#poundsGained'){
			loadPoundsGainedTab();
		} 
	});
	
	google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {
      var data = google.visualization.arrayToDataTable([
        ['Year', 'Sales', 'Expenses'],
        ['2013',  1000,      400],
        ['2014',  1170,      460],
        ['2015',  660,       1120],
        ['2016',  1030,      540]
      ]);

      var options = {
        title: 'Company Performance',
        hAxis: {title: 'Year',  titleTextStyle: {color: '#333'}},
        vAxis: {minValue: 0}
        
      };
      var options_stacked = {
              isStacked: true,
              height: 300,
              legend: {position: 'top', maxLines: 3},
              vAxis: {minValue: 0}
            };

      var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
      chart.draw(data, options_stacked);
    }
    
});

function loadPoundsGainedTab(){
	$.ajax({
		type : "GET",
		headers: headers,
		url : "/admin/poundsGainedTab",
		success : function(e){
			$('#poundsGainedBody').empty();
			$('#poundsGainedBody').append($(e));
		}
	});		
}
