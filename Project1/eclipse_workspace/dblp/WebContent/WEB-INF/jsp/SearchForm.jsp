<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DBLP Search</title>
</head>
<body>
	<div align="center">
		<form:form action="search" method="post" commandName="searchForm">
			<table border="0">
				<tr>
					<td colspan="2" align="center"><h2>DBLP Search</h2></td>
				</tr>
				<tr>
					<td colspan="2" align="left"><h4>General search</h4></td>
				</tr>
				<tr>
					<td>Keyword:</td>
					<td><form:input path="keyword" /></td>
				</tr>
				<tr>
					<td>Result limit:</td>
					<td><form:select path="limit">
							<form:options items="${limits}" />
						</form:select></td>
				</tr>
				<tr>
					<td colspan="2" align="left"><h4>Specific fields</h4></td>
				</tr>
				<tr>
					<td>Title:</td>
					<td><form:input path="title" /></td>
				</tr>
				<tr>
					<td>Author:</td>
					<td><form:input path="author" /></td>
				</tr>
				<tr>
					<td>Type:</td>
					<td><form:input path="type" /></td>
				</tr>
				<tr>
					<td>Journal:</td>
					<td><form:input path="journal" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Search" /></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>