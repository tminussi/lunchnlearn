package com.leapest.lunchnlearn.repository;

import com.leapest.lunchnlearn.model.Movie;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movie, String>{

    Mono<Movie> findOneByTitle(String title);
}
