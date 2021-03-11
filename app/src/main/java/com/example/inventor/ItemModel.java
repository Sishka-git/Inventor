package com.example.inventor;

public class ItemModel {
    int id;
    String name;
    String location;
    int inventNum;
    String owner;
    String description;
    int status;

    ItemModel(int id,String name, String location, int inventNum, String owner, String description){
        this.id = id;
        this.name = name;
        this.location = location;
        this.inventNum = inventNum;
        this.owner = owner;
        this.description = description;
        this.status = 0;
    }
}
