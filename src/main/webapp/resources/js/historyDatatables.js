var ajaxUrl = "ajax/admin/history/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": false,
        "columns": [
            {
                "data": "date"
            },
            {
                "data": "restaurantName",
                "render": function (data, type, row) {
                    if (type === "display") {
                        return "<a href='restaurants/" + data + "'>" + data + "</a>";
                    }
                    return data;
                }
            },
            {
                "data": "date",
                "defaultContent": "",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return "<a onclick='deleteRow(\"" + date + "\")'>" +
                            "<span class='glyphicon glyphicon-remove' aria-hidden='true'></span></a>";
                    }
                    return date;
                }
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ],
        "initComplete": makeEditable
    });
});

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function deleteRow(date) {
    $.ajax({
        url: ajaxUrl + date,
        type: "DELETE",
    }).done(function () {
        updateTable();
        successNoty("common.deleted");
    });
}

function makeEditable() {

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

