/**
 * Created by lex on 10.02.2017.
 */

$(document).ready ( function () {
    AddressBook.actions();
    AddressBook.grid();
});

AddressBook = {
    dialog: null,
    actions: function () {

        var _self = this;

        $(document).on('click', '#create-address', function () {
            var url = $(this).data('href');

            $.ajax({
                dataType: "html",
                method: "GET",
                url: url,
                success: function (result) {

                    if (_self.dialog == null) {
                        _self.dialog = $("<div id='addressBookDialog'></div>").dialog({
                            title: "Добавить пользователя",
                            modal: true,
                            width: "600px",
                            resizable: false
                        });
                    }
                    _self.dialog.html(result);
                    _self.dialog.dialog('open');

                    ServiceMessages.updateForm("#addressBookDialog", "#addressBookForm");
                }
            });

            return false;
        });

        $(document).on('click', '.remove-address', function () {
            var url = $(this).attr('href');
            $.get(url);
            $('#refresh_addressBookGrid').trigger('click');

            return false;
        });
    },
    grid: function () {
        var url = $("#dataGrid").data("href");

        $("#addressBookGrid").jqGrid({
            url: url,
            datatype: "json",
            colNames: ['Пользователь', 'Действия'],
            colModel: [
                {name: 'toUser', index: 'toUser', width: 700, sortable: true},
                {name: 'action', index: 'action', width: 130, sortable: false}
            ],
            rowNum: 15,
            rowList: [15, 30, 50],
            pager: "#paddressBookGrid",
            autowidth: true,
            sortname: "toUser",
            sortorder: "asc",
            altRows: true,
            viewrecords: true,
            gridComplete: function () {
                var ids = $("#addressBookGrid").jqGrid("getDataIDs");
                for (var i=0; i < ids.length; i++) {
                    var cl = ids[i];
                    var sendAction = "<a class='send-address' href='message/send/" + cl +
                        "'><span class='glyphicon glyphicon-share'></span> Отправить</a>&#160;";
                    var delAction = "<a class='remove-address' href='address-book/remove/" + cl +
                        "'><span class='glyphicon glyphicon-trash'></span> Удалить</a>";
                    $("#addressBookGrid").jqGrid("setRowData", ids[i], {
                        action: sendAction + delAction
                    });
                }
            },
            caption: ""
        });
        $("#addressBookGrid").jqGrid("navGrid", "#paddressBookGrid", {edit: false, add: false, del: false})
    }
};
