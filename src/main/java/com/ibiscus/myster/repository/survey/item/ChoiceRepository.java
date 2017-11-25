package com.ibiscus.myster.repository.survey.item;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ibiscus.myster.model.survey.item.Choice;

public interface ChoiceRepository extends CrudRepository<Choice, Long> {

    List<Choice> findByItemOptionId(long itemOptionId);

}
