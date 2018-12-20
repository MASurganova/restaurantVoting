var ajaxUrl = "ajax/admin/restaurants/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
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

function deleteRow(id, restaurantId) {
    $.ajax({
        url: ajaxUrl + restaurantId + "/" + id,
        type: "DELETE",
    }).done(function () {
        updateTable();
        successNoty("Deleted");
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
    form.find(":input").val("");
    $("#editRow").modal();
}


function updateTable(id) {
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
        updateTable();
        successNoty("Saved");
    });
}

function saveRestaurant(id) {
    $.ajax({
        type: "PUT",
        url: ajaxUrl + id,
        data: $('#restaurantForm').serialize()
    }).done(function () {
        $.get(ajaxUrl);
        successNoty("Saved");
    });
}
