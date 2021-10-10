<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Meals</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<h1>Meals</h1>

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
            <tr>
                <td><p>${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm')}</p></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                    <%--                    <td><a href="meal?id=${meal.id}&action=delete"></a></td>--%>
                    <%--                    <td><a href="meal?id=${meal.id}}&action=edit"></a></td>--%>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
</body>
</html>
