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
<section>
    <table border="2" cellpadding="8" cellspacing="0">
        <tbody>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <jsp:useBean id="meals" scope="request" type="java.util.List"/>
        <c:forEach var="meal" items="${meals}">
            <c:set var="color" value="${meal.excess ? 'color:#ce0d0d':'color:#196c25'}"/>
            <tr style="${color}">
                <td><div>${f:formatLocalDateTime(meal.dateTime, 'yyyy-MM-dd HH:mm')}</div></td>
                <td>
                    <div>${meal.description}</div>
                </td>
                <td>
                    <div>${meal.calories}</div>
                </td>
                <td><a href="meals?id=${meal.id}&action=edit">Edit</a></td>
                <td><a href="meals?id=${meal.id}&action=delete">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <br>
    <div style='text-align:left;'><a class="a_1" href="meals?action=add">Add meal</a></div>
</section>
</body>
</html>
