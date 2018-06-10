package com.ibiscus.myster.service.report;

import com.google.common.collect.ImmutableMap;
import com.ibiscus.myster.configuration.ServiceConfig;
import com.ibiscus.myster.configuration.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.ibiscus.myster.service.report.ReportCriteriaBuilder.newReportCriteriaBuilder;
import static com.ibiscus.myster.service.report.ReportService.MAXIMUM_VALUE_PER_ITEM_QUERY;
import static org.apache.commons.lang3.RandomUtils.nextFloat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, TestConfig.class})
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Test
    public void getCategorySummary() throws Exception {
        ReportCriteria criteria = getReportCriteria();
        int categoriesCount = getCategoriesCount(criteria);

        List<Map<String, Object>> categorySummary = reportService.getCategorySummary(criteria);

        assertEquals(categoriesCount, categorySummary.size());
    }

    private ReportCriteria getReportCriteria() {
        return newReportCriteriaBuilder(10L).build();
    }

    private int getCategoriesCount(ReportCriteria criteria) {
        List<Map<String, Object>> surveyTotal = newArrayList();
        surveyTotal.add(ImmutableMap.of("categoryId", 1L, "maximum", 2000L));
        surveyTotal.add(ImmutableMap.of("categoryId", 2L, "maximum", 1000L));
        surveyTotal.add(ImmutableMap.of("categoryId", 3L, "maximum", 1500L));

        when(template.queryForList(eq(MAXIMUM_VALUE_PER_ITEM_QUERY), anyMap())).thenReturn(surveyTotal);

        List<Map<String, Object>> categorySummaryResults = newArrayList();
        categorySummaryResults.add(getSummaryData(1L, "category 1"));
        categorySummaryResults.add(getSummaryData(2L, "category 2"));
        categorySummaryResults.add(getSummaryData(3L, "category 3"));

        String categorySummaryQuery = new CategorySummaryQueryBuilder(criteria).build();
        when(template.queryForList(eq(categorySummaryQuery), anyMap())).thenReturn(categorySummaryResults);
        return categorySummaryResults.size();
    }

    private Map<String, Object> getSummaryData(Long id, String categoryName) {
        Map<String, Object> summaryData = newHashMap();
        summaryData.put("id", id);
        summaryData.put("name", categoryName);
        summaryData.put("average", new BigDecimal(nextFloat(0, 100)));
        return summaryData;
    }

}