package com.leapest.lunchnlearn.service;

import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public class FluxTest {

    private static final Flux<String> OLD_SCHOOL_LANGUAGES = Flux.just("c", "c++", "pascal", "fortran");

    private Flux<String> firstClassLanguages;
    private Flux<String> thirdClassLanguages;

    @Before
    public void setup() {
        this.firstClassLanguages = Flux.just("java", "kotlin", "nodejs");
        this.thirdClassLanguages = Flux.just("python", "ruby", "php");
    }

    @Test
    public void zip() {
        Flux.zip(firstClassLanguages, thirdClassLanguages, (firstClassLanguage, thirdClassLanguage) -> firstClassLanguage + "/" + thirdClassLanguage)
        .subscribe(System.out::println);
    }

    @Test
    public void zipWith() {
        firstClassLanguages.zipWith(thirdClassLanguages)
                .subscribe(System.out::println);

        Flux.concat(firstClassLanguages, thirdClassLanguages).subscribe(System.out::println);
    }

    @Test
    public void merge() {
        Flux.merge(firstClassLanguages, thirdClassLanguages).subscribe(System.out::println);
    }

    @Test
    public void mergeWith() {
        firstClassLanguages.mergeWith(thirdClassLanguages).subscribe(System.out::println);
    }

    @Test
    public void concat() {
        Flux.concat(firstClassLanguages, thirdClassLanguages).subscribe(System.out::println);
    }

    @Test
    public void onErrorResume() {
        firstClassLanguages
                .flatMap(language -> Flux.just(Integer.valueOf(language)))
                .map(String::valueOf)
                .onErrorResume(throwable -> {
                    throwable.printStackTrace();
                    return OLD_SCHOOL_LANGUAGES;
                })
                .subscribe(System.out::println);
    }

    @Test
    public void onErrorMap() {
        firstClassLanguages
                .flatMap(language -> Flux.just(Integer.valueOf(language)))
                .map(String::valueOf)
                .onErrorMap(throwable -> new RuntimeException("Something failed"))
                .subscribe(System.out::println);
    }

    @Test
    public void onErrorReturn() {
        firstClassLanguages
                .flatMap(language -> Flux.just(Integer.valueOf(language)))
                .map(String::valueOf)
                .onErrorReturn("I have to return an instance of T, which happens to be a String")
                .subscribe(System.out::println);
    }

    @Test
    public void defaultIfEmpty() {
        firstClassLanguages
                .flatMap(language -> Flux.empty())
                .defaultIfEmpty("Here I need to return an instance of T, which happens to be a String")
                .subscribe(System.out::println);
    }

    @Test
    public void switchIfEmpty() {
        firstClassLanguages
                .flatMap(language -> Flux.empty())
                .switchIfEmpty(Flux.just(LocalDateTime.now()))
                .subscribe(System.out::println);
    }

}