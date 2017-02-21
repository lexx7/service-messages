<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:form="http://www.springframework.org/tags/form"
	xmlns:spring="http://www.springframework.org/tags" version="2.3">

		<div class="form-group">
			<label class="control-label col-sm-3 form-control-static">Отправитель:</label>
			<div class="col-sm-9 form-control-static">
				${messageForm.userTo}
			</div>
		</div>

		<div class="form-group">
			<label class="control-label col-sm-3 form-control-static">Дата - время:</label>
			<div class="col-sm-9 form-control-static">
				${createDate}
			</div>
		</div>

		<div class="form-group">
			<label class="control-label col-sm-3 form-control-static">Тема:</label>
			<div class="col-sm-9 form-control-static">
				${messageForm.theme}
			</div>
		</div>

		<div class="form-group">
			<label class="control-label col-sm-2">Сообщение:</label>
			<div class="col-sm-12">
				<textarea id="message" path="message" type="text" style="height:120px;"
						  class="form-control">${messageForm.message}</textarea>
			</div>
		</div>

</jsp:root>