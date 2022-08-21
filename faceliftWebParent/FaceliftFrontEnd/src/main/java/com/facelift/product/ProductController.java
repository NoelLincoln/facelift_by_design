package com.facelift.product;

import java.util.List;

import com.facelift.common.entity.Review;
import com.facelift.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.facelift.category.CategoryService;
import com.facelift.common.entity.Category;
import com.facelift.common.entity.product.Product;
import com.facelift.common.exception.CategoryNotFoundException;
import com.facelift.common.exception.ProductNotFoundException;

@Controller
public class ProductController {
	@Autowired private ProductService productService;
	@Autowired private CategoryService categoryService;
	@Autowired private ReviewService reviewService;

	@GetMapping("/c/{category_alias}")
	public String viewCategoryFirstPage(@PathVariable("category_alias") String alias,
										Model model) {
		return viewCategoryByPage(alias, 1, model);
	}

	@GetMapping("/c/{category_alias}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias,
									 @PathVariable("pageNum") int pageNum,
									 Model model) {
		try {
			Category category = categoryService.getCategory(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(category);

			Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());
			List<Product> listProducts = pageProducts.getContent();

			long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
			long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
			if (endCount > pageProducts.getTotalElements()) {
				endCount = pageProducts.getTotalElements();
			}


			model.addAttribute("currentPage", pageNum);
			model.addAttribute("totalPages", pageProducts.getTotalPages());
			model.addAttribute("startCount", startCount);
			model.addAttribute("endCount", endCount);
			model.addAttribute("totalItems", pageProducts.getTotalElements());
			model.addAttribute("pageTitle", category.getName());
			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("listProducts", listProducts);
			model.addAttribute("category", category);

			return "product/products_by_category";
		} catch (CategoryNotFoundException ex) {
			return "error/404";
		}
	}

	@GetMapping("/p/{product_alias}")
	public String viewProductDetail(@PathVariable("product_alias") String alias, Model model) {

		try {
			Product product = productService.getProduct(alias);
			List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());
			Page<Review> listReviews = reviewService.list3MostRecentReviewsByProduct(product);

			model.addAttribute("listCategoryParents", listCategoryParents);
			model.addAttribute("product", product);
			model.addAttribute("listReviews", listReviews);
			model.addAttribute("pageTitle", product.getShortName());

			return "product/product_detail";
		} catch (ProductNotFoundException e) {
			return "error/404";
		}
	}
//	@GetMapping("/products")
//	public String viewProducts(String alias, Model model) {
//
//		try {
//			Product product = productService.showAll();
//			Page<Review> listReviews = reviewService.list3MostRecentReviewsByProduct(product);
//
//
//			model.addAttribute("product", product);
//			model.addAttribute("listReviews", listReviews);
//			model.addAttribute("pageTitle", product.getShortName());
//
//			return "products";
//		} catch (ProductNotFoundException e) {
//			return "error/404";
//		}
//	}

	@GetMapping("/search")
	public String searchFirstPage(String keyword, Model model) {
		return searchByPage(keyword, 1, model);
	}

	@GetMapping("/search/page/{pageNum}")
	public String searchByPage(String keyword,
							   @PathVariable("pageNum") int pageNum,
							   Model model) {
		Page<Product> pageProducts = productService.search(keyword, pageNum);
		List<Product> listResult = pageProducts.getContent();

		long startCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
		long endCount = startCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1;
		if (endCount > pageProducts.getTotalElements()) {
			endCount = pageProducts.getTotalElements();
		}

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("pageTitle", keyword + " - Search Result");

		model.addAttribute("keyword", keyword);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listResult", listResult);

		return "product/search_result";
	}
}
