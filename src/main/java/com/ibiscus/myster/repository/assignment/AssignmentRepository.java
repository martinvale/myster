package com.ibiscus.myster.repository.assignment;

import org.springframework.data.repository.CrudRepository;

import com.ibiscus.myster.model.survey.Assignment;

import java.util.List;

public interface AssignmentRepository extends CrudRepository<Assignment, Long> {

    List<Assignment> findByShopperId(long shopperId);

}
