document.getElementById('getData').addEventListener("click", getData);
$( document ).ready(getData());
setInterval(getData, 7000);
function getData()
{
    $.ajax({
        url: 'api/orders',
        type: 'GET',
        contentType: "application/json",
        dataType: "json",
        success: function (response) {
            console.log(response);
            var order_data = '';
            $("#order_table").find("tr:gt(0)").remove();
            $.each(response, function (key, value) {
                order_data += '<tr>';
                order_data += '<td>' + value.id + '</td>';
                order_data += '<td>' + value.clientName + '</td>';
                order_data += '<td>' + value.status + '</td>';
                order_data += '</tr>';
            });
            $('#order_table').append(order_data);
        },
        error: function (errors) {
            console.log(errors);
        },
    });
}
