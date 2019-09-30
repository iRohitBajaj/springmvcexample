<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
	<title>Spring MVC Hello World</title>
</head>

<body>
	<h2>All properties for this app</h2>

	<table border="1">
	<tr>
    			<th>Properties</th>
    		</tr>
	<c:forEach items="${properties}" var="prop">
            			<tr>
            				<td><c:out value="${prop}" /></td>
            			</tr>
            		</c:forEach>
	</table

</body>
</html>