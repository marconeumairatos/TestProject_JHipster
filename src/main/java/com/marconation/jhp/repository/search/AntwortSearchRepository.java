package com.marconation.jhp.repository.search;

import com.marconation.jhp.domain.Antwort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Antwort} entity.
 */
public interface AntwortSearchRepository extends ElasticsearchRepository<Antwort, Long> {
}
