$(document).ready(function() {
	$(".linkMinus").on("click", function(evt) {
		evt.preventDefault();
		productId = $(this).attr("pid");
		quantityInput = $("#quantity" + productId);
		newQuantity = parseInt(quantityInput.val()) - 1;
		
		if (newQuantity > 0) {
			quantityInput.val(newQuantity);
		} else {
			showWarningModal('Minimum quantity is 1');
		}
	});
	
	$(".linkPlus").on("click", function(evt) {
		evt.preventDefault();
		productId = $(this).attr("pid");
		quantityInput = $("#quantity" + productId);
		newQuantity = parseInt(quantityInput.val()) + 1;
		
		if (newQuantity <= 5) {
			quantityInput.val(newQuantity);
		} else {
			showWarningModal('Maximum quantity is 5');
		}
	});

	$(".addtocart").on("click", function(evt) {
		evt.preventDefault();
		productId = $(this).attr("pid");
		quantityInput = $("#quantity" + productId);
		newQuantity = parseInt(quantityInput.val()) + 1;



		if (newQuantity <= 5) {
			quantityInput.val(newQuantity);
			addToCart();

		} else {
			showWarningModal('Maximum quantity is 5');
		}
	});
	function addToCart() {
		quantity = $("#quantity" + productId).val();
		url = contextPath + "cart/add/" + productId + "/" + quantity;

		$.ajax({
			type: "POST",
			url: url,
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeaderName, csrfValue);
			}
		}).done(function(response) {
			showModalDialog("Shopping Cart", response);
		}).fail(function() {
			showErrorModal("Error while adding product to shopping cart.");
		});
	}

});