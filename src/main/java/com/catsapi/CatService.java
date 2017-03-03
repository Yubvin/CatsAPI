package com.catsapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by mac on 09.02.17.
 */
@RestController
@CrossOrigin
@RequestMapping(value="/rest/cat")
public class CatService {

    @Autowired
    private CatRepository repository;
    private final StorageService storageService;

    @Autowired
    public CatService(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value="/",method = RequestMethod.GET)
    public ResponseEntity<Collection<Cat>> getAllCats(){
        return new ResponseEntity<>((Collection<Cat>) repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ResponseEntity<Cat> getCat(@PathVariable long id){
        return new ResponseEntity<>(repository.findOne(id),HttpStatus.OK);
    }

    @RequestMapping(value="name/{name}",method = RequestMethod.GET)
    public ResponseEntity<Collection<Cat>> getCatsByName(@PathVariable String name){
        return new ResponseEntity<>((Collection<Cat>) repository.findByName(name), HttpStatus.OK);
    }
    @RequestMapping(value="age/{age}",method = RequestMethod.GET)
    public ResponseEntity<Collection<Cat>> getCatsByAge(@PathVariable short age){
        return new ResponseEntity<>((Collection<Cat>) repository.findByAge(age), HttpStatus.OK);
    }

    @RequestMapping(value="breed/{breed}",method = RequestMethod.GET)
    public ResponseEntity<Collection<Cat>> getCatsByBreed(@PathVariable String breed){
        return new ResponseEntity<>((Collection<Cat>) repository.findByBreed(breed), HttpStatus.OK);
    }

    private static String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    @RequestMapping(value="/add",method = RequestMethod.POST)
    public ResponseEntity<?> addCat(@RequestParam(value="name", defaultValue = "unknown") String name
            ,@RequestParam(value="age",defaultValue = "0") short age
            ,@RequestParam(value="breed", defaultValue = "unknown") String breed
            ,@RequestParam(value="image", required=false) MultipartFile file){

        Cat newCat = null;

        if (file!=null && !file.isEmpty()) {
            try {
                String ext = getFileExtension(file);
                UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
                String nameImg = uid.randomUUID().toString().replaceAll("-", "")+"."+ext;
                storageService.store(file, nameImg);
                newCat = new Cat(name,age, breed, nameImg);
            } catch (Exception e) {
            }
        } else {
            newCat = new Cat(name,age, breed);
        }

        return new ResponseEntity<>(repository.save(newCat), HttpStatus.CREATED);
    }

    @RequestMapping(value="/imgUpdate",method = RequestMethod.POST)
    public ResponseEntity<?> updateCatImage(@RequestParam(value="id", required = true) long id
                                            ,@RequestParam(value="image", required=false) MultipartFile file) throws Exception {

        if (file!=null && !file.isEmpty()) {
            try {
                String ext = getFileExtension(file);
                UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
                String nameImg = uid.randomUUID().toString().replaceAll("-", "")+"."+ext;
                storageService.store(file, nameImg);
                repository.findOne(id).setImgName(nameImg);
                repository.save(repository.findOne(id));
            } catch (Exception e) {
            }
        } else {

        }

        return new ResponseEntity<>(repository.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(value="/update",method = RequestMethod.PUT)
    public ResponseEntity<?> updateCat(@RequestBody Cat cat) throws Exception {

        if (repository.exists(cat.getId())) {
            return new ResponseEntity<>(repository.save(cat), HttpStatus.OK);
        } else {
            throw new Exception("Cat " + cat.getId() + " does not exists");
        }

    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCat(@PathVariable long id) throws Exception {

        if (repository.exists(id)) {
            Cat cat = new Cat(repository.findOne(id));
            repository.delete(id);
            storageService.delete(cat.getImgName());
            return new ResponseEntity<>(cat, HttpStatus.OK);
        } else {
            throw new Exception("Cat " + id + " does not exists");
        }
    }


}
