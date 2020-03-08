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
