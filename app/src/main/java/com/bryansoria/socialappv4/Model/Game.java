package com.bryansoria.socialappv4.Model;

import java.io.Serializable;


public class Game implements Serializable
{

    private String name;
    private String developer;
    private String description;
    private String gender;
    private int img;
    private int id;



    public Game(){}
    public Game(String nombre, String creador, String descripcion, int imagen, String genero, int id) {
        this.name = nombre;
        this.developer = creador;
        this.description = descripcion;
        this.img = imagen;
        this.gender = genero;
        this.id=id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
