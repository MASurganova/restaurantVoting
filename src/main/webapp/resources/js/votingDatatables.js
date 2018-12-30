var ajaxUrl = "ajax/profile/voting/";

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
        type: "Get"
    }).done(function () {
        updateTable();
        successNoty("app.end");
    });
}

function startVoting() {
    $.ajax({
        url: ajaxUrl + "start",
        type: "Get"
    }).done(function () {
        successNoty("app.start");
    });
}

function updateTable() {
    window.location = "http://localhost:8080/restaurantVoting/voting/";
}

$(function () {
    $.ajaxSetup({cache: false});
});