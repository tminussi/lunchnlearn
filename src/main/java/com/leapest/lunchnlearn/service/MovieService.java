package com.leapest.lunchnlearn.service;

import com.leapest.lunchnlearn.dto.MovieDto;
import com.leapest.lunchnlearn.exception.MovieAlreadyExistsException;
import com.leapest.lunchnlearn.exception.MovieNotFoundException;
import com.leapest.lunchnlearn.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Flux<MovieDto> findAll() {
        return this.movieRepository.findAll()
                .take(2)
                .flatMap(movie -> Flux.just(MovieDto.fromEntity(movie)))
                .doOnEach(System.out::println)
                .doOnComplete(() -> System.out.println("Stream is over"));
    }

    public Mono<MovieDto> findById(String id) {
        return this.movieRepository.findById(id)
                .map(MovieDto::fromEntity)
                .switchIfEmpty(Mono.error(new MovieNotFoundException("The given movie does not exist")));
    }

    public Mono<MovieDto> save(MovieDto movieDto) {
        return this.movieRepository.findOneByTitle(movieDto.getTitle())
                .flatMap(movie -> Mono.error(new MovieAlreadyExistsException("The movie with this title already exists. Please choose another one")))
                .then(movieRepository.save(MovieDto.toEntity(movieDto)))
                .map(MovieDto::fromEntity)
                .doOnError(e -> System.out.println(e.getMessage()))
                .doFinally(System.out::println);
    }

    public Mono<MovieDto> update(String id, MovieDto movieDto) {
        return this.movieRepository.findById(id)
                .flatMap(movie -> {
                    movie.setGenre(movieDto.getGenre());
                    movie.setReleaseYear(movieDto.getReleaseYear());
                    movie.setTitle(movieDto.getTitle());
                    return this.movieRepository.save(movie);
                })
                .map(MovieDto::fromEntity)
                .switchIfEmpty(Mono.error(new MovieNotFoundException("The given movie does not exist")));
    }
}
