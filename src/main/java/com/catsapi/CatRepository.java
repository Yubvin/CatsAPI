package com.catsapi;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by mac on 12.02.17.
 */
public interface CatRepository extends CrudRepository<Cat, Long> {
    List<Cat> findByName(String name);
}
