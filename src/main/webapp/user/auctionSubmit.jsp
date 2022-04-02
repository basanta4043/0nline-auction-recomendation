<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- <!DOCTYPE html> -->
<html>
<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
    <title>Auction Submitted</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/css/skeleton.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/organism.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
</head>
<body>

<%@ include file="../header.jsp" %>

<div class="container">
    <h1>Your auction submitted successfully.</h1>
    <p>Thanks for your auction!</p>

    <h4>Please, navigate to one of the following pages.</h4>
    <p>
        <a class="button button-primary" href=${pageContext.request.contextPath}/auction.do?action=getAnAuction&aid=${aid}>View/Edit Submitted Auction</a>
        <a class="button" href="${pageContext.request.contextPath}/user/homepage.jsp">Homepage</a>
    </p>
</div>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>
