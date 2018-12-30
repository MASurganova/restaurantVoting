var ajaxUrl = "ajax/admin/restaurants/";

$(function () {
    makeEditable();
});


function enable(chkbox, id) {
    var enabled = chkbox.is(":checked");
//  https://stackoverflow.com/a/22213543/548473
    $.ajax({
        url: ajaxUrl + id + "/enabled",
        type: "POST"
    }).done(function () {
        chkbox.closest("tr").toggleClass("disabled");
        successNoty(enabled ? "common.enabled" : "common.disabled");
    }).fail(function () {
        $(chkbox).prop("checked", !enabled);
    });
}

function updateTable() {
    window.location = "http://localhost:8080/restaurantVoting/restaurants/";
}


