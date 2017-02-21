<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c = "http://java.sun.com/jsp/jstl/core" version="2.3">

	<form:form class="form-horizontal" method="post"
			   modelAttribute="replacePasswordUserForm" action="${userReplacePassword}">

		<form:input id="id" path="id" type="hidden" />

		<spring:bind path="password">
			<div class="${status.error ? 'has-error' : ''}">
				<form:label class="control-label col-sm-3" path="password">Новый пароль:</form:label>
				<div class="col-sm-9">
					<form:input id="password" path="password" type="text"
								   class="form-control" placeholder="Введите новый пароль" autofocus="true" />
					<form:errors class="help-block" path="password" />
				</div>
			</div>
		</spring:bind>

		<div>
			<div class="col-sm-10" style="margin-top: 14px;">
				<c:if test="${successReplace}">
					<span class="label label-success"><strong>Пароль удачно изменён.</strong></span>
					<script type="application/javascript">
                        $(function() { window.setTimeout(function () {$('#mainDialog').dialog('close');}, 2000);});
					</script>
				</c:if>
			</div>
			<div class="col-sm-2" style="margin-top: 10px;">
				<input class="btn btn-default" type="submit" value="Изменить" />
			</div>
		</div>


	</form:form>

</jsp:root>
