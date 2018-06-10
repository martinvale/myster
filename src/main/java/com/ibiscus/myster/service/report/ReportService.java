package com.ibiscus.myster.service.report;

import com.ibiscus.myster.model.survey.item.AbstractSurveyItem;
import com.ibiscus.myster.model.survey.item.Choice;
import com.ibiscus.myster.repository.category.CategoryRepository;
import com.ibiscus.myster.repository.survey.item.ChoiceRepository;
import com.ibiscus.myster.repository.survey.item.SurveyItemRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.ImmutableMap.of;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.stream.Collectors.toList;

public class ReportService {

    public static final String MAXIMUM_VALUE_PER_ITEM_QUERY = "select c.id as categoryId, si.id as itemId, " +
            "(select max(ch.value) from choice ch where si.id = ch.survey_item_id) as maximum\n" +
            "from survey s, category c, survey_item si, choice ch\n" +
            "where\n" +
            "\ts.id = c.survey_id\n" +
            "\tand c.id = si.category_id\n" +
            "\tand si.id = ch.survey_item_id\n" +
            "\tand s.id = :surveyId\n" +
            "group by c.id, si.id";

    private final NamedParameterJdbcTemplate template;

    private final CategoryRepository categoryRepository;

    private final SurveyItemRepository surveyItemRepository;

    private final ChoiceRepository choiceRepository;

    public ReportService(NamedParameterJdbcTemplate template, CategoryRepository categoryRepository,
                         SurveyItemRepository surveyItemRepository, ChoiceRepository choiceRepository) {
        this.template = template;
        this.categoryRepository = categoryRepository;
        this.surveyItemRepository = surveyItemRepository;
        this.choiceRepository = choiceRepository;
    }

    public List<Map<String, Object>> getCategorySummary(ReportCriteria criteria) {
        ValueCollector maximumPerCategory = getMaximumPerCategory(criteria.getSurveyId());

        String categorySummarySQL = getCategorySummaryQuery(criteria);
        Map<String, Object> parameters = getQueryParameters(criteria);
        List<Map<String, Object>> categorySummary = template.queryForList(categorySummarySQL, parameters);

        parameters = getPreviousIntervalQueryParameters(criteria);
        List<Map<String, Object>> previousCategorySummary = template.queryForList(categorySummarySQL, parameters);

        Map<Long, BigDecimal> previousTotal = newHashMap();
        previousCategorySummary.stream()
                .forEach(totalValue -> previousTotal.put((Long) totalValue.get("id"), (BigDecimal) totalValue.get("total")));
        categorySummary.stream()
                .forEach(categoryItem -> completeReport(categoryItem, maximumPerCategory, previousTotal));
        return categorySummary;
    }

    private ValueCollector getMaximumPerCategory(Long surveyId) {
        List<Map<String, Object>> maximumPerItems = template.queryForList(MAXIMUM_VALUE_PER_ITEM_QUERY,
                of("surveyId", surveyId));
        ValueCollector maximumPerCategory = new ValueCollector();
        maximumPerItems.stream()
                .forEach(totalValue -> maximumPerCategory.addValue((Long) totalValue.get("categoryId"),
                        (Long) totalValue.get("maximum")));
        return maximumPerCategory;
    }

    private Map<String, Object> getPreviousIntervalQueryParameters(ReportCriteria criteria) {
        ReportCriteria previousIntervalCriteria = new ReportCriteriaBuilder(criteria.getSurveyId())
                .usingCriteria(criteria)
                .withInterval(criteria.getMonthInterval().getPreviousMonthInterval())
                .build();
        return getQueryParameters(previousIntervalCriteria);
    }

    private Map<String, Object> getQueryParameters(ReportCriteria criteria) {
        Map<String, Object> parameters = newHashMap();
        parameters.put("surveyId", criteria.getSurveyId());
        parameters.put("initialDate", criteria.getMonthInterval().getInitialDate());
        parameters.put("finalDate", criteria.getMonthInterval().getFinalDate());
        criteria.getCode().ifPresent(code -> parameters.put("code", code));
        criteria.getName().ifPresent(name -> parameters.put("name", name));
        return parameters;
    }

    private String getCategorySummaryQuery(ReportCriteria criteria) {
        return new CategorySummaryQueryBuilder(criteria)
                .build();
    }

