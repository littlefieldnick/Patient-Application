
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<br/>
	<h3>Patients</h3>
	<br>

	<c:choose>
		<c:when test="${empty patients}">
			<p>No patients found</p>
		</c:when>
		<c:otherwise>
			<c:forEach items="${patients}" var="patient">
				<div class="media">
						<div class="media-body">
							<h4>${fn:escapeXml(patient.firstName)}
								${fn:escapeXml(patient.lastName)}</h4>
							<p>${fn:escapeXml(patient.birthDate)}</p>
						</div>
				</div>
			</c:forEach>

			<c:if test="${not empty cursor}">
				<nav>
					<ul class="pager">
						<li><a href="?cursor=${fn:escapeXml(cursor)}">More</a></li>
					</ul>
				</nav>
			</c:if>
		</c:otherwise>
	</c:choose>
</div>

