<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Result</title>
</head>
<body>
	<c:set var="keyword" value="${searchForm.keyword}" />
	<c:set var="title" value="${searchForm.title}" />
	<c:set var="author" value="${searchForm.author}" />
	<c:set var="type" value="${searchForm.type}" />
	<c:set var="journal" value="${searchForm.journal}" />
	<table border="1" align="center" style="width: 80%" cellpadding="4">
		<thead>
			<tr>
				<td colspan="5" align="center"><h2>DBLP Search Result(s)</h2></td>
			</tr>
			<tr>
				<td colspan="5" align="left">
						Search Query:
						<c:if test="${keyword!=''}"> Keyword=${keyword}</c:if>
						<c:if test="${title!=''}"> Title=${title}</c:if>
						<c:if test="${author!=''}"> Author=${author}</c:if>
						<c:if test="${type!=''}"> Type=${type}</c:if>
						<c:if test="${journal!=''}"> Journal=${journal}</c:if>
					</td>
			</tr>
			<tr>
				<th>Rank</th>
				<th>Title</th>
				<th>Author</th>
				<th>Type</th>
				<th>Journal</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="count" value="0" />
			<c:forEach var="hit" items="${hitList}">
				<c:set var="source" value="${hit.source}" />
				<c:set var="count" value="${count + 1}" />
				<tr>
					<td>${count}</td>
					<td>${source.title}</td>
					<td>${source.author}</td>
					<td>${source.type}</td>
					<td>${source.journal}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>