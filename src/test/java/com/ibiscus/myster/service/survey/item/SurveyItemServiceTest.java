package com.ibiscus.myster.service.survey.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.ibiscus.myster.service.survey.SurveyService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ibiscus.myster.model.survey.Survey;
import com.ibiscus.myster.model.survey.item.Choice;
import com.ibiscus.myster.service.survey.SurveyBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:services.xml", "classpath:persistence.xml"})
public class SurveyItemServiceTest {

    @Autowired
    private ItemOptionService itemOptionService;

    @Autowired
    private SurveyService pollService;

    private Survey poll;

    @Before
    public void setUp() {
        poll = getPoll();
    }

    @After
    public void release() {
        pollService.delete(poll);
    }

    @Test
    public void crud() {
        /*Survey poll = getPoll();

        SurveyItem singleChoice = new SingleChoiceBuilder()
            .withCategoryId(poll.getId())
            .build();
        singleChoice = itemOptionService.save(singleChoice);
        assertTrue(singleChoice.getId() > 0);
        assertTrue(((SingleChoice) singleChoice).getChoices().isEmpty());

        List<Choice> choices = getChoices(singleChoice.getId());
        singleChoice = new SingleChoiceBuilder()
            .withId(singleChoice.getId())
            .withCategoryId(singleChoice.getCategoryId())
            .withChoices(choices)
            .build();

        itemOptionService.save(singleChoice);
        singleChoice = itemOptionService.get(singleChoice.getId());
        assertEquals(poll.getId(), singleChoice.getCategoryId());
        assertEquals(choices.size(), ((SingleChoice) singleChoice).getChoices().size());

        choices = getChoices(singleChoice.getId());
        singleChoice = new SingleChoiceBuilder()
            .withId(singleChoice.getId())
            .withCategoryId(singleChoice.getCategoryId())
            .withChoices(choices)
            .build();

        itemOptionService.save(singleChoice);
        singleChoice = itemOptionService.get(singleChoice.getId());
        assertEquals(poll.getId(), singleChoice.getCategoryId());
        assertEquals(choices.size(), ((SingleChoice) singleChoice).getChoices().size());

        itemOptionService.delete(singleChoice);
        singleChoice = itemOptionService.get(singleChoice.getId());
        assertNull(singleChoice);*/
    }

    private Survey getPoll() {
        Survey poll = new SurveyBuilder().build();
        return pollService.save(poll);
    }

    private List<Choice> getChoices(long itemOptionId) {
        List<Choice> choices = new ArrayList<Choice>();
        for (int i = 0; i < 3; i++) {
            Choice choice = new ChoiceBuilder()
                .withItemOptionId(itemOptionId)
                .build();
            choices.add(choice);
        }
        return choices;
    }

}
