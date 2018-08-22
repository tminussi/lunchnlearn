package com.leapest.lunchnlearn.controller;

import com.leapest.lunchnlearn.dto.MovieDto;
import com.leapest.lunchnlearn.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public Flux<MovieDto> allMovies() {
        return this.movieService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<MovieDto> findById(@PathVariable String id) {
        return this.movieService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<MovieDto> update(@PathVariable String id, @RequestBody MovieDto movieDto) {
        return this.movieService.update(id, movieDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<MovieDto>> create(@RequestBody MovieDto movieDto) {
        return this
                .movieService
                .save(movieDto)
                .map(movie -> ResponseEntity.created(URI.create("/movies/" + movie.getId()))
                .body(movie));
    }
}
