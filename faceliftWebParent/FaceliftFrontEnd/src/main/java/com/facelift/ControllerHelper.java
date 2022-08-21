package com.facelift;

import com.facelift.common.entity.Customer;
import com.facelift.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ControllerHelper {
	@Autowired private CustomerService customerService;
	
	public Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);				
		return customerService.getCustomerByEmail(email);
	}
}
