package com.facelift.shipping;

import org.springframework.data.repository.CrudRepository;

import com.facelift.common.entity.Country;
import com.facelift.common.entity.ShippingRate;

import java.security.Principal;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {
	
	public ShippingRate findByCountryAndState(Country country, String state);

//	public ShippingRate GuestShipping(String state);

}
