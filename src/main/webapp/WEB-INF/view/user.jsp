<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:spring="http://www.springframework.org/tags" version="2.3">

	<form:form class="form-horizontal" method="post" modelAttribute="userForm">

		<div class="form-group">
			<form:label class="control-label col-sm-2" path="id">ID:</form:label>
			<div class="col-sm-10">
				<form:input path="id" type="text" class="form-control"
					readonly="true" />
			</div>
		</div>

		<spring:bind path="login">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:label class="control-label col-sm-2" path="login">Логин:</form:label>
				<div class="col-sm-10">
					<form:input path="login" type="text" class="form-control"
						placeholder="Введите логин" autofocus="autofocus" />
					<form:errors class="help-block" path="login" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="password">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:label class="control-label col-sm-2" path="password">Пароль:</form:label>
				<div class="col-sm-10">
					<form:input id="password" path="password" type="password"
								class="form-control" placeholder="Введите пароль" />
					<form:errors class="help-block" path="password" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="lastName">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:label class="control-label col-sm-2" path="lastName">Фамилия:</form:label>
				<div class="col-sm-10">
					<form:input id="lastName" path="lastName" type="text"
						class="form-control" placeholder="Введите фамилию" />
					<form:errors class="help-block" path="lastName" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="firstName">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:label class="control-label col-sm-2" path="firstName">Имя:</form:label>
				<div class="col-sm-10">
					<form:input path="firstName" type="text" class="form-control"
								placeholder="Введите имя" />
					<form:errors class="help-block" path="firstName" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="middleName">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:label class="control-label col-sm-2" path="middleName">Отчество:</form:label>
				<div class="col-sm-10">
					<form:input id="middleName" path="middleName" type="text"
								class="form-control" placeholder="Введите отчество" />
					<form:errors class="help-block" path="middleName" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="email">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:label class="control-label col-sm-2" path="email">Email:</form:label>
				<div class="col-sm-10">
					<form:input id="email" path="email" type="text"
								class="form-control" placeholder="Введите email" />
					<form:errors class="help-block" path="email" />
				</div>
			</div>
		</spring:bind>

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<input class="btn btn-default" type="submit" value="Submit" />
				<input class="btn btn-default" type="reset" value="Reset" />
			</div>
		</div>

	</form:form>

</jsp:root>