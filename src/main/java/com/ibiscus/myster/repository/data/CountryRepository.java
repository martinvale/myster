package com.ibiscus.myster.repository.data;

import com.ibiscus.myster.model.company.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {
}
