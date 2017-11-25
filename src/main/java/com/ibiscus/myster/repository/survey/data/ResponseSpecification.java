package com.ibiscus.myster.repository.survey.data;

import com.ibiscus.myster.model.survey.Assignment;
import com.ibiscus.myster.model.survey.data.Response;
import com.ibiscus.myster.model.survey.item.SurveyItem;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class ResponseSpecification {

    public static Specification<Response> belongsTo(Assignment assignment, long surveyItemId) {
        return new Specification<Response>() {
            @Override
            public Predicate toPredicate(Root<Response> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate assignmentPredicate = cb.equal(root.get("assignmentId"), assignment.getId());
                Predicate surveyItemPredicate = cb.equal(root.get("surveyItemId"), surveyItemId);
                return cb.and(assignmentPredicate, surveyItemPredicate);
            }
        };
    }
}
