package com.facelift.common.entity;

import javax.persistence.*;

import com.facelift.common.entity.product.Product;

import java.util.Date;

@Entity
@Table(name = "cart_items")
public class CartItem extends IdBasedEntity {
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")	
	private Product product;

	@Temporal(TemporalType.DATE)
	private Date date;
	private int quantity;
	
	@Transient
	private float shippingCost;

	private String sessionToken;


	public CartItem() {
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public String getSessionToken() {
		return sessionToken;
	}


	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	@Override
	public String toString() {
		return "CartItem [id=" + id + ", customer=" + customer.getFullName() + ", product=" + product.getShortName() + ", quantity=" + quantity
				+ "]";
	}

	@Transient
	public float getSubtotal() {
		return product.getDiscountPrice() * quantity;
	}

	@Transient
	public float getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(float shippingCost) {
		this.shippingCost = shippingCost;
	}
	
	
}
