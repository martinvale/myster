package com.ibiscus.myster.repository.survey.data;

import com.ibiscus.myster.model.survey.data.Response;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ResponseRepository extends CrudRepository<Response, Long> {

    @Query("select r from response r where r.assignmentId = :assignmentId and r.surveyItemId = :surveyItemId")
    Response findByAssignment(@Param("assignmentId") long assignmentId, @Param("surveyItemId") long surveyItemId);
}
