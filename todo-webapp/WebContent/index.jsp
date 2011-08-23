
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Todo Register/Login Page</title>
</head>
<body>

<c:if test="${message != null}">
${message}
</c:if>


<h1>Please register</h1>
<form method="post" action="RegisterServlet"><label>Email: </label><input
	type="text" name="email" /><br />
<label>Password: </label><input type="password" name="password" /><br />
<button type="submit">Register</button>
</form>

<h1>Please Login</h1>
<form method="post" action="LoginServlet"><label>Email: </label><input
	type="text" name="email" /><br />
<label>Password: </label><input type="password" name="password" /><br />
<button type="submit">Login</button>
</form>

</body>
</html>
