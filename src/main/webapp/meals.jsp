<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Meals</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>Meals</h1>
<br>
<p><a href="meals?action=insert">Add meal</a></p>
<section>
    <table border="2" cellpadding="8" cellspacing="0" style="margin: auto">
        <tbody>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Update</th>
            <th>Delete</th>
        </tr>
        <jsp:useBean id="meals" scope="request" type="java.util.List"/>
        <c:forEach var="meal" items="${meals}">
            <c:if test="${!meal.excess}">
                <c:set var="color" value="color:#196c25"/>
            </c:if>
            <c:if test="${meal.excess}">
                <c:set var="color" value="color:#ce0d0d"/>
            </c:if>
            <tr>
                <td><p style="${color}">${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm')}</p></td>
                <td><div style="${color}"><c:out value="${meal.description}"/></div></td>
                <td><div style="${color}"><c:out value="${meal.calories}"/></div></td>
                <td><a href="meals?id=${meal.id}&action=edit">Edit</a></td>
                <td><a href="meals?id=${meal.id}&action=delete">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
</body>
</html>
