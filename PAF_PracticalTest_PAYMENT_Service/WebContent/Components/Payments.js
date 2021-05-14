$(document).ready(function() {
	$("#alertSuccess").hide();
	$("#alertSuccess").hide();

});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validatePayForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------

	var type = ($("#hidPayIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "PaymentsAPI",
		type :type ,
		data : $("#formPay").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPaySaveComplete(response.responseText, status);
		}
	});
});

function onPaySaveComplete(response, status) {
	
	
	if (status == "success") {
		var resultSet = JSON.parse(response);

    if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPayGrid").html(resultSet.data);

		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}

	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();

	} 

	else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	
	$("#hidPayIDSave").val("");
	$("#formPay")[0].reset();
}

// UPDATE==========================================
$(document).on("click",".btnUpdate",function(event)
		{
			$("#hidPayIDSave").val($(this).data("payid"));
			
			
			$("#cardnumber").val($(this).closest("tr").find('td:eq(1)').text());
			$("#name").val($(this).closest("tr").find('td:eq(2)').text());
		    $("#cvc").val($(this).closest("tr").find('td:eq(3)').text());
		    $("#amount").val($(this).closest("tr").find('td:eq(4)').text());
		    $("#datepicker").val($(this).closest("tr").find('td:eq(5)').text());
		    $("#oid").val($(this).closest("tr").find('td:eq(6)').text());
			
			
		});

// REMOVE==========================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PaymentsAPI",
		type : "DELETE",
		data : "PayID=" + $(this).data("payid"),
		dataType : "text",
		complete : function(response, status) {
			onPayDeleteComplete(response.responseText, status);
		}
	});
});

function onPayDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPayGrid").html(resultSet.data);
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

// CLIENTMODEL=========================================================================
function validatePayForm() {
	// Customer Number-------------------------------
	if ($("#cardnumber").val().trim() == "") {
		return "Insert your card Number.";
	}
	// is numerical value
	var cno = $("#cardnumber").val().trim();
	if (!$.isNumeric(cno)) {
		return "Insert a numerical value for Card Number.";
	}
	

	
	//Name On Card------------------------------
	if ($("#name").val().trim() == "") {
		return "Insert your name on card.";
	}
	
	// CVC-------------------------------
	if ($("#cvc").val().trim() == "") {
		return "Insert your cvc.";
	}
	// is numerical value
	var cvc = $("#cvc").val().trim();
	if (!$.isNumeric(cvc)) {
	   return "Insert a numerical value for cvc.";
	   
	}
	
	// Amount-------------------------------
	if ($("#amount").val().trim() == "") {
		return "Insert your amount.";
	}
	// is numerical value
	var amount = $("#amount").val().trim();
	if (!$.isNumeric(amount)) {
	   return "Insert a numerical value for amount.";
	   
	}
	
	// Date
			if ($("#datepicker").val().trim() == "") {
				return "Select a date.";
			}
		
		// order id-------------------------------
		if ($("#oid").val().trim() == "") {
			return "Insert your order id.";
		}
		// is numerical value
		var oid = $("#oid").val().trim();
		if (!$.isNumeric(oid)) {
		   return "Insert a numerical value for oid.";
		   
		}
		
		
		return true;
}


