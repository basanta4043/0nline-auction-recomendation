<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>404</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/css/skeleton.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/organism.css" rel="stylesheet">
</head>
<body>
<%@ include file="../header.jsp" %>
    <div class="white-box container">
        <div style="text-align: center">
            <img src='${pageContext.request.contextPath}/images/img404.jpg' style="max-width:600px; height:auto" alt='[]' />
        </div>
        <div style="text-align: center">
            <a class="button" style="display: inline-block; margin: auto;" href="${pageContext.request.contextPath}/public/">Go back to homepage</a>
        </div>
    </div>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>
