package com.ibiscus.myster.service.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ibiscus.myster.model.security.User;
import com.ibiscus.myster.service.security.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:services.xml", "classpath:persistence.xml"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void crud() {
        User user = new UserBuilder().build();
        user = userService.save(user);
        assertTrue(user.getId() > 0);

        User storedUser = userService.get(user.getId());
        assertEquals(user.getUsername(), storedUser.getUsername());
        assertEquals(user.getPassword(), storedUser.getPassword());
        assertEquals(user.isEnabled(), storedUser.isEnabled());

        String newName = getUsername();
        String newPassword = getPassword();
        boolean enabled = !storedUser.isEnabled();
        user = new UserBuilder()
            .withId(storedUser.getId())
            .withUsername(newName)
            .withPassword(newPassword)
            .withEnabled(enabled)
            .build();
        userService.save(user);
        storedUser = userService.get(user.getId());
        assertEquals(newName, storedUser.getUsername());
        assertEquals(newPassword, storedUser.getPassword());
        assertEquals(enabled, storedUser.isEnabled());
    }

    private String getUsername() {
        return new RandomStringGenerator.Builder().withinRange('a', 'z').build()
                .generate(5);
    }

    private String getPassword() {
        return new RandomStringGenerator.Builder().withinRange('a', 'z').build()
                .generate(5);
    }
}
