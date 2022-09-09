package com.facelift;

import java.util.List;

import com.facelift.common.entity.product.Product;
import com.facelift.common.exception.ProductNotFoundException;
import com.facelift.product.ProductRepository;
import com.facelift.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.facelift.category.CategoryService;
import com.facelift.common.entity.Category;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

	@Autowired private CategoryService categoryService;
	@Autowired private ProductRepository productRepository;

	@GetMapping("")
	public String viewHomePage(Model model, HttpServletRequest request) {


			List<Product> productList = productRepository.findAll();
			List<Category> listCategories = categoryService.listNoChildrenCategories();
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("productList", productList);

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

//	@GetMapping("/gallery")
//	public String viewGallery(Model model, HttpServletRequest request) {
//
//
//		List<Product> productList = productRepository.findAll();
//		List<Category> listCategories = categoryService.listNoChildrenCategories();
//		model.addAttribute("listCategories", listCategories);
//		model.addAttribute("productList", productList);
//
//		return "gallery";
//	}


}
