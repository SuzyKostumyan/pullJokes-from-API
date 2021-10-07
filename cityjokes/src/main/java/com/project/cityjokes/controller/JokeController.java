/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.cityjokes.controller;

import com.project.cityjokes.model.Joke;
import com.project.cityjokes.service.JokeService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author suzy
 */
@RestController
@RequestMapping("/api/jokes")
@Slf4j

public class JokeController {

    @Autowired
    JokeService jokeService;
    @Autowired
    JokesIntoExcel jokesIntoExcel;

    @GetMapping(path = "/findAll/jokes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Joke> findAll() {
        return jokeService.findAll();
    }

    @PostMapping(path = "/requestBody", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> requestBody(@RequestBody Joke request) {
        return ResponseEntity.status(HttpStatus.OK).body(request);
    }

    @GetMapping(path = "/pull/jokes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity pullJokes() throws IOException {
        Joke foundJoke = jokeService.pullJokes();
        return ResponseEntity.status(HttpStatus.OK).body(foundJoke);
    }

    @GetMapping(path = "/findBySearch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findBySearch(@RequestParam String search) {
        List<Joke> foundJoke = jokeService.search(search);
        List<Joke> newJokeList = new ArrayList<>();
        if (!foundJoke.isEmpty()) {
            log.info("found joke is not empty");
            newJokeList.addAll(foundJoke);
            return ResponseEntity.status(HttpStatus.OK).body(newJokeList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Didn't find appropriate jokes");
    }

    @GetMapping(path = "/findByAdvancedSearch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findByAdvancedSearch(@RequestParam String[] category, @RequestParam String search) {
        List<Joke> foundJoke = jokeService.advancedSearch(category, search);
        List<Joke> newJokeList = new ArrayList<>();
        if (!foundJoke.isEmpty()) {
            log.info("foundJoke is not empty");
            newJokeList.addAll(foundJoke);
            return ResponseEntity.status(HttpStatus.OK).body(newJokeList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Didn't find appropriate jokes with advancedSearch");
    }

    @GetMapping(value = "/downloadExcelFile")
    public ResponseEntity download(@RequestParam(name = "category", required = false) String[] categories,
            @RequestParam(name = "key") String input) {

        List<Joke> jokes = new ArrayList<>();
        String name = "joke.xlsx";

        if (categories == null) {
            jokes = jokeService.search(input);
        } else {
            jokes = jokeService.advancedSearch(categories, input);
        }
        byte[] bytes = jokesIntoExcel.buildExcelDocument(jokes);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"");
        return new ResponseEntity<>(new ByteArrayResource(bytes), header, HttpStatus.CREATED);

    }
}
