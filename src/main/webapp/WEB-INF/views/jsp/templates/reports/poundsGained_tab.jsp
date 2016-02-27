<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="row">
	<h3>Feeding Data</h3>
	<div id="gainedTable" class="col-sm-4">
		<table class="table table-bordered table-condensed table-striped">
			<thead>
				<tr>
					<th>Herds</th>
					<th>Gained (lbs)/Dried Food (lbs)</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="gain" items="${gains}">
		    		<tr id="${gain.groupedHerdId}">
						<td>${gain.herdsLabels}</td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${gain.pGpD}" /></td>
						<td>
							<button onclick="veiwFeedingHistory(${gain.groupedHerdId});" type="button" class="btn btn-info btn-xs">History</button>					
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="graph" class="col-sm-8">
		<div id="chart_div"></div>
	</div>
</div>
<div class="row">
	<h3>Sales Data</h3>
	<table class="table table-bordered table-condensed table-striped">
			<thead>
				<tr>
					<th>Herds</th>
					<th>Start Count</th>
					<th>End Count</th>
					<th>Start Weight</th>
					<th>End Weight</th>
					<th>Weight Diff.</th>
					<th>Purchase Price</th>
					<th>Total Sales Amount</th>
					<th>Price Diff.</th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach var="overview" items="${overviews}">
		    		<tr>
						<td>${overview.herdsLabels}</td>
						<td>${overview.startCount}</td>
						<td>${overview.endCount}</td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${overview.startWeight}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${overview.endWeight}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${overview.endWeight - overview.startWeight}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${overview.purchasePrice}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${overview.salesAmount}" /></td>
						<td><fmt:formatNumber type="number" maxFractionDigits="3" value="${overview.salesAmount - overview.purchasePrice}" /></td>						
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>