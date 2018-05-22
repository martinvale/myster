package com.ibiscus.myster.service.report;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class ReportService {

    public static final String CATEGORY_SUMMARY_SQL = "select c.id, c.name, count(r.id) as count, sum(ch.value) as total, sum(ch.value) / count(r.id) as porcentage\n" +
            "from survey s, assignment a, category c, survey_item si, choice ch, response r\n" +
            "where\n" +
            "\ts.id = a.survey_id\n" +
            "\tand s.id = c.survey_id\n" +
            "\tand c.id = si.category_id\n" +
            "\tand si.id = ch.survey_item_id\n" +
            "\tand ch.id = r.choice_id\n" +
            "\tand s.id = ?\n" +
            "\tand a.state = 'CLOSED'\n" +
            "\tand a.visit_date >= ?\n" +
            "\tand a.visit_date < ?\n" +
            "group by\n" +
            "\ts.id, c.id";

    private final JdbcTemplate template;

    public ReportService(JdbcTemplate template) {
        this.template = template;
    }

    public List<Map<String, Object>> getCategorySummary(long surveyId, MonthInterval interval) {
        List<Map<String, Object>> categorySummary = template.queryForList(CATEGORY_SUMMARY_SQL, surveyId,
                interval.getInitialDate(), interval.getFinalDate());
        MonthInterval previousMonthInterval = interval.getPreviousMonthInterval();
        List<Map<String, Object>> previousCategorySummary = template.queryForList(CATEGORY_SUMMARY_SQL, surveyId,
                previousMonthInterval.getInitialDate(), previousMonthInterval.getFinalDate());
        categorySummary.stream().forEach(categoryItem -> categoryItem.put("previousPorcentage",
                getPorcentage(categoryItem.get("id"), previousCategorySummary)));
        return categorySummary;
    }

    private Object getPorcentage(Object id, List<Map<String, Object>> previousCategorySummary) {
        return previousCategorySummary.stream()
                .filter(categoryItem -> categoryItem.get("id").equals(id))
                .map(categoryItem -> categoryItem.get("porcentage"))
                .findFirst()
                .orElse(0);
    }
}
