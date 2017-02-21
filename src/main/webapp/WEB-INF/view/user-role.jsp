<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c = "http://java.sun.com/jsp/jstl/core" version="2.3">

		<form:form class="form-horizontal" method="post" modelAttribute="userRoleForm">

			<form:input path="id" type="hidden" class="form-control" readonly="true" />

			<spring:bind path="admin">
				<div class="${status.error ? 'has-error' : ''}">
					<form:label class="control-label col-sm-3" path="admin">Администратор:</form:label>
					<form:checkbox id="admin" path="admin" class="col-sm-8" style="margin-left: 15px; margin-top: 11px;" />
				</div>
			</spring:bind>

			<div>
				<div class="col-sm-10" style="margin-top: 14px;">
					<c:if test="${successReplace}">
						<span class="label label-success"><strong>Права удачно изменёны.</strong></span>
						<script type="application/javascript">
                            $(function() { window.setTimeout(function () {
                                $('#refresh_userGrid').trigger('click');
                                $('#userRoleDialog').dialog('close');}, 1000);
                            });
						</script>
					</c:if>
				</div>
				<div class="col-sm-2" style="margin-top: 10px;">
					<input class="btn btn-default" type="submit" value="Изменить" />
				</div>
			</div>

		</form:form>

</jsp:root>
