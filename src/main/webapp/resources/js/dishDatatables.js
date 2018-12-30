var ajaxUrl = "ajax/admin/restaurants/";
var datatableApi;
var id;

// $(document).ready(function () {
$(function () {
    id = $(this).data("restaurantId");
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl + id,
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

function deleteRow(id, restaurantId) {
    $.ajax({
        url: ajaxUrl + restaurantId + "/" + id,
        type: "DELETE"
    }).done(function () {
        updateTable(restaurantId);
        successNoty("common.deleted");
    });
}

function updateRow(id, restaurantId) {
    $("#modalTitle").html(i18n["editTitle"]);
    $.get(ajaxUrl + restaurantId + "/" + id, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });
        $('#editRow').modal();
    });
}

var form;
function makeEditable() {
    form = $('#detailsForm');

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function add() {
    $("#modalTitle").html(i18n["addTitle"]);
    form.find(":input").val("");
    $("#editRow").modal();
}


function updateTable(id) {
    // window.location = "http://localhost:8080/restaurantVoting/restaurants/" + id;
    $.get(ajaxUrl + id, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function save(id) {
    $.ajax({
        type: "POST",
        url: ajaxUrl + id,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        updateTable(id);
        successNoty("common.saved");
    });
}

function saveRestaurant(id) {
    $.ajax({
        type: "POST",
        url: ajaxUrl + id + "/update",
        data: $('#restaurantForm').serialize()
    }).done(function () {
        window.location = "http://localhost:8080/restaurantVoting/restaurants/";
        successNoty("common.saved");
    });

}

function renderEditBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='updateRow(" + row.id + ", " + id + ");'>" +
            "<span class='glyphicon glyphicon-pencil' aria-hidden='true'></span></a>";
    }
}

function renderDeleteBtn(data, type, row) {
    if (type === "display") {
        return "<a onclick='deleteRow(" + row.id + ", " + id + ");'>" +
            "<span class='glyphicon glyphicon-remove' aria-hidden='true'></span></a>";
    }
}

