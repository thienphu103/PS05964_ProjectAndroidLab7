package com.example.a.lab7;

/**
 * Created by A on 8/12/2017.
 */

public class ClassBook  {
    public int id;
    public String tilte;
    public String author;
    public String price;

    public ClassBook(int id, String tilte, String author, String price) {
        this.id = id;
        this.tilte = tilte;
        this.author = author;
        this.price = price;
    }

    public ClassBook() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return  id +" - "+ tilte +" -- "+ author +" - "+ price ;
    }

}

