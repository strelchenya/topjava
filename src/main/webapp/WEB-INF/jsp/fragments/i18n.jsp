<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<script type="text/javascript">
    <c:set var="currentPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>
    <c:set var="isMealsUri" value="${fn:endsWith(currentPage, '/meals')}"/>

    const i18n = [];

    i18n["addTitle"] = ${isMealsUri} ? '<spring:message code="meal.add"/>' : '<spring:message code="user.add"/>';
    i18n["editTitle"] = ${isMealsUri} ? '<spring:message code="meal.edit"/>' : '<spring:message code="user.edit"/>';

    <c:set var="typei18n" value="prerna" />
    <c:forEach var="key"
               items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","common.confirm"}%>'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
    </c:forEach>
</script>