package com.marconation.jhp.repository.search;

import com.marconation.jhp.domain.Userantwort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link UserAntwort} entity.
 */
public interface UserantwortSearchRepository extends ElasticsearchRepository<Userantwort, Long> {
}
