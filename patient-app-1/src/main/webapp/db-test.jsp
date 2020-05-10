<%--
  Created by IntelliJ IDEA.
  User: nicklittlefield
  Date: 5/9/20
  Time: 9:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
    <br>
    <h3>Database Connection Test</h3>

    <c:choose>
        <c:when test="${connected}">
            <p>Connection to the database was successful.</p>
        </c:when>
        <c:otherwise>
            <p>Could not connect to the database. Check the database url and parameters.</p>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${dev}">
            <p>The connection is made to: <br/> <code>${dbUrl}</code></p>
        </c:when>
    </c:choose>

    <p>Executed: <code>${query}</code></p>
    <p>Result: <code>${queryResult}</code></p>
</div>