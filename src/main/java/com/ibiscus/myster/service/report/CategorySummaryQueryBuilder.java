package com.ibiscus.myster.service.report;

public class CategorySummaryQueryBuilder {

    private final ReportCriteria criteria;

    public CategorySummaryQueryBuilder(ReportCriteria criteria) {
        this.criteria = criteria;
    }

    public String build() {
        StringBuilder countQuery = new StringBuilder("select count(a.id) ")
                .append("from assignment a, point_of_sale pos, location l ")
                .append("where a.survey_id = :surveyId and a.point_of_sale_id = pos.id ")
                .append("and pos.location_id = l.id and a.state = 'CLOSED' and a.visit_date >= :initialDate ")
                .append("and a.visit_date < :finalDate");
        criteria.getCode().ifPresent(code -> countQuery.append(" and pos.code = :code"));
        criteria.getName().ifPresent(name -> countQuery.append(" and pos.name = :name"));
        criteria.getStateId().ifPresent(stateId -> countQuery.append(" and l.state_id = :stateId"));
        StringBuilder sql = new StringBuilder("select c.id, c.name, sum(ch.value) / (")
                .append(countQuery)
                .append(") as average\n")
                .append("from assignment a, point_of_sale pos, location l, response r, choice ch, survey_item si, category c\n")
                .append("where\n")
                .append("\ta.id = r.assignment_id\n")
                .append("\tand a.point_of_sale_id = pos.id\n")
                .append("\tand pos.location_id = l.id\n")
                .append("\tand r.choice_id = ch.id\n")
                .append("\tand ch.survey_item_id = si.id\n")
                .append("\tand si.category_id = c.id\n")
                .append("\tand a.survey_id = :surveyId\n")
                .append("\tand a.state = 'CLOSED'\n")
                .append("\tand a.visit_date >= :initialDate\n")
                .append("\tand a.visit_date < :finalDate\n");
        criteria.getCode().ifPresent(s -> sql.append("\tand pos.code = :code\n"));
        criteria.getName().ifPresent(s -> sql.append("\tand pos.name = :name\n"));
        criteria.getStateId().ifPresent(s -> sql.append("\tand l.state_id = :stateId\n"));
        sql.append("group by c.id, c.name\n");
        sql.append("order by c.id");
        return sql.toString();
    }
}
