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
                dataType: "html",
                method: "GET",
                url: url,
                success: function (result) {
                    if (_self.dialog == null) {
                        _self.dialog = $("<div id='mainDialog'></div>").dialog({
                            title: "Изменить пароль",
                            modal: true,
                            width: "600px",
                            resizable: false
                        });
                    }
                    _self.dialog.html(result);
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
            dataType: "html",
            success: function (result) {
                $(to).html(result);
                _self.updateForm(to, form);
            }
        })
    },
    parseXML: function( data ) {
        var xml, tmp;
        try {
            if ( window.DOMParser ) { // Standard
                tmp = new DOMParser();
                xml = tmp.parseFromString( data , "text/xml" );
            } else { // IE
                xml = new ActiveXObject( "Microsoft.XMLDOM" );
                xml.async = "false";
                xml.loadXML( data );
            }
        } catch( e ) {
            xml = undefined;
        }
        if ( !xml || !xml.documentElement || xml.getElementsByTagName( "parsererror" ).length ) {
            jQuery.error( "Invalid XML: " + data );
        }
        return xml;
    },
};
