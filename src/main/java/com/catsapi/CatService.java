package com.catsapi;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by mac on 09.02.17.
 */
@RestController
@RequestMapping(value="/rest/cat")
public class CatService {

    @RequestMapping(value="/",method = RequestMethod.GET)
    public HashMap<Long,Cat> getAllStudents(){
        return CatsApiApplication.hmCat;
    }

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public Cat addCat(@RequestParam(value="name") String name
            ,@RequestParam(value="age",defaultValue = "unknown") short age
            ,@RequestParam(value="breed", defaultValue = "unknown") String breed){

        Cat newCat =new Cat(name,age, breed);
        CatsApiApplication.hmCat.put(new Long(newCat.getId()),newCat);
        return newCat;
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Cat getStudent(@PathVariable long id){
        return CatsApiApplication.hmCat.get(new Long(id));
    }

    @RequestMapping(value="/update",method = RequestMethod.PUT)
    public Cat updateCat(@RequestBody Cat cat) throws Exception {

        if(CatsApiApplication.hmCat.containsKey(new Long(cat.getId()))){
            CatsApiApplication.hmCat.put(new Long(cat.getId()),cat);
        }else{
            throw new Exception("Cat "+cat.getId()+" does not exists");
        }

        return cat;
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.DELETE)
    public Cat deleteCat(@PathVariable long id) throws Exception {

        Cat cat;

        if (CatsApiApplication.hmCat.containsKey(new Long(id))) {
            cat = CatsApiApplication.hmCat.get(new Long(id));
            CatsApiApplication.hmCat.remove(new Long(id));
        } else {
            throw new Exception("Cat " + id + " does not exists");
        }

        return cat;
    }


}
