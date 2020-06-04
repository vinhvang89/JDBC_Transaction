<%--
  Created by IntelliJ IDEA.
  User: 33vin
  Date: 6/4/2020
  Time: 10:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<center>
    <h1>User Management</h1>
    <h2>
        <a href="users?action=users">List All Users</a>
    </h2>
</center>
<div align="center">
    <form method="post">
        <table border="1" cellpadding="5">
            <tr>
                <th>Username</th>
                <td>
                    <input type="text" placeholder="Enter username" name="name" id="name" >
                </td>
            </tr>
            <tr>
                <th>Username</th>
                <td>
                    <input type="text" placeholder="Enter email" name="email" id="email" >
                </td>
            </tr>
            <tr>
                <th>Username</th>
                <td>
                    <input type="text" placeholder="Enter country" name="country" id="country" >
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" value="Save" >
                </td>
            </tr>

        </table>
    </form>
</div>

</body>
</html>
