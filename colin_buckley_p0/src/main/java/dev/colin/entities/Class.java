package dev.colin.entities;

public class Class {

    private int id;
    private String name;
    private String description;
    private int open;

    public Class(){

    }

    public Class(int id, String name, String description, int open) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.open = open;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }
}
