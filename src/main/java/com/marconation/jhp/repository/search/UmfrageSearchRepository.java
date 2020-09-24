package com.marconation.jhp.repository.search;

import com.marconation.jhp.domain.Umfrage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Umfrage} entity.
 */
public interface UmfrageSearchRepository extends ElasticsearchRepository<Umfrage, Long> {
}
