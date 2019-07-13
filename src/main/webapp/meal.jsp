<%--
  Created by IntelliJ IDEA.
  User: Zixit
  Date: 13.07.2019
  Time: 1:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <td>Time</td>
        <td>Description</td>
        <td>Calories</td>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="meal" items="${requestScope.get('MealTo')}">
        <tr style="background-color:${meal.isExcess() ? 'red' : 'blue'}">
            <td>${meal.getDescription()}</td>
            <td>${meal.getDate().toString()}</td>
            <td>${meal.getCalories()}</td>
        </tr>
        <%--<p>${num}</p>--%>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
