package com.ibiscus.myster.service.survey;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ibiscus.myster.model.survey.Survey;
import com.ibiscus.myster.service.survey.SurveyServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:services.xml", "classpath:persistence.xml"})
public class SurveyServiceTest {

    @Autowired
    private SurveyServiceImpl pollService;

    @Test
    public void crud() {
        Survey poll = new SurveyBuilder().build();
        poll = pollService.save(poll);
        assertTrue(poll.getId() > 0);

        Survey storedPoll = pollService.get(poll.getId());
        assertEquals(poll.getName(), storedPoll.getName());
        assertEquals(poll.isEnabled(), storedPoll.isEnabled());

        String newName = getPollName();
        boolean enabled = !storedPoll.isEnabled();
        poll = new SurveyBuilder()
            .withId(storedPoll.getId())
            .withName(newName)
            .withEnabled(enabled)
            .build();
        pollService.save(poll);
        storedPoll = pollService.get(poll.getId());
        assertEquals(newName, storedPoll.getName());
        assertEquals(enabled, storedPoll.isEnabled());

        pollService.delete(storedPoll);
        storedPoll = pollService.get(poll.getId());
        assertNull(storedPoll);
    }

    private String getPollName() {
        return new RandomStringGenerator.Builder().withinRange('a', 'z').build()
                .generate(5);
    }
}
