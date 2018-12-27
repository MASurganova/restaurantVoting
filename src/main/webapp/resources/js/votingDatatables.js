var ajaxUrl = "ajax/profile/voting/";

var datatableApi;

function chooseRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: "POST"
    }).done(function () {
        updateTable();
        successNoty("common.saved");
    });
}

function endVoting() {
    $.ajax({
        url: ajaxUrl + "end",
        type: "PUT"
    }).done(function () {
        updateTable();
        successNoty("app.end");
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
                "data": "voters",
                "defaultContent": "0"
            },

            {
                "defaultContent": "Choose",
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