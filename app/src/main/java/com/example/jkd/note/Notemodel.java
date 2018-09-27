package com.example.jkd.note;

public class Notemodel {

    public String title;
    public String description;

    public Notemodel(){

    }

    public Notemodel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
