<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="content">
	<h2 class="home_title center_row">Feeding Location</h2>
	<div class="center_row col-md-12">
		<c:forEach var="locale" items="${farm.locales}">
    		<div data-locale="${locale.localeId}" class="location_selector well well-sm">
				<label><c:out value="${locale.localeName}"/></label>
				<span>
					<c:forEach var="herd" items="${locale.herdIds}" varStatus="status">
			    		<c:out value="${herd}"/><c:if test="${!status.last}">,</c:if>	         					    		 
					</c:forEach>
				</span>
			</div> 
		</c:forEach>
	</div>
</div>