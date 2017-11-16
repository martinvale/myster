package com.ibiscus.myster.service.shopper;

import com.ibiscus.myster.model.shopper.Shopper;
import com.ibiscus.myster.repository.shopper.ShopperRepository;

public class ShopperServiceImpl implements ShopperService {

    private final ShopperRepository shopperRepository;

    public ShopperServiceImpl(ShopperRepository shopperRepository) {
        this.shopperRepository = shopperRepository;
    }

    @Override
    public Shopper get(long id) {
        return shopperRepository.findOne(id);
    }

    @Override
    public Shopper save(Shopper shopper) {
        return shopperRepository.save(shopper);
    }
}
