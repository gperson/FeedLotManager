$( document ).ready(function() {
	var headers = {};
	headers[$("meta[name='_csrf_header']").attr("content")] = $("meta[name='_csrf']").attr("content");
	headers['Accept'] = 'application/json';
	headers['Content-Type'] = 'application/json';
});