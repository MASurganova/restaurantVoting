var ajaxUrl;
var datatableApi;
var id;

// $(document).ready(function () {
$(function () {
    restaurantId = $('#restaurantId').attr('value');
    ajaxUrl = "ajax/admin/restaurants/" + restaurantId + "/";
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": false,
        "columns": [
            {
                "data": "description"
            },
            {
                "data": "price"
            },

            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
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
    // window.location = "http://localhost:8080/restaurantVoting/restaurants/" + id;
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function saveRestaurant() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "/update",
        data: $('#restaurantForm').serialize()
    }).done(function () {
        window.location = "http://localhost:8080/restaurantVoting/restaurants/";
        successNoty("common.saved");
    });

}

function renderEditBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='updateRow(" + row.id + ");'>" +
            "<span class='glyphicon glyphicon-pencil' aria-hidden='true'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='deleteRow(" + row.id + ");'>" +
            "<span class='glyphicon glyphicon-remove' aria-hidden='true'></span></a>";
    }
}

