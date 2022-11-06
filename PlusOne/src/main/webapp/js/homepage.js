$(document).ready(function () {
    updateData();
});

var inventoryData;

var updateData = function () {

    $.ajax({
            url: "/api/getinfo",
            dataType: "json",
            type: "Get",
            async: true,
            success: function (data) {
                inventoryData = data;
                $('#ProductCountText').text(data['product-count'])
                $('#ProductTotalWorthText').html('<span>&#8377;</span> '+data['total-worth']);
            },
            error: function (xhr, exception, thrownError) {

            }
        });
};