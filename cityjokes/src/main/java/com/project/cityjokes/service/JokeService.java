/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.cityjokes.service;

import com.google.gson.Gson;
import com.project.cityjokes.model.Joke;
import com.project.cityjokes.repository.JokeRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author suzy
 */
@Service
@Slf4j
public class JokeService {

    @Autowired
    private JokeRepository jokeRepository;

    @Transactional
    public List<Joke> search(String search) {
        List<Joke> foundJoke = jokeRepository.findByIdContainingOrValueContaining(search, search);
        return foundJoke;
    }

    @Transactional
    public List<Joke> advancedSearch(String[] category, String input) {
        List<Joke> foundJoke = jokeRepository.findByCategoriesAndValueContainingOrIdContaining(category, input, input);
        return foundJoke;
    }

    @Transactional
    public Optional<Joke> save(Joke joke) {
        return Optional.ofNullable(jokeRepository.save(joke));
    }

    @Transactional
    public List<Joke> findAll() {
        return jokeRepository.findAll();
    }

    @Transactional
    @Scheduled(fixedDelay = 8 * 1000, initialDelay = 1000)
    public Joke pullJokes() throws MalformedURLException, IOException {
        log.info("pull a joke");
        URL url = new URL("https://api.chucknorris.io/jokes/random");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        Gson gson = new Gson();
        Joke joke = gson.fromJson(content.toString(), Joke.class);
        final Joke saveJoke = jokeRepository.save(joke);

        in.close();
        con.disconnect();

        return saveJoke;
    }

    // second way
//    private String jokes = "https://api.chucknorris.io/jokes/random";
//
//    private RestTemplate restTemplate = new RestTemplate();
//
//    @Async
//    @Scheduled(fixedDelay = 8 * 1000, initialDelay = 1000)
//    public Joke downloadJoke() {
//        log.info("START schedule");
//        Joke joke = new Joke();
//        try {
//
//            joke = restTemplate.getForObject(jokes, Joke.class);
//            log.info("type of joke " + joke.getClass().getSimpleName());
//            jokeRepository.save(joke);
//            return joke;
//        } catch (Exception e) {
//
//            log.warn("Scheduler must be wait 15 minutes");
//
//        }
//        log.info("END schedule");
//        return new Joke();
//    }
    //    public List<Joke> findByTypeAndKey(String type, String input) {
//        List<Joke> foundJokes = jokeRepo.findAllByTypeAndSetupContainingOrPunchlineContaining(type, input, input);
//        return foundJokes;
//    }
}
