<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title><tiles:getAsString name="title"/></title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" integrity="sha256-7s5uDGW3AHqw6xtJmNNtr+OBRJUlgkNJEo78P4b0yRw= sha512-nNo+yCHEyn0smMxSswnf/OnX6/KwJuZTlNZBjauKhTK0c+zT+q5JOCx0UFhXQ6rJR9jg6Es8gPuD2uZcYDLqSw==" crossorigin="anonymous" type="text/css">       
	<link href="/resources/css/common.css" rel="stylesheet" type="text/css">       
  	<link rel="stylesheet" type="text/css" href="<tiles:getAsString name='specialCss'/>">
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="/resources/js/common.js"></script>
    <script src="<tiles:getAsString name='specialJs'/>"	type="text/javascript"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha256-KXn5puMvxCw+dAYznun+drMdG1IFl3agK0p/pqT9KAo= sha512-2e8qq0ETcfWRI4HJBzQiA3UoyFk6tbNyG+qSaIBZLyW9Xf3sWZHN/lxe9fTh1U45DpPf07yj94KsUHHWe4Yk1A==" crossorigin="anonymous"></script> 
  </head>
  <body>
    <div class="container">
        <div class="row">
            <div class="col-md-12" id="header">
                <tiles:insertAttribute name="header" />
            </div>
        </div>
        <div class="row">
            <div id="body">
                <tiles:insertAttribute name="body" />
            </div>
        </div>
        <div class="row">
        	<tiles:insertAttribute name="footer" />
        </div>
    </div>
  </body>
</html>