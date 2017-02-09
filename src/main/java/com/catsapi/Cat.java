package com.catsapi;

/**
 * Created by mac on 09.02.17.
 */
public class Cat {

    private static long nextId = 1;
    private long id;
    private String name;
    private short age;
    private String breed;


    public Cat() {
    }

    public Cat(String name, short age, String breed) {
        this.id = nextId;
        nextId++;
        this.name = name;
        this.age = age;
        this.breed = breed;
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

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", breed='" + breed + '\'' +
                '}';
    }
}