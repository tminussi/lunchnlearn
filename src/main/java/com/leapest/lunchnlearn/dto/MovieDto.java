package com.leapest.lunchnlearn.dto;

import com.leapest.lunchnlearn.model.Movie;

import java.time.LocalDateTime;

public class MovieDto {

    private String id;

    private String title;

    private Integer releaseYear;

    private String genre;

    public MovieDto() {
    }

    public MovieDto(String id, String title, Integer releaseYear, String genre) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public static Movie toEntity(MovieDto movieDto) {
        return new Movie(movieDto.id, movieDto.title, movieDto.releaseYear, movieDto.genre);
    }

    public static MovieDto fromEntity(Movie movie) {
        return new MovieDto(movie.getId(), movie.getTitle(), movie.getReleaseYear(), movie.getGenre());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
