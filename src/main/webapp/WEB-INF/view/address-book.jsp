<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.3">

	<link href="${pageContext.request.contextPath}/webjars/free-jqgrid/4.13.6/css/ui.jqgrid.min.css" rel="stylesheet" />

	<script type="application/javascript" src="${pageContext.request.contextPath}/webjars/free-jqgrid/4.13.6/js/jquery.jqgrid.min.js" />
	<script type="application/javascript" src="${pageContext.request.contextPath}/js/address-book.js" />

	<button id="create-address" class="btn btn-primary"
			data-href="${pageContext.request.contextPath}/address-book/create">Добавить пользователя</button>

    <div id="dataGrid" data-href="${pageContext.request.contextPath}/address-book/list" style="margin-top: 15px">
		<table id="addressBookGrid"></table>
		<div id="paddressBookGrid"></div>
	</div>

</jsp:root>