package com.ibiscus.myster.service.shopper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ibiscus.myster.model.security.User;
import com.ibiscus.myster.model.shopper.Shopper;
import com.ibiscus.myster.service.security.UserService;
import com.ibiscus.myster.service.user.UserBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:services.xml", "classpath:persistence.xml"})
public class ShopperServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ShopperService shopperService;

    @Test
    public void crud() {
        User user = new UserBuilder().build();
        userService.save(user);

        Shopper shopper = new ShopperBuilder()
            .withUserId(user.getId())
            .build();
        shopper = shopperService.save(shopper);
        assertTrue(shopper.getId() > 0);

        Shopper storedShopper = shopperService.get(shopper.getId());
        assertEquals(shopper.getUserId(), storedShopper.getUserId());
        assertEquals(shopper.getLastName(), storedShopper.getLastName());
        assertEquals(shopper.getFirstName(), storedShopper.getFirstName());

        String newFirstName = getFirstName();
        String newLastName = getLastName();
        shopper = new ShopperBuilder()
            .withId(storedShopper.getId())
            .withUserId(user.getId())
            .withFirstName(newFirstName)
            .withLastName(newLastName)
            .build();
        shopperService.save(shopper);
        storedShopper = shopperService.get(shopper.getId());
        assertEquals(newFirstName, storedShopper.getFirstName());
        assertEquals(newLastName, storedShopper.getLastName());
    }

    private String getFirstName() {
        return new RandomStringGenerator.Builder().withinRange('a', 'z').build()
                .generate(5);
    }

    private String getLastName() {
        return new RandomStringGenerator.Builder().withinRange('a', 'z').build()
                .generate(5);
    }
}
