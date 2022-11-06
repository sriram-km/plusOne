$(document).ready(function () {
    refreshData();
}
);

var refreshData = function () {
    $("#searchDataInput").val("")
    $.ajax({
            url: "/api/gettransactions",
            dataType: "json",
            type: "Get",
            async: true,
            success: function (data) {
              metaData = data[0];
              populateTable(data);
            },
            error: function (xhr, exception, thrownError) {

            }
        });
};

var transactionData;
var rowCount;
var currentPage = 1;
var pageCount = 1;

var populateTable = function (data) {
    transactionData = data;
    $('#productTable').html('<tr><th>SKU</th><th>Name</th><th>Units Before</th><th>Transaction</th><th>Units Before</th><th>Updated time</th></tr>');
    rowCount = transactionData.length-1;

    if (rowCount <= 20) {
        $('#pagination').text('1/1');
        currentPage = 1;
        pageCount = 1;
        for(var i = 1; i <= rowCount; i++) {
            var currentRow = data[i];

            var sku = currentRow[0];
            var name = currentRow[1];
            var countBefore = currentRow[2];
            var countAfter = currentRow[3];
            var transaction = currentRow[4];
            var transactionTime = currentRow[5];
            console.log(transactionTime);

            var rowData = `<tr> <td>${sku}</td> <td>${name}</td> <td>${countBefore}</td> <td>${transaction}</td> <td>${countAfter}</td> <td>${transactionTime}</td></tr>`;

            $('#productTable').append(rowData);
        }
    }
    else {
        pageCount = Math.ceil((rowCount/20));
        currentPage = 1;
        $('#pagination').text(`1/${pageCount}`);
        for(var i = 1; i <= 20; i++) {
            var currentRow = data[i];

            var sku = currentRow[0];
            var name = currentRow[1];
            var units = currentRow[3];
            var units_price = currentRow[4];
            var updated_time = currentRow[5];

            var rowData = `<tr> <td>${sku}</td> <td>${name}</td> <td>${units}</td> <td>${units_price}</td> <td>${updated_time}</td> ${buttonCell}</tr>`;

            $('#productTable').append(rowData);
        }
    }
};