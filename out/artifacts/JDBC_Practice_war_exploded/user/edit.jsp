<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 33vin
  Date: 6/4/2020
  Time: 11:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Management Application</title>
</head>
<body>
<h1>User Management</h1>
<h2>
    <a href="users?action=users">List All Users</a>
</h2>
</center>
<div align="center">
    <form method="post" action="users">
        <table border="1" cellpadding="5">
        <caption>
            <h2>
                Edit User
            </h2>
        </caption>
        <c:if test="${user!=null}">
            <input type="hidden" name="id" value='<c:out value="${user.id}"/>' />
        </c:if>
        <tr>
            <th>User Name</th>
            <td>
                <input type="text" name="name" value='<c:out value="${user.name}"/>'/>
            </td>
        </tr>
        <tr>
            <th>Email</th>
            <td>
                <input type="text" name="email" value='<c:out value="${user.email}"/>'/>
            </td>
        </tr>
        <tr>
            <th>Country</th>
            <td>
                <input type="text" name="country" value='<c:out value="${user.country}"/>'/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Save"/>
            </td>
        </tr>
        </table>
    </form>
</div>
</body>
</html>
