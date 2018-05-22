package com.ibiscus.myster.service.report;

import com.google.common.collect.ImmutableMap;
import com.ibiscus.myster.configuration.ServiceConfig;
import com.ibiscus.myster.configuration.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.ibiscus.myster.service.report.MonthInterval.currentMonthInterval;
import static org.apache.commons.lang3.RandomUtils.nextFloat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, TestConfig.class})
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private JdbcTemplate template;

    @Test
    public void getCategorySummary() throws Exception {
        long surveyId = getSurveyId();
        int categoriesCount = getCategoriesCount(surveyId);

        List<Map<String, Object>> categorySummary = reportService.getCategorySummary(surveyId, currentMonthInterval());

        assertEquals(categoriesCount, categorySummary.size());
    }

    private int getCategoriesCount(long surveyId) {
        List<Map<String, Object>> categorySummaryResults = newArrayList();
        categorySummaryResults.add(getSummaryData("category 1"));
        categorySummaryResults.add(getSummaryData("category 2"));
        categorySummaryResults.add(getSummaryData("category 3"));

        MonthInterval monthInterval = currentMonthInterval();
        when(template.queryForList(ReportService.CATEGORY_SUMMARY_SQL, surveyId, monthInterval.getInitialDate(),
                monthInterval.getFinalDate())).thenReturn(categorySummaryResults);
        return categorySummaryResults.size();
    }

    private Map<String, Object> getSummaryData(String categoryName) {
        Map<String, Object> summaryData = newHashMap();
        summaryData.put("name", categoryName);
        summaryData.put("porcentage", nextFloat(0, 100));
        return summaryData;
    }

    private long getSurveyId() {
        return 10;
    }

}