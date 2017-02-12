package com.catsapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by mac on 09.02.17.
 */
@RestController
@CrossOrigin
@RequestMapping(value="/rest/cat")
public class CatService {

    @Autowired
    private CatRepository repository;

    @RequestMapping(value="/",method = RequestMethod.GET)
    public ResponseEntity<Collection<Cat>> getAllCats(){
        return new ResponseEntity<>((Collection<Cat>) repository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ResponseEntity<Cat> getCat(@PathVariable long id){
        return new ResponseEntity<>(repository.findOne(id),HttpStatus.OK);
    }

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ResponseEntity<?> addCat(@RequestParam(value="name", defaultValue = "unknown") String name
            ,@RequestParam(value="age",defaultValue = "0") short age
            ,@RequestParam(value="breed", defaultValue = "unknown") String breed){

        Cat newCat =new Cat(name,age, breed);
        return new ResponseEntity<>(repository.save(newCat), HttpStatus.CREATED);
    }

    @RequestMapping(value="/update",method = RequestMethod.PUT)
    public ResponseEntity<?> updateCat(@RequestBody Cat cat) throws Exception {
        return new ResponseEntity<>(repository.save(cat), HttpStatus.OK);
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCat(@PathVariable long id) throws Exception {

        if (repository.exists(id)) {
            repository.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new Exception("Cat " + id + " does not exists");
        }
    }


}
