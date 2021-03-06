<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>List Inbox</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/css/skeleton.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/custom.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/organism.css" rel="stylesheet">

    <jsp:useBean id="userLst" class="java.util.ArrayList" scope="request"/>
    <jsp:useBean id="sendersLst" class="java.util.ArrayList" scope="request"/>
    <jsp:useBean id="auctionsLst" class="java.util.ArrayList" scope="request"/>
</head>
<body>

<c:if test="${empty user}">
    <c:redirect url="/"/>
</c:if>
<%@ include file="../header.jsp" %>

<div class="container">
    <h5>Inbox</h5>
    <c:if test="${not empty messagesLst}">
        <c:forEach var="message" items="${messagesLst}" varStatus="status">
            <a href="${pageContext.request.contextPath}/message.do?action=getConversation&rid=${message.senderId}&aid=${message.auctionId}"
               class="message message--inbox">
                <span class="message__composer">${sendersLst[status.index].firstname} ${sendersLst[status.index].lastname} for ${auctionsLst[status.index].name}:</span>
                <span class="message__text">${message.message}</span><span
                    class="message__time">${message.sendDate}</span>
            </a>
        </c:forEach>
    </c:if>
</div>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>
