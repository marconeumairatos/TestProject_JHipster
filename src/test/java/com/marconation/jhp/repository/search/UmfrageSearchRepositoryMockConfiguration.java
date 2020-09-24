package com.marconation.jhp.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link UmfrageSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class UmfrageSearchRepositoryMockConfiguration {

    @MockBean
    private UmfrageSearchRepository mockUmfrageSearchRepository;

}
