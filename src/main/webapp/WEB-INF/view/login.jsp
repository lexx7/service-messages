<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c = "http://java.sun.com/jsp/jstl/core" 
	xmlns:sec="http://www.springframework.org/security/tags" version="2.3">

	<form:form class="form-horizontal" method="post" action="login">

		<div class="form-group">
			<label class="control-label col-sm-2" for="username">Логин:</label>
			<div class="col-sm-10">
				<input id="username" name="username" class="form-control"
					placeholder="Введите логин" autofocus="autofocus" />
			</div>
		</div>

		<div class="form-group">
			<label class="control-label col-sm-2" for="password">Пароль:</label>
			<div class="col-sm-10">
				<input id="password" name="password" type="password"
					class="form-control" placeholder="Введите пароль" />
			</div>
		</div>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<input class="btn btn-default" type="submit" value="Войти" />
			</div>
		</div>
		
		<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<span class="label label-danger"><strong>Не удалось выполнить вход.</strong> Причина: <c:out
							value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />. </span>
				</div>
			</div>
		</c:if>

	</form:form>

</jsp:root>