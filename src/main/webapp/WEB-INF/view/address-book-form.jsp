<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c = "http://java.sun.com/jsp/jstl/core" version="2.3">


		<form:form class="form-horizontal" method="post" modelAttribute="addressBookForm">

			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

			<spring:bind path="userTo">
				<div class="${status.error ? 'has-error' : ''}">
					<form:label class="control-label col-sm-3" path="userTo">Пользователь:</form:label>
					<div class="col-sm-9">
						<form:select path="userTo" type="text" class="form-control" items="${addressBookFormData.users}"/>
						<form:errors class="help-block" path="userTo" />
					</div>
				</div>
			</spring:bind>

			<div class="">
				<div class="col-sm-10" style="margin-top: 14px;">
					<c:if test="${successAdd}" >
						<span class="label label-success"><strong>Пользователь удачно добавлен.</strong></span>
						<script type="application/javascript">
							$(function() { window.setTimeout(function () {
							    $('#refresh_addressBookGrid').trigger('click');
								$('#addressBookDialog').dialog('close');
							}, 1000);});
						</script>
					</c:if>
				</div>
				<div class="col-sm-2" style="margin-top: 10px;">
					<input class="btn btn-default" type="submit" value="Submit" />
				</div>
			</div>

		</form:form>

</jsp:root>