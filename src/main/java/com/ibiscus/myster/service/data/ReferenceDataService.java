package com.ibiscus.myster.service.data;

import com.ibiscus.myster.model.company.Country;
import com.ibiscus.myster.model.company.State;
import com.ibiscus.myster.repository.data.CountryRepository;
import com.ibiscus.myster.repository.data.StateRepository;

public class ReferenceDataService {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;

    public ReferenceDataService(CountryRepository countryRepository, StateRepository stateRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
    }

    public Iterable<Country> getCountries() {
        return countryRepository.findAll();
    }

    public Iterable<State> getStatesByCountry(Long countryId) {
        return stateRepository.findByCountryId(countryId);
    }
}
