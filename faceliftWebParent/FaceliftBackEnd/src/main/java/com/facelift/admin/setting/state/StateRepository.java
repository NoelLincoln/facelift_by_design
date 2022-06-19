package com.facelift.admin.setting.state;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.facelift.common.entity.Country;
import com.facelift.common.entity.State;

public interface StateRepository extends CrudRepository<State, Integer> {
	
	public List<State> findByCountryOrderByNameAsc(Country country);
}
