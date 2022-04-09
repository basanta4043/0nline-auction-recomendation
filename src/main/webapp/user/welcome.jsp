<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Welcome Page</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/css/skeleton.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/organism.css" rel="stylesheet">
</head>
<body>
<%@ include file="../header.jsp" %>
<div class="container">
    <h1>Wait for your approval.</h1>
    <h4>Your registration was applied successfully.</h4>
    <p>Please note that your account will be active when a moderator accept your registration.</p>
    <a class="button button-primary" href="${pageContext.request.contextPath}/">Start Page</a>
</div>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>

    