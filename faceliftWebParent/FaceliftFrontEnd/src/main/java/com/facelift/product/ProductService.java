package com.facelift.product;

import com.facelift.paging.PagingAndSortingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.facelift.common.entity.product.Product;
import com.facelift.common.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ProductService {
	public static final int PRODUCTS_PER_PAGE = 10;
	public static final int SEARCH_RESULTS_PER_PAGE = 10;

	@Autowired private ProductRepository repo;


	public Page<Product> listByCategory(int pageNum, Integer categoryId) {
		String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

		return repo.listByCategory(categoryId, categoryIdMatch, pageable);
	}
	public List<Product> listByPage(ProductPageInfo pageInfo, int pageNum, String sortDir,
									String keyword) {

		Sort sort = Sort.by("name");

		if (sortDir.equals("asc")) {
			sort = sort.ascending();
		} else if (sortDir.equals("desc")) {
			sort = sort.descending();
		}


		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE, sort);

		Page<Product> pageProducts = null;

		if (keyword != null && !keyword.isEmpty()) {
			pageProducts = repo.search(keyword, pageable);
		} else {
			pageProducts = repo.findProducts(pageable);
		}
		List<Product> products = pageProducts.getContent();

		pageInfo.setTotalElements(pageProducts.getTotalElements());
		pageInfo.setTotalPages(pageProducts.getTotalPages());

		if (keyword != null && !keyword.isEmpty()) {
			List<Product> searchResult = pageProducts.getContent();

			return searchResult;

		} else {
			return listHierarchicalProducts(products, sortDir);
		}
	}
	private List<Product> listHierarchicalProducts(List<Product> products, String sortDir) {
		List<Product> hierarchicalProducts = new ArrayList<>();

		for (Product product : products) {
			hierarchicalProducts.add(Product.copyFull(product));

//			Set<Product> children = sortSubCategories(rootCategory.getChildren(), sortDir);

//			for (Product subCategory : children) {
//				String name = "--" + subCategory.getName();
//				hierarchicalProducts.add(Product.copyFull(subCategory, name));
//
//				listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1, sortDir);
//			}
		}

		return hierarchicalProducts;
	}
	public void searchProducts(int pageNum, PagingAndSortingHelper helper) {
		Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
		String keyword = helper.getKeyword();
		Page<Product> page = repo.searchProductsByName(keyword, pageable);
		helper.updateModelAttributes(pageNum, page);
	}

	public Product getProduct(String alias) throws ProductNotFoundException {
		Product product = repo.findByAlias(alias);
		if (product == null) {
			throw new ProductNotFoundException("Could not find any product with alias " + alias);
		}

		return product;
	}

	public Product getProductById(Integer id) throws ProductNotFoundException {
		try {
			Product product = repo.findById(id).get();
			return product;
		} catch (NoSuchElementException ex) {
			throw new ProductNotFoundException("Could not find any product with ID " + id);
		}
	}

	public Page<Product> search(String keyword, int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);
		return repo.search(keyword, pageable);

	}


}
