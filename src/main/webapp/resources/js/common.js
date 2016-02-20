$( document ).ready(function() {
	$('.datepicker').datepicker();
	
	$('.datepicker').datepicker({
	    format: 'mm/dd/yyyy'
	});
});

function convertUTCDateToLocalDate(date) {
    var newDate = new Date(date.getTime()+date.getTimezoneOffset()*60*1000);

    var offset = date.getTimezoneOffset() / 60;
    var hours = date.getHours();

    newDate.setHours(hours - offset);

    return newDate;   
}