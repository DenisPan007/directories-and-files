function getFiles() {

    var search = $("#testID").text();
    $.ajax({
        type: "GET",
        url: "/files"+'/'+ search,
        success: function (data) {
            alert(data);
            $('#files-modal').modal('show');
        },
        error: function (e) {
            alert('error wile retrieving files')
        }
    });

}
