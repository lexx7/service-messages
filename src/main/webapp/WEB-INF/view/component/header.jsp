<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root
		xmlns:jsp="http://java.sun.com/JSP/Page"
		xmlns:sec="http://www.springframework.org/security/tags"
		xmlns:spring="http://www.springframework.org/tags"
		xmlns:form="http://www.springframework.org/tags/form"
		xmlns:c="http://java.sun.com/jsp/jstl/core"
		xmlns:tiles="http://tiles.apache.org/tags-tiles"
		version="2.3">
	
	<tiles:importAttribute name="headerData"/>

	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="nav navbar-header">
				<div class="navbar-brand"><a href="${pageContext.request.contextPath}/">Service Messages</a></div>
			</div>
			<ul class="nav navbar-nav navbar-left">
				<sec:authorize access="isAuthenticated()">
					<li><a href="${pageContext.request.contextPath}/address-book">Адресная книга</a></li>
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<li><a href="${pageContext.request.contextPath}/user/control">Управление пользователями</a></li>
					</sec:authorize>
				</sec:authorize>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<sec:authorize access="!isAuthenticated()">
					<li><a href="${pageContext.request.contextPath}/registration"><span class="glyphicon glyphicon-user"></span> Регистрация</a></li>
					<li><a href="${pageContext.request.contextPath}/login"><span class="glyphicon glyphicon-log-in"></span> Вход</a></li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<li><a href="${pageContext.request.contextPath}/replace-password/${headerData.currentUserId}" id="replace-password-user">Сменить пароль</a> </li>
					<li><a><span class="glyphicon glyphicon-user"/>&#160;<sec:authentication property="principal.username"/></a></li>
					<li>
						<form:form action="${pageContext.request.contextPath}/logout">
							<button class="btn btn-link navbar-btn" type="submit"><span class="glyphicon glyphicon-log-out"/> Выход</button>
						</form:form>
					</li>
				</sec:authorize>
			</ul>
		</div>
	</nav>


</jsp:root>