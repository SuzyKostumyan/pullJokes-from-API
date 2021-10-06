/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.cityjokes.controller;

import com.project.cityjokes.model.Joke;
//import com.project.cityjokes.schedule.JokesSchedule;
import com.project.cityjokes.service.JokeService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

}
