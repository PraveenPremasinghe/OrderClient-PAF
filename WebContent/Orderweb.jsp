<%@ page import="com.orderclient.Order"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Order Management</title>
<script src="Component/jquery-3.2.1.min.js"></script>
<script src="Component/order.js"></script>
<link rel="stylesheet" href="Views/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">

				<div class="container pt-5 pb-5">

					<center>
						<u><h4 class="mb-3 pb-3">Order Form</h4></u>
					</center>
					<form class="needs-validation" novalidate id="order_idform">
						<input type='hidden' id='hiddenorder_idSave'
							name='hiddenorder_idSave' value=''>
						<div class="row">

							<div class="col-md-12 mb-3">
								<label for="customerid">Customer ID:</label> <input type="text"
									class="form-control" id="customerid" name="customerid">

							</div>

						
							
						</div>

						<div class="mb-3">
							<label for="product_id">Product ID:</label> <input type="text"
								class="form-control" id="product_id" name="product_id">

						</div>

						<div class="mb-3">
							<label for="items">Items :</label> <input type="text"
								class="form-control" id="items" name="items">

						</div>

						<div class="mb-3">
							<label for="item_price">Items Price:</label> <input type="text"
								class="form-control" id="item_price" name="item_price">

						</div>

						<div class="mb-3">
							<label for="discount">Discount:</label> <input
								class="form-control" id="discount" name="discount"></input>
						</div>




						<button class="btn btn-primary btn-lg btn-block" type="button" 
						
							id="placeorder">Place Order</button>
							
							
					</form>
				</div>
			</div>

			<br> <br> <br> <br> <br> <br>

		</div>
		
		<div id='alertSuccess' name='alertSuccess' class='alert alert-success'></div>
		<div id='alertError' name='alertError' class='alert alert-danger'></div>
		<br>

		<div id="divOrderGrid">
		
			<%
				Order OrderObject = new Order();
				out.print(OrderObject.getOrder());
			%>
		</div>
	</div>


</body>
</html>