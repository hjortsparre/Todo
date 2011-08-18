
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Todo List</title>
</head>
<body>

<a href="LogoutServlet">Logout</a>
<br />
<br />
<c:out value="${message}" />
<h1>Todo Items</h1>
<form action="CreateTodoServlet" method="post"><label>Name:
</label><input type="text" name="name" />
<button type="submit">Create</button>
</form>
<h2>Checked Out</h2>
<table>
	<c:forEach items="${todos}" var="todo">
		<c:if test="${todo.checkedOutBy != null}">
			<tr>
				<td>${todo.id}</td>
				<td>${todo.name}</td>
				<td><a href="CheckInServlet?todoId=${todo.id}">Check In</a></td>
				<td>${todo.checkedOutBy.email}</td>
			</tr>
		</c:if>
	</c:forEach>
</table>

<h2>Checked In</h2>
<table>
	<c:forEach items="${todos}" var="todo">
		<c:if test="${todo.checkedOutBy == null}">
			<tr>
				<td>${todo.id}</td>
				<td>${todo.name}</td>
				<td><a href="CheckOutServlet?todoId=${todo.id}">Check Out</a></td>
			</tr>
		</c:if>
	</c:forEach>
</table>

</body>
</html>