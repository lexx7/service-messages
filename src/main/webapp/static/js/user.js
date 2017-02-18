$(function () {
     User.grid();
     User.actions();
});

User = {
    dialog: null,
    actions: function () {
        var _self = this;

        $(document).on('click', '.remove-user', function () {
            var url = $(this).attr('href');
            $.get(url);
            $('#refresh_userGrid').trigger('click');

            return false;
        });

        $(document).on('click', '.edit-role', function () {
            var url = $(this).attr("href");
            $.ajax({
                dataType: "xml",
                method: "GET",
                url: url,
                success: function (result) {
                    var data = $(result).find('div:first').html();

                    if (_self.dialog == null) {
                        _self.dialog = $("<div id='userRoleDialog'></div>").dialog({
                            title: "Изменить права пользователя",
                            modal: true,
                            width: "600px",
                            resizable: false
                        });
                    }
                    _self.dialog.html(data);
                    _self.dialog.dialog('open');
                    _self.updateForm("#userRoleDialog", "#userRoleForm");
                }
            });

            return false;
        });
    },
    grid: function () {
        var _self = this;

        var url = $("#dataGrid").data("href");

        $("#userGrid").jqGrid({
            url: url,
            datatype: "json",
            colNames: ['ФИО', 'Email', 'Login', 'Роль', 'Действия'],
            colModel: [
                {name: 'fio', index: 'fio', sortable: true},
                {name: 'email', index: 'email', sortable: true},
                {name: 'login', index: 'login', sortable: true},
                {name: 'role', index: 'role', sortable: true},
                {name: 'action', index: 'action', sotrable: false}
            ],
            rowNum: 15,
            rowList: [15, 30, 50],
            pager: "#puserGrid",
            autowidth: true,
            sortname: "fio",
            sortorder: "desc",
            altRows: true,
            viewrecords: true,
            caption: "",
            gridComplete: function () {
                var ids = $("#userGrid").jqGrid("getDataIDs");
                for (var i=0; i < ids.length; i++) {
                    var cl = ids[i];
                    var delAction = "<a class='remove-user' href='remove/" + cl +
                        "'><span class='glyphicon glyphicon-trash'></span> Удалить</a>";
                    var editAction = "<a class='edit-role' href='role/" + cl +
                        "'><span class='glyphicon glyphicon-pencil'></span> Права</a>&#160;";
                    $("#userGrid").jqGrid("setRowData", ids[i], {
                        action: editAction + delAction
                    });
                }
            },
            ondblClickRow: function (id) {_self.viewuser(id);}
        });
        $("#userGrid").jqGrid("navGrid", "#puserGrid", {edit: false, add: false, del: false});
    },
    updateForm: function (to, form) {
        var _self = this;
        $(form).ajaxForm({
            dataType: "xml",
            success: function (result) {
                var data = $(result).find('div:first').html();
                $(to).html(data);
                _self.updateForm(to, form);
            }
        })
    }
};