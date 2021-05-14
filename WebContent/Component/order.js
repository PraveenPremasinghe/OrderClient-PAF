
	//alert("Test");

$(document).ready(function () {

    if ($("#alertSuccess").text().trim() == "") {
        $("#alertSuccess").hide();
    }
    $("#alertError").hide();
});







$(document).on("click", "#placeorder", function (event) {
	// Clear alerts---------------------
    $("#alertSuccess").text("");
    $("#alertSuccess").hide();
    $("#alertError").text("");
    $("#alertError").hide();
    // Form validation-------------------
    var status = validateOrderForm();
    if (status != true) {
	

	
        $("#alertError").text(status);
        $("#alertError").show();

        return;
    }

    // If valid------------------------
    var type = ($("#hiddenorder_idSave").val() == "") ? "POST" : "PUT";
    $.ajax(
        {
            url: "OrderClientAPI",
            type: type,
            data: $("#order_idform").serialize(),
            dataType: "text",
            complete: function (response, status) {
                onOrderSaveComplete(response.responseText, status);
            }
        });
});



function onOrderSaveComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "successfull") {
            $("#alertSuccess").text("Successfully saved.");
            $("#alertSuccess").show();
            $("#divOrderGrid").html(resultSet.data);
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while saving.");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while saving..");
        $("#alertError").show();
    }
    $("#hiddenorder_idSave").val("");
    $("#order_idform")[0].reset();
}

$(document).on("click", ".btnUpdate", function (event) {
    $("#hiddenorder_idSave").val($(this).data("orderid"));

    $("#customerid").val($(this).closest("tr").find('td:eq(1)').text());
    $("#product_id").val($(this).closest("tr").find('td:eq(2)').text());
    $("#items").val($(this).closest("tr").find('td:eq(3)').text());
    $("#item_price").val($(this).closest("tr").find('td:eq(4)').text());
    $("#discount").val($(this).closest("tr").find('td:eq(5)').text());

});

$(document).on("click", "#btnRemove", function (event) {
    $.ajax(
        {
            url: "OrderClientAPI",
            type: "DELETE",
            data: "order_id=" + $(this).data("orderid"),
            dataType: "text",
            complete: function (response, status) {
                onOrderDeleteComplete(response.responseText, status);
            }
        });
});

function onOrderDeleteComplete(response, status) {
    if (status == "success") {
        var resultSet = JSON.parse(response);
        if (resultSet.status.trim() == "successfull") {
            $("#alertSuccess").text("Successfully deleted.");
            $("#alertSuccess").show();
            $("#divOrderGrid").html(resultSet.data);
        } else if (resultSet.status.trim() == "error") {
            $("#alertError").text(resultSet.data);
            $("#alertError").show();
        }
    } else if (status == "error") {
        $("#alertError").text("Error while deleting.");
        $("#alertError").show();
    } else {
        $("#alertError").text("Unknown error while deleting..");
        $("#alertError").show();
    }
}



// CLIENT-MODEL================================================================
function validateOrderForm() {
	
	
	///////////////////////////Cutomer id////////////////////////////

   if ($("#customerid").val().trim() == "") {
        return "Insert  Customer ID.";
    }

var tmpCustomerId = $("#customerid").val().trim();
    if (!$.isNumeric(tmpCustomerId)) {
        return "Insert a numerical value for Customer Id.";
    }

	
    ///////////////////// Product id//////////////////////////////
    if ($("#product_id").val().trim() == "") {
        return "Insert Product Id.";
    }

var tmpProductid = $("#product_id").val().trim();
    if (!$.isNumeric(tmpProductid)) {
        return "Insert a numerical value for Product ID.";
    }





  /////////////////////Items////////////////////////////////
    if ($("#items").val().trim() == "") {
        return "Insert Items.";
    }

var tmpitem = $("#items").val().trim();
    if (!$.isNumeric(tmpitem)) {
        return "Insert a numerical value for Items.";
    }



    ///////////////////////Item Price////////////////////// 
    if ($("#item_price").val().trim() == "") {
        return "Insert Item Price.";
    }

var tmpitemPrice = $("#item_price").val().trim();
    if (!$.isNumeric(tmpitemPrice)) {
        return "Insert a numerical value for Item Price .";
    }

 $("#item_price").val(parseFloat(tmpitemPrice).toFixed(2));




    ////////////////////Discount/////////////////////// 
    if ($("#discount").val().trim() == "") {
        return "Insert discount .";
    }

var tmpdiscount = $("#discount").val().trim();
    if (!$.isNumeric(tmpdiscount)) {
        return "Insert a numerical value for Discount.";
    }

 $("#discount").val(parseFloat(tmpdiscount).toFixed(2));









  

   

    return true;
}
