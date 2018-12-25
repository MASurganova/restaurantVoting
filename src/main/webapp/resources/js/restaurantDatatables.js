var ajaxUrl = "ajax/admin/restaurants/";

var datatableApi;

function enable(chkbox, id) {
    var enabled = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: ajaxUrl + id + "/enabled",
        type: "POST"
    }).done(function () {
        chkbox.closest("tr").toggleClass("disabled");
        successNoty(enabled ? "Enabled" : "Disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !enabled);
    });
}

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": false,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "lunch",
                "defaultContent": "<i>Not set</i>"
            },
            {
                "data": "totalPrice",
                "defaultContent": "0"
            },
            {
                "data": "enabled"
            },

            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});