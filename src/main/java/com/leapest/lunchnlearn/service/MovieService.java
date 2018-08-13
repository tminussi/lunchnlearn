package com.leapest.lunchnlearn.service;

import com.leapest.lunchnlearn.dto.MovieDto;
import com.leapest.lunchnlearn.exception.MovieAlreadyExistsException;
import com.leapest.lunchnlearn.exception.MovieException;
import com.leapest.lunchnlearn.model.Movie;
import com.leapest.lunchnlearn.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Flux<MovieDto> findAll() {
        return this.movieRepository.findAll()
                .doOnEach(System.out::println)
                .doOnComplete(() -> System.out.println("Stream is over"))
                .filter(movie -> movie.getTitle().contains("2"))
                .map(MovieDto::fromEntity);
    }

    public Mono<MovieDto> findById(String id) {
        return this.movieRepository.findById(id)
                .map(MovieDto::fromEntity);
    }

    public Mono<Movie> save(MovieDto movieDto) {
        return this.movieRepository.findOneByTitle(movieDto.getTitle())
                .flatMap(movie -> Mono.error(new MovieAlreadyExistsException("The movie with this title already exists. Please choose another one")))
                .then(movieRepository.save(MovieDto.toEntity(movieDto)));
    }

    public Mono<MovieDto> update(String id, MovieDto movieDto) {
        return this.movieRepository.findById(id)
                .flatMap(movie -> {
                    movie.setGenre(movieDto.getGenre());
                    movie.setReleaseYear(movieDto.getReleaseYear());
                    movie.setTitle(movieDto.getTitle());
                    return this.movieRepository.save(movie);
                })
                .map(MovieDto::fromEntity);
    }

    public Mono<MovieDto> updatedAt(String id, MovieDto movieDto) {
        return this.movieRepository.findById(id)
                .flatMap(movie -> {
                    movie.setGenre(movieDto.getGenre());
                    movie.setReleaseYear(movieDto.getReleaseYear());
                    movie.setTitle(movieDto.getTitle());
                    movie.setUpdatedAt(LocalDateTime.now());
                    return this.movieRepository.save(movie);
                })
                .map(MovieDto::fromEntity)
                .onErrorMap(exception -> new MovieException(exception.getMessage()));
    }
}
