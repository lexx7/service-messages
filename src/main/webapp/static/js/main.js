/**
 * Created by lex on 08.02.2017.
 */

$(function () {
    ServiceMessages.actions();
});

ServiceMessages = {
    dialog: null,
    actions: function () {
        var _self = this;

        $(document).on('click', '#replace-password-user', function () {
            var url = $(this).attr("href");
            $.ajax({
                dataType: "xml",
                method: "GET",
                url: url,
                success: function (result) {
                    var data = $(result).find('div:first').html();

                    if (_self.dialog == null) {
                        _self.dialog = $("<div id='mainDialog'></div>").dialog({
                            title: "Изменить пароль",
                            modal: true,
                            width: "600px",
                            resizable: false
                        });
                    }
                    _self.dialog.html(data);
                    _self.dialog.dialog('open');
                    _self.updateForm("#mainDialog", "#replacePasswordUserForm");
                }
            });

            return false;
        });
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