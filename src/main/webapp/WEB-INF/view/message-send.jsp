<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:spring="http://www.springframework.org/tags" version="2.3">

	<form:form class="form-horizontal" method="post"
		modelAttribute="messageForm" action="${message}">


		<form:input path="id" type="hidden" class="form-control" />

		<spring:bind path="userTo">
			<form:input path="userTo" type="hidden" class="form-control" />
		</spring:bind>

		<spring:bind path="userToText">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:label class="control-label col-sm-2" path="userTo">Получатель:</form:label>
				<div class="col-sm-10">
					<form:input path="userToText" type="text" class="form-control" readonly="true" />
					<form:errors class="help-block" path="userToText" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="theme">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:label class="control-label col-sm-2" path="theme">Тема:</form:label>
				<div class="col-sm-10">
					<form:input id="theme" path="theme" type="text"
								class="form-control" placeholder="Введите тему" autofocus="true" />
					<form:errors class="help-block" path="theme" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="message">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<form:label class="control-label col-sm-2" path="message">Сообщение:</form:label>
				<div class="col-sm-10">
					<form:textarea id="message" path="message" type="text" class="form-control"></form:textarea>
					<form:errors class="help-block" path="message" />
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