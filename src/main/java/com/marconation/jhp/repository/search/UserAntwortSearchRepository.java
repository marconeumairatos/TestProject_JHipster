package com.marconation.jhp.repository.search;

import com.marconation.jhp.domain.UserAntwort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link UserAntwort} entity.
 */
public interface UserAntwortSearchRepository extends ElasticsearchRepository<UserAntwort, Long> {
}
