/*
 * Copyright (c) Rakuten, Inc. All Rights Reserved.
 *
 * This program is the information assets which are handled as "Strictly Confidential".
 * Permission of Use is only admitted in Rakuten Inc Development Department.
 * If you don't have permission,
 * MUST not be published, broadcast, rewritten for broadcast or publication or redistributed
 * directly or indirectly in any medium.
 *
 */
package com.reactive.reactiveofficer.repo;

import com.reactive.reactiveofficer.entity.Officer;
import com.reactive.reactiveofficer.entity.Rank;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * @author yihan.a.chen
 */
public interface OfficerRepository extends ReactiveMongoRepository<Officer, String> {
    Flux<Officer> findByRank(Rank rank);

    Flux<Officer> findByLast(String last);
}
