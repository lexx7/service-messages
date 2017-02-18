<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
		  xmlns:sec="http://www.springframework.org/security/tags"
		  version="2.3">

	<link href="${pageContext.request.contextPath}/webjars/free-jqgrid/4.13.6/css/ui.jqgrid.min.css" rel="stylesheet" />

	<script type="application/javascript" src="${pageContext.request.contextPath}/webjars/free-jqgrid/4.13.6/js/jquery.jqgrid.min.js" />
	<script type="application/javascript" src="${pageContext.request.contextPath}/js/message.js" />

	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<input type="hidden" id="isAdmin" value="1" />
	</sec:authorize>

	<script type="application/javascript">
		$(function () {
            Message.grid();
        });
	</script>

	<div id="viewMessage" data-href="${pageContext.request.contextPath}/message/"></div>

	<div id="dataGrid" data-href="${pageContext.request.contextPath}/message/list">
		<table id="messageGrid"></table>
		<div id="pmessageGrid"></div>
	</div>

</jsp:root>