    public List<CategoryResults> getAnswerSummary(long surveyId, MonthInterval interval) {
        /*List<Map<String, Object>> choicesSummary = template.queryForList(CHOICES_SUMMARY_SQL, interval.getInitialDate(),
                interval.getFinalDate(), surveyId, interval.getInitialDate(), interval.getFinalDate());
        Map<Long, ChoiceResults> choicesResults = choicesSummary.stream()
                .collect(toMap(x -> (Long) x.get("id"),
                        x -> new ChoiceResults(x.get("description").toString(),
                                ((BigDecimal) x.get("percentage")).floatValue(), ((Long) x.get("count")).intValue())));

        List<Category> categories = categoryRepository.findBySurveyId(surveyId);
        return categories.stream()
                .map(category -> new CategoryResults(category.getName(),
                        getSurveyItemsResults(category.getId(), choicesResults)))
                .collect(toList());*/
        return newArrayList();
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

    public List<Map<String, Object>> getTopPerformers(ReportCriteria criteria) {
        BigDecimal totalValue = BigDecimal.valueOf(getTotalScore(criteria.getSurveyId()));
        List<Map<String, Object>> topPerformersResult = template.queryForList(getTopPerformersSql(criteria),
                getQueryParameters(criteria));
        topPerformersResult.stream()
                .forEach(performerData -> performerData.put("percentage",
                        getPercentage(totalValue, (BigDecimal) performerData.get("total"))));
        return topPerformersResult;
    }

    private String getTopPerformersSql(ReportCriteria criteria) {
        StringBuilder sql = new StringBuilder("select a.id, pos.name, l.address, sum(ch.value) as total")
                .append(" from assignment a, point_of_sale pos, location l, response r, choice ch")
                .append(" where")
                .append("\ta.point_of_sale_id = pos.id")
                .append("\tand pos.location_id = l.id")
                .append("\tand a.id = r.assignment_id")
                .append("\tand r.choice_id = ch.id")
                .append("\tand a.survey_id = :surveyId")
                .append("\tand a.state = 'CLOSED'")
                .append("\tand a.visit_date >= :initialDate")
                .append("\tand a.visit_date < :finalDate");
        criteria.getCode().ifPresent(s -> sql.append("\tand pos.code = :code"));
        criteria.getName().ifPresent(s -> sql.append("\tand pos.name = :name"));
        sql.append(" group by a.id");
        sql.append(" order by sum(ch.value) desc");
        sql.append(" limit 3");
        return sql.toString();
    }

    public Long getTotalScore(long surveyId) {
        List<Map<String, Object>> surveyTotal = template.queryForList(MAXIMUM_VALUE_PER_ITEM_QUERY, of("surveyId", surveyId));
        return surveyTotal.stream()
                .mapToLong(categoryRow -> (Long) categoryRow.get("maximum"))
                .sum();
    }

    private BigDecimal getPercentage(BigDecimal totalSurvey, BigDecimal totalResponse) {
        return totalResponse.multiply(BigDecimal.valueOf(100)).divide(totalSurvey, 2, BigDecimal.ROUND_HALF_UP);
    }

    private void completeReport(Map<String, Object> categoryItem, ValueCollector maximumPerCategory,
                                Map<Long, BigDecimal> previousCategorySummary) {
        Long categoryId = (Long) categoryItem.get("id");
        BigDecimal total = BigDecimal.valueOf(maximumPerCategory.getValue(categoryId));
        categoryItem.put("percentage", ((BigDecimal) categoryItem.get("average")).multiply(BigDecimal.valueOf(100))
                .divide(total, 2, BigDecimal.ROUND_HALF_UP));
        Optional<BigDecimal> previousMonthTotal = Optional.ofNullable(previousCategorySummary.get(categoryId));
        categoryItem.put("previousPercentage", previousMonthTotal.orElse(BigDecimal.ZERO).multiply(BigDecimal.valueOf(100))
                .divide(total, 2, BigDecimal.ROUND_HALF_UP));
    }

    private class ValueCollector {

        private Map<Long, Long> maximumsPerId = newHashMap();

        private void addValue(Long id, Long value) {
            Optional<Long> actualValue = Optional.ofNullable(maximumsPerId.get(id));
            maximumsPerId.put(id, value + actualValue.orElse(0L));
        }

        private Long getValue(Long id) {
            return Optional.ofNullable(maximumsPerId.get(id)).orElse(0L);
        }
    }
}
