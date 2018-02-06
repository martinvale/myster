package com.ibiscus.myster.repository.shopper;

import org.springframework.data.repository.CrudRepository;

import com.ibiscus.myster.model.shopper.Shopper;

public interface ShopperRepository extends CrudRepository<Shopper, Long> {

    Shopper getByUserId(long userId);

}
