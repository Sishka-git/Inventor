package com.example.inventor;

public class ItemModel {
    String name;
    String location;
    int inventNum;
    String owner;
    String description;
    int status;

    ItemModel(String name, String location, int inventNum, String owner, String description){
        this.name = name;
        this.location = location;
        this.inventNum = inventNum;
        this.owner = owner;
        this.description = description;
        this.status = 0;
    }
}
