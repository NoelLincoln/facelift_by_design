package com.facelift.admin.review;

import com.facelift.admin.paging.SearchRepository;
import com.facelift.common.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends SearchRepository<Review, Integer> {
	
	@Query("SELECT r FROM Review r WHERE r.headline LIKE %?1% OR "
			+ "r.comment LIKE %?1% OR r.product.name LIKE %?1% OR "
			+ "CONCAT(r.customer.firstName, ' ', r.customer.lastName) LIKE %?1%")
	public Page<Review> findAll(String keyword, Pageable pageable);
	
	public List<Review> findAll();
}
