/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.cityjokes.repository;

import com.project.cityjokes.model.Joke;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author suzy
 */

@Repository
public interface JokeRepository extends MongoRepository<Joke, String> {

    List<Joke> findByIdContainingOrValueContaining(String id, String value);

    List<Joke> findByCategoriesAndValueContainingOrIdContaining(String[] category, String id, String value);

}
