package com.leapest.lunchnlearn.controller;

import com.leapest.lunchnlearn.dto.MovieDto;
import com.leapest.lunchnlearn.model.Movie;
import com.leapest.lunchnlearn.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<ResponseEntity<MovieDto>> findById(@PathVariable String id) {
        return this.movieService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<MovieDto>> update(@PathVariable String id, @RequestBody MovieDto movieDto) {
        return this.movieService.updatedAt(id, movieDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Movie> create(@RequestBody MovieDto movieDto) {
        return this.movieService.save(movieDto);
    }
}
