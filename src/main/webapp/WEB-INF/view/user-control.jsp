<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.3">

	<link href="${pageContext.request.contextPath}/webjars/free-jqgrid/4.13.6/css/ui.jqgrid.min.css" rel="stylesheet" />

	<script type="application/javascript" src="${pageContext.request.contextPath}/webjars/free-jqgrid/4.13.6/js/jquery.jqgrid.min.js" />
	<script type="application/javascript" src="${pageContext.request.contextPath}/js/user.js" />

	<a href="${pageContext.request.contextPath}/user"><button class="btn btn-primary">Добавить пользователя</button></a>

	<div id="viewUser" data-href="${pageContext.request.contextPath}/user/"></div>

	<div id="dataGrid" data-href="${pageContext.request.contextPath}/user/list" style="margin-top: 14px;">
		<table id="userGrid"></table>
		<div id="puserGrid"></div>
	</div>

</jsp:root>