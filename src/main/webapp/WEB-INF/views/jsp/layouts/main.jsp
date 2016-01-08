<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title><tiles:getAsString name="title"/></title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">    
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