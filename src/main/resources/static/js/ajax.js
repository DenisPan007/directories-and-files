function getFiles(id) {

    $.ajax({
        type: "GET",
        dataType: 'json',
        url: "/files"+'?directoryId='+ encodeURIComponent(id),
        success: function (data) {
        fillTable(data);
            $('#files-modal').modal('show');
        },
        error: function (e) {
            alert('error wile retrieving files')
        }
    });

}

function fillTable(data){

var html = '';
for(var i = 0; i < data.length; i++)
            html += '<tr><td>' + data[i].name + '</td><td>' + data[i].size + '</td></tr>';
$('#modal_table_body').append(html);
}
