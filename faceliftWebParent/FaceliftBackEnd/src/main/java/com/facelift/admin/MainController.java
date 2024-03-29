package com.facelift.admin;

import com.facelift.admin.brand.BrandRepository;
import com.facelift.admin.category.CategoryRepository;
import com.facelift.admin.customer.CustomerRepository;
import com.facelift.admin.order.OrderRepository;
import com.facelift.admin.product.ProductRepository;
import com.facelift.admin.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private BrandRepository brandRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CustomerRepository customerRepository;




	@GetMapping("")
	public String viewHomePage(Model model) {
		model.addAttribute("user_count", userRepository.count());
		model.addAttribute("category_count", categoryRepository.count());
		model.addAttribute("product_count", productRepository.count());
		model.addAttribute("brand_count", brandRepository.count());
		model.addAttribute("order_count", orderRepository.count());
		model.addAttribute("customer_count", customerRepository.count());




		return "index";
	}
	
	@GetMapping("/login")
	public String viewLoginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}
		
		return "redirect:/";
	}
}
