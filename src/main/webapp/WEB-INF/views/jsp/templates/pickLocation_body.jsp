<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="content">
	<h2 class="home_title center_row">Feeding Location</h2>
	<div class="center_row col-md-12">
		<c:forEach var="groupedHerd" items="${groupedHerds}">
    		<div data-group="${groupedHerd.id}" class="well_selector location_selector well well-sm">
				<label><c:out value="${groupedHerd.locale.localeName}"/></label>
				<span>
					<c:forEach var="herd" items="${groupedHerd.herds}" varStatus="status">
			    		<c:out value="${herd.herdLabel}"/><c:if test="${!status.last}">,</c:if>	         					    		 
					</c:forEach>
				</span>
			</div> 
		</c:forEach>
	</div>
</div>