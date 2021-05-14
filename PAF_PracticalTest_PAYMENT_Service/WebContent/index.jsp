<%@page import="com.Payments"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Payments.js"></script>

<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>

  <script>
  
  $( function() {
    $( "#datepicker" ).datepicker({ minDate: -20, maxDate: "+1M +15D" });
  } );
  
 
  
  </script>
 
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Payments Management</h1>
				<form id="formPay" name="formPay">
				
					
						<br> Select your CardNumber: <input id="cardnumber" name="cardnumber" type="text"
					class="form-control form-control-sm"> <br> 
						
					 
					<br> Select your name: <input id="name" name="name" type="text"
					class="form-control form-control-sm"> <br>
					
						<br> Select cvc on your card: <input id="cvc" name="cvc" type="text"
					class="form-control form-control-sm"> <br> 
					
						<br> Select amount: <input id="amount" name="amount" type="text"
					class="form-control form-control-sm"> <br>
					
					
				<br>	Select a date : <input id="datepicker" name="datepicker" type="text"
						class="form-control form-control-sm"> 
						
						<br>Select a order id : <input id="oid" name="oid" type="text"
						class="form-control form-control-sm"> <br>
					
					<br><input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"><br>
						
		     	<br> <input type="hidden" id="hidPayIDSave" name="hidPayIDSave" value=""><br>
					 
				</form>
				
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divPayGrid">
				
					<%
					Payments PayObj = new Payments();
						out.print(PayObj.ReadPayments());  
					%>
					
				</div>
			</div>
		</div>
	</div>

</body>
</html>