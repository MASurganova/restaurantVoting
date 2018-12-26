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
