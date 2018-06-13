package com.ibiscus.myster.repository.data;

import com.ibiscus.myster.model.company.Country;
import com.ibiscus.myster.model.company.State;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StateRepository extends CrudRepository<State, Long> {

    Iterable<State> findByCountryId(Long countryId);

}
