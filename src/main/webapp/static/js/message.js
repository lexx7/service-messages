/**
 * Created by lex on 12.02.2017.
 */

$(function () {

});

Message = {
    dialog: null,
    grid: function () {
        var _self = this;

        var url = $("#dataGrid").data("href");

        var isAdmin = $('#isAdmin').val();

        var colNames = ['От кого', 'Дата - время', 'Тема', 'Действия'];

        var colModel = [
            {name: 'fromUser', index: 'fromUser', sortable: true},
            {name: 'createDate', index: 'createDate', sortable: true},
            {name: 'theme', index: 'theme', sortable: true},
            {name: 'action', index: 'action', sotrable: false}
        ];

        if (isAdmin == 1) {
            colNames.splice(1, 0, 'Кому');
            colModel.splice(1, 0, {name: 'toUser', index: 'toUser', sortable: true});
        }

        $("#messageGrid").jqGrid({
            url: url,
            datatype: "json",
            colNames: colNames,
            colModel: colModel,
            rowNum: 15,
            rowList: [15, 30, 50],
            pager: "#pmessageGrid",
            autowidth: true,
            sortname: "createDate",
            sortorder: "desc",
            altRows: true,
            viewrecords: true,
            caption: "",
            gridComplete: function () {
                var ids = $("#messageGrid").jqGrid("getDataIDs");
                for (var i=0; i < ids.length; i++) {
                    var cl = ids[i];
                    var delAction = "<a class='remove-message' href='message/remove/" + cl +
                        "'><span class='glyphicon glyphicon-trash'></span> Удалить</a>";
                    $("#messageGrid").jqGrid("setRowData", ids[i], {
                        action: delAction
                    });
                }
            },
            onSelectRow: function (id) {_self.viewMessage(id);}
        });
        $("#messageGrid").jqGrid("navGrid", "#pmessageGrid", {edit: false, add: false, del: false});

        // Action remove message
        $(document).on('click', '.remove-message', function () {
            var url = $(this).attr('href');
            $.get(url);
            $('#refresh_messageGrid').trigger('click');

            return false;
        });
    },
    viewMessage: function (id) {
        var _self = this;
        var url = $("#viewMessage").data('href') + id;

        $.ajax({
            dataType: "xml",
            method: "GET",
            url: url,
            success: function (result) {
                var data = $(result).find('div:first').html();

                if (_self.dialog == null) {

                    _self.dialog = $("<div id='messageDialog'></div>").dialog({
                        title: "Сообщение",
                        modal: true,
                        width: "600px",
                        resizable: false
                    });
                }
                _self.dialog.html(data);
                _self.dialog.dialog('open');
            }
        });
    }
};