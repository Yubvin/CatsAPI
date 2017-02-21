package com.catsapi;

import javax.persistence.*;

/**
 * Created by mac on 09.02.17.
 */
@Entity
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private short age;

    @Column(nullable = false)
    private String breed;

    @Column(nullable = false)
    private String imgName;

    public Cat() {
    }

    public Cat(String name, short age, String breed) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.imgName = "default-cat.jpg";
    }

    public Cat(String name, short age, String breed, String imgName) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.imgName = imgName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", breed='" + breed + '\'' +
                ", imgName='" + imgName + '\'' +
                '}';
    }
}