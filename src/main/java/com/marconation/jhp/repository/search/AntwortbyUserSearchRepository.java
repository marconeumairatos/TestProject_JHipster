package com.marconation.jhp.repository.search;

import com.marconation.jhp.domain.AntwortbyUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link AntwortbyUser} entity.
 */
public interface AntwortbyUserSearchRepository extends ElasticsearchRepository<AntwortbyUser, Long> {
}
