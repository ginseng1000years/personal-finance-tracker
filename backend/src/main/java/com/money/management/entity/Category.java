package com.money.management.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
public class Category extends BaseEntity {
    
    private String name;
    
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}