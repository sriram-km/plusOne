$(document).ready(function () {
    refreshData();
}
);

var productData;
var rowCount;
var currentPage = 1;
var pageCount = 1;
var buttonCell = '<td><button id="editRow"><i class="fas fa-edit"></i></button><button id="deleteRow"><i class="fas fa-trash-alt"></i></button></td>';

$(document).delegate('#syncData', 'click', function() {
    refreshData();
});

var refreshData = function () {
    $("#searchDataInput").val("")
    $.ajax({
            url: "/api/getproducts",
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

var populateTable = function (data) {
    productData = data;
    $('#productTable').html('<tr><th>SKU</th><th>Name</th><th>Units</th><th>Unit price</th><th>Updated time</th><th>Action</th></tr>');
    rowCount = productData.length-1;

    if (rowCount <= 20) {
        $('#pagination').text('1/1');
        currentPage = 1;
        pageCount = 1;
        for(var i = 1; i <= rowCount; i++) {
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

$(document).delegate('#deleteRow', 'click', function() {
    var proceed = confirm("Are you sure you want to delete the row?");
    if (proceed) {
      var row = $(this).parent().parent().children();
      var cells = row.text().split(" ");

      var sku = row.eq(0).text();
      var name = row.eq(1).text();
      console.log(row.eq(1).text());

      jsonObject = {"SKU": sku, "NAME": name};
      postData = {"params":encodeURIComponent(JSON.stringify(jsonObject))};

      $.ajax({
              url: "/api/deleteproduct",
              dataType: "json",
              type: "Post",
              async: true,
              data: postData,
              success: function (data) {
                refreshData();
              },
              error: function (xhr, exception, thrownError) {
                  alert("Error code : "+xhr.status+" "+exception);
              }
      });
    }
});

let update_row = undefined;

$(document).delegate('#editRow', 'click', function() {
    $("#editProductFormDiv").show();
    var row = $(this).parent().parent();
    update_row = row;

    $("#editProductForm #sku").val(update_row.children().eq(0).text());
    $("#editProductForm #name").val(update_row.children().eq(1).text());
    $("#editProductForm #units").val(update_row.children().eq(2).text());
    $("#editProductForm #units_price").val(update_row.children().eq(3).text());
});

$("#editProductFormDiv #closeButton").click(function() {
    $('#editProductForm').trigger("reset");
    $("#editProductFormDiv").hide();
})

$("#updateProductButton").click(function() {
    var update_data = {};

    if($("#editProductForm #units").val() != "") {
        update_data["UNITS"] = $("#editProductForm #units").val();
    }
    if($("#editProductForm #units_price").val() != "") {
        update_data["UNITS_PRICE"] = $("#editProductForm #units_price").val();
    }
    if($("#editProductForm #description").val() != "") {
            update_data["DESCRIPTION"] = $("#editProductForm #description").val();
    }

    jsonObject = {"SKU": $("#editProductForm #sku").val(), "NAME": $("#editProductForm #name").val(), "update-data": update_data};
    postData = {"params":encodeURIComponent(JSON.stringify(jsonObject))};

    $.ajax({
        url: "/api/updateproduct",
        dataType: "json",
        type: "Post",
        async: true,
        data: postData,
        success: function (data) {
          alert("Updated the product");
          refreshData();
        },
        error: function (xhr, exception, thrownError) {
            alert("Error code : "+xhr.status+" "+exception);
        }
    });
    update_row = undefined;
    $('#editProductForm').trigger("reset");
    $("#editProductFormDiv").hide();
});

$("#addData").click(function() {
    $("#addProductFormDiv").show();
});

$("#addProductButton").click(function() {

    jsonObject = {"SKU": $("#addProductForm #sku").val(), "NAME": $("#addProductForm #name").val(), "DESCRIPTION": $("#addProductForm #description").val(), "UNITS": $("#addProductForm #units").val(), "UNITS_PRICE": $("#addProductForm #units_price").val()};
    postData = {"params":encodeURIComponent(JSON.stringify(jsonObject))};
    $.ajax({
        url: "/api/addproduct",
        dataType: "json",
        type: "Post",
        async: true,
        data: postData,
        success: function (data) {
          refreshData();
        },
        error: function (xhr, exception, thrownError) {
            alert("Error code : "+xhr.status+" "+exception);
        }
    });
    $('#addProductForm').trigger("reset");
    $("#addProductFormDiv").hide();
});

$("#addProductFormDiv #closeButton").click(function() {
    $('#addProductForm').trigger("reset");
    $("#addProductFormDiv").hide();
})

$(document).delegate('#searchData', 'click', function() {
    var search_term= $("#searchDataInput").val();
    if (search_term != "") {
        jsonObject = {"search-term": search_term};
        postData = {"params":encodeURIComponent(JSON.stringify(jsonObject))};
        console.log(postData);
        $.ajax({
                  url: "/api/getproducts",
                  dataType: "json",
                  type: "Get",
                  async: true,
                  data: postData,
                  success: function (data) {
                    populateTable(data);
                  },
                  error: function (xhr, exception, thrownError) {
                      alert("Error code : "+xhr.status+" "+exception);
                  }
          });
    }
});

$(document).delegate('#nextPage', 'click', function() {

    var nextPage = currentPage + 1;

    if (nextPage <= pageCount) {
        $('#productTable').html('<tr><th>SKU</th><th>Name</th><th>Units</th><th>Unit price</th><th>Updated time</th><th>Action</th></tr>');

        for(var i = currentPage*20+1; i <= Math.min(rowCount,nextPage*20); i++) {
            var currentRow = productData[i];

            var sku = currentRow[0];
            var name = currentRow[1];
            var units = currentRow[3];
            var units_price = currentRow[4];
            var updated_time = currentRow[5];
            var rowData = `<tr> <td>${sku}</td> <td>${name}</td> <td>${units}</td> <td>${units_price}</td> <td>${updated_time}</td> ${buttonCell}</tr>`;

            $('#productTable').append(rowData);
        }

        currentPage = nextPage;

        $('#pagination').text(`${currentPage}/${pageCount}`);
    }
});

$(document).delegate('#prevPage', 'click', function() {

    var prevPage = currentPage - 1;

    if (prevPage >= 1) {
        $('#productTable').html('<tr><th>SKU</th><th>Name</th><th>Units</th><th>Unit price</th><th>Updated time</th><th>Action</th></tr>');

        for(var i = (prevPage)*20-19; i <= prevPage*20; i++) {
            var currentRow = productData[i];

            var sku = currentRow[0];
            var name = currentRow[1];
            var units = currentRow[3];
            var units_price = currentRow[4];
            var updated_time = currentRow[5];
            var rowData = `<tr> <td>${sku}</td> <td>${name}</td> <td>${units}</td> <td>${units_price}</td> <td>${updated_time}</td> ${buttonCell}</tr>`;

            $('#productTable').append(rowData);
        }

        currentPage = prevPage;
        $('#pagination').text(`${currentPage}/${pageCount}`);
    }

});
