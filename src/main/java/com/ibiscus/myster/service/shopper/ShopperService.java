package com.ibiscus.myster.service.shopper;

import com.ibiscus.myster.model.shopper.Shopper;

public interface ShopperService {

    Shopper get(long id);

    Shopper save(Shopper user);

}
