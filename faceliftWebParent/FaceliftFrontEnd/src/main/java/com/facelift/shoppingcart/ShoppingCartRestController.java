package com.facelift.shoppingcart;

import com.facelift.Utility;
import com.facelift.common.entity.Customer;
import com.facelift.common.exception.CustomerNotFoundException;
import com.facelift.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class ShoppingCartRestController {
	@Autowired private ShoppingCartService cartService;
	@Autowired private CustomerService customerService;

	@PostMapping("/cart/add/{productId}/{quantity}")
	public String addProductToCart(@PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {

		try{
			String sessionToken = (String) request.getSession(true).getAttribute("sessiontToken");

			Customer customer = getAuthenticatedCustomer(request);
			if (customer == null ) {
				sessionToken = UUID.randomUUID().toString();
				request.getSession().setAttribute("sessiontToken", sessionToken);
			}else{
				Integer updatedQuantity = cartService.addProduct(productId, quantity, customer, sessionToken);
				return updatedQuantity + " item(s) of this product were added to your shopping cart.";

			}
			Integer updatedQuantity = cartService.addProduct(productId, quantity, customer, sessionToken);
			return updatedQuantity + " item(s) of this product were added to your shopping cart.";

		}catch (ShoppingCartException ex) {
			return ex.getMessage();
		}

	}

	private Customer getAuthenticatedCustomer(HttpServletRequest request)
			 {
				 String sessionToken = (String) request.getSession(true).getAttribute("sessiontToken");

				 String email = Utility.getEmailOfAuthenticatedCustomer(request);
		if (email == null) {
			sessionToken = UUID.randomUUID().toString();
			request.getSession().setAttribute("sessiontToken", sessionToken);		}

		return customerService.getCustomerByEmail(email);
	}

	@PostMapping("/cart/update/{productId}/{quantity}")
	public String updateQuantity(@PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {
			String sessionToken = (String) request.getSession(true).getAttribute("sessiontToken");

			Customer customer = getAuthenticatedCustomer(request);

			request.getSession();

			float subtotal = cartService.updateQuantity(productId, quantity, customer,sessionToken );

			return String.valueOf(subtotal);
			}

//	@DeleteMapping("/cart/remove/{productId}")
//	public String removeProduct(@PathVariable("productId") Integer productId,
//			HttpServletRequest request) {
//		try {
//			Customer customer = getAuthenticatedCustomer(request);
//			cartService.removeProduct(productId, customer);
//
//			return "The product has been removed from your shopping cart.";
//
//		} catch (CustomerNotFoundException e) {
//			return "You must login to remove product.";
//		}
//	}
}
