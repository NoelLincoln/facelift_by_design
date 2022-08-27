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
	private CategoryRepository categoryRepository;
	private ProductRepository productRepository;
	private BrandRepository brandRepository;
	private OrderRepository orderRepository;
	private CustomerRepository customerRepository;

	public MainController(CategoryRepository categoryRepository, ProductRepository productRepository, BrandRepository brandRepository, OrderRepository orderRepository, CustomerRepository customerRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.brandRepository = brandRepository;
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
	}


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
