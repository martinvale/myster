package com.ibiscus.myster.service.report;

import com.ibiscus.myster.model.survey.category.Category;
import com.ibiscus.myster.model.survey.item.AbstractSurveyItem;
import com.ibiscus.myster.model.survey.item.Choice;
import com.ibiscus.myster.repository.category.CategoryRepository;
import com.ibiscus.myster.repository.survey.item.ChoiceRepository;
import com.ibiscus.myster.repository.survey.item.SurveyItemRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class ReportService {

    public static final String SURVEY_TOTAL = "select c.id, sum(ch.value) as total\n" +
            "from survey s, category c, survey_item si, choice ch\n" +
            "where\n" +
            "\ts.id = c.survey_id\n" +
            "\tand c.id = si.category_id\n" +
            "\tand si.id = ch.survey_item_id\n" +
            "\tand s.id = ?\n" +
            "group by\n" +
            "\tc.id";

    public static final String CATEGORY_SUMMARY_SQL = "select c.id, c.name, sum(ch.value) / (" +
            "\tselect count(id) from assignment where state = 'CLOSED' and visit_date >= ? and visit_date < ?) as average\n" +
            "from assignment a, response r, choice ch, survey_item si, category c\n" +
            "where\n" +
            "\ta.id = r.assignment_id\n" +
            "\tand r.choice_id = ch.id\n" +
            "\tand ch.survey_item_id = si.id\n" +
            "\tand si.category_id = c.id\n" +
            "\tand a.survey_id = ?\n" +
            "\tand a.state = 'CLOSED'\n" +
            "\tand a.visit_date >= ?\n" +
            "\tand a.visit_date < ?\n" +
            "group by c.name\n" +
            "order by c.id\n";

    public static final String CHOICES_SUMMARY_SQL = "select ch.id, ch.description, count(ch.id) as count, count(ch.id) / (" +
            "\tselect count(id) from assignment where state = 'CLOSED' and visit_date >= ? and visit_date < ?) * 100 as percentage\n" +
            "from assignment a, response r, choice ch, survey_item si, category c\n" +
            "where\n" +
            "\ta.id = r.assignment_id\n" +
            "\tand r.choice_id = ch.id\n" +
            "\tand ch.survey_item_id = si.id\n" +
            "\tand si.category_id = c.id\n" +
            "\tand a.survey_id = ?\n" +
            "\tand a.state = 'CLOSED'\n" +
            "\tand a.visit_date >= ?\n" +
            "\tand a.visit_date < ?\n" +
            "group by c.name, si.position, ch.id\n" +
            "order by c.id, si.position";

    public static final String TOP_PERFORMERS_SQL = "select a.id, pos.name, pos.address, sum(ch.value) as total\n" +
            "from assignment a, point_of_sale pos, response r, choice ch\n" +
            "where\n" +
            "\ta.point_of_sale_id = pos.id\n" +
            "\tand a.id = r.assignment_id\n" +
            "\tand r.choice_id = ch.id\n" +
            "\tand a.survey_id = ?\n" +
            "\tand a.state = 'CLOSED'\n" +
            "\tand a.visit_date >= ?\n" +
            "\tand a.visit_date < ?\n" +
            "group by a.id\n" +
            "order by sum(ch.value) desc\n" +
            "limit 3";

    private final JdbcTemplate template;

    private final CategoryRepository categoryRepository;

    private final SurveyItemRepository surveyItemRepository;

    private final ChoiceRepository choiceRepository;

    public ReportService(JdbcTemplate template, CategoryRepository categoryRepository,
                         SurveyItemRepository surveyItemRepository, ChoiceRepository choiceRepository) {
        this.template = template;
        this.categoryRepository = categoryRepository;
        this.surveyItemRepository = surveyItemRepository;
        this.choiceRepository = choiceRepository;
    }

    public List<Map<String, Object>> getCategorySummary(long surveyId, MonthInterval interval) {
        List<Map<String, Object>> surveyTotal = template.queryForList(SURVEY_TOTAL, surveyId);
        Map<Long, BigDecimal> totalValues = newHashMap();
        surveyTotal.stream()
                .forEach(totalValue -> totalValues.put((Long) totalValue.get("id"), (BigDecimal) totalValue.get("total")));

        List<Map<String, Object>> categorySummary = template.queryForList(CATEGORY_SUMMARY_SQL, interval.getInitialDate(),
                interval.getFinalDate(), surveyId, interval.getInitialDate(), interval.getFinalDate());
        MonthInterval previousMonthInterval = interval.getPreviousMonthInterval();
        List<Map<String, Object>> previousCategorySummary = template.queryForList(CATEGORY_SUMMARY_SQL,
                previousMonthInterval.getInitialDate(), previousMonthInterval.getFinalDate(), surveyId,
                previousMonthInterval.getInitialDate(), previousMonthInterval.getFinalDate());
        Map<Long, BigDecimal> previousTotal = newHashMap();
        previousCategorySummary.stream()
                .forEach(totalValue -> previousTotal.put((Long) totalValue.get("id"), (BigDecimal) totalValue.get("total")));
        categorySummary.stream()
                .forEach(categoryItem -> completeReport(categoryItem, totalValues, previousTotal));
        return categorySummary;
    }

    public List<CategoryResults> getAnswerSummary(long surveyId, MonthInterval interval) {
        List<Map<String, Object>> choicesSummary = template.queryForList(CHOICES_SUMMARY_SQL, interval.getInitialDate(),
                interval.getFinalDate(), surveyId, interval.getInitialDate(), interval.getFinalDate());
        Map<Long, ChoiceResults> choicesResults = choicesSummary.stream()
                .collect(toMap(x -> (Long) x.get("id"),
                        x -> new ChoiceResults(x.get("description").toString(),
                                ((BigDecimal) x.get("percentage")).floatValue(), ((Long) x.get("count")).intValue())));

        List<Category> categories = categoryRepository.findBySurveyId(surveyId);
        return categories.stream()
                .map(category -> new CategoryResults(category.getName(),
                        getSurveyItemsResults(category.getId(), choicesResults)))
                .collect(toList());
    }

    private List<SurveyItemResults> getSurveyItemsResults(long categoryId, Map<Long, ChoiceResults> choicesResults) {
        List<AbstractSurveyItem> surveyItems = surveyItemRepository.findByCategoryIdOrderByPositionAsc(categoryId);
        return surveyItems.stream()
                .map(surveyItem -> new SurveyItemResults(surveyItem.getTitle(),
                        getChoiceResults(surveyItem.getId(), choicesResults)))
                .collect(toList());
    }

    private List<ChoiceResults> getChoiceResults(long surveyItemId, Map<Long, ChoiceResults> choicesResults) {
        List<Choice> choices = choiceRepository.findBySurveyItemId(surveyItemId);
        return choices.stream()
                .map(choice -> Optional.ofNullable(choicesResults.get(choice.getId()))
                    .orElse(new ChoiceResults(choice.getDescription())))
                .collect(toList());
    }

    public List<Map<String, Object>> getTopPerformers(long surveyId, MonthInterval interval) {
        List<Map<String, Object>> surveyTotal = template.queryForList(SURVEY_TOTAL, surveyId);
        BigDecimal totalValue = BigDecimal.valueOf(surveyTotal.stream()
                .mapToInt(categoryRow -> ((BigDecimal) categoryRow.get("total")).intValue())
                .sum());
        List<Map<String, Object>> topPerformersResult = template.queryForList(TOP_PERFORMERS_SQL, surveyId,
                interval.getInitialDate(), interval.getFinalDate());
        topPerformersResult.stream()
                .forEach(performerData -> performerData.put("percentage",
                        getPercentage(totalValue, (BigDecimal) performerData.get("total"))));
        return topPerformersResult;
    }

    public int getTotalScore(long surveyId) {
        List<Map<String, Object>> surveyTotal = template.queryForList(SURVEY_TOTAL, surveyId);
        return surveyTotal.stream()
                .mapToInt(categoryRow -> ((BigDecimal) categoryRow.get("total")).intValue())
                .sum();
    }

    private BigDecimal getPercentage(BigDecimal totalSurvey, BigDecimal totalResponse) {
        return totalResponse.multiply(BigDecimal.valueOf(100)).divide(totalSurvey, 2, BigDecimal.ROUND_HALF_UP);
    }

    private void completeReport(Map<String, Object> categoryItem, Map<Long, BigDecimal> surveyTotal,
                                Map<Long, BigDecimal> previousCategorySummary) {
        Long categoryId = (Long) categoryItem.get("id");
        BigDecimal total = surveyTotal.get(categoryId);
        categoryItem.put("percentage", ((BigDecimal) categoryItem.get("average")).multiply(BigDecimal.valueOf(100))
                .divide(total, 2, BigDecimal.ROUND_HALF_UP));
        Optional<BigDecimal> previousMonthTotal = Optional.ofNullable(previousCategorySummary.get(categoryId));
        categoryItem.put("previousPercentage", previousMonthTotal.orElse(BigDecimal.ZERO).multiply(BigDecimal.valueOf(100))
                .divide(total, 2, BigDecimal.ROUND_HALF_UP));
    }

}
