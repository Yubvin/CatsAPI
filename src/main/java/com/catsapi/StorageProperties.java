package com.catsapi;

import org.springframework.stereotype.Component;

@Component
public class StorageProperties {

    private String location = "CatImage";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
