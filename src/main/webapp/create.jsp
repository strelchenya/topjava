<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Add new meal</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<c:if test="${param['action'] eq 'add'}">
    <h1>Add new meal</h1>
    <jsp:useBean id="now" class="java.util.Date"/>
    <fmt:formatDate value="${now}" pattern="yyyy-MM-dd'T'HH:mm" var="parseNow"/>
    <c:set var="addDate" value="${parseNow}"/>
    <c:set var="addDescription" value=""/>
    <c:set var="addCalories" value="0"/>
</c:if>
<c:if test="${param['action'] eq 'edit'}">
    <h1>Edit meal</h1>
    <jsp:useBean id="mealEdit" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
    <c:set var="addDate" value="${mealEdit.dateTime}"/>
    <c:set var="addDescription" value="${mealEdit.description}"/>
    <c:set var="addCalories" value="${mealEdit.calories}"/>
</c:if>
<br>
<form method="POST" action='meals' name="addOrEditMael" enctype="application/x-www-form-urlencoded">

    DateTime : <input type="datetime-local" id="datetime"
                      name="datetime" value="${addDate}"
                      min="2000-06-07T00:00" max="2050-06-14T00:00"
                      pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" required>
    <span class="validity"></span></br>

    Description : <input type="text" name="description" value="${addDescription}"
                         placeholder="breakfast, lunch, dinner..." minlength="3" maxlength="20" required>
    <span class="validity"></span></br>

    Calories : <input type="number" name="calories" value="${addCalories}" step="1" min="1" max="2147483647">
    <span class="validity"></span></br>
    <br>
    <input type="hidden" name="id" value="${param['id']}"/>
    <button type="submit">Save</button>
    <input type="button" onclick="history.back();" value="Cancel"/>
</form>
</body>
</html>
