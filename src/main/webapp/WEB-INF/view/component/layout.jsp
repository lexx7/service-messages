<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns="http://www.w3.org/1999/xhtml"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:tiles="http://tiles.apache.org/tags-tiles"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:sec="http://www.springframework.org/security/tags" version="2.3">

	<jsp:directive.page language="java" contentType="application/xhtml+xml"
		pageEncoding="utf-8" />
	<jsp:output doctype-root-element="html"
		doctype-public="-//W3C//DTD XHTML 1.1//EN"
		doctype-system="http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd"
		omit-xml-declaration="true" />

	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="application/xhtml+xml" charset="UTF-8"/>
			
	<sec:csrfMetaTags />

	<!-- CSS resources -->
	<link href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"
		rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/webjars/jquery-ui/1.12.1/jquery-ui.min.css" rel="stylesheet" />

	<!-- JS resources -->
	<script type="application/javascript"
		src="${pageContext.request.contextPath}/webjars/jquery/3.1.1-1/jquery.min.js" />
	<script type="application/javascript"
			src="${pageContext.request.contextPath}/webjars/jquery-ui/1.12.1/jquery-ui.min.js" />
	<script type="application/javascript"
		src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js" />
	<script type="application/javascript"
			src="${pageContext.request.contextPath}/webjars/jquery-form/3.51/jquery.form.js" />

	<script type="application/javascript" src="${pageContext.request.contextPath}/js/main.js" />
	
	<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body>
	<tiles:insertAttribute name="header" />
	<div id="layout-content" class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<tiles:insertAttribute name="title" ignore="true" />
			</div>
			<div class="panel-body">
				<tiles:insertAttribute name="content" />
			</div>
		</div>
	</div>
	<tiles:insertAttribute name="footer" />

</body>
	</html>
</jsp:root>