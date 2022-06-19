package com.facelift.setting;

import org.springframework.data.repository.CrudRepository;

import com.facelift.common.entity.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, Integer> {

}
