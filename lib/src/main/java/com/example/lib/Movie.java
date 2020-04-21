package com.example.lib;

import java.util.ArrayList;

public class Movie {
    protected String name;
    protected String description;
    protected String writer;
    ArrayList<String> cast;
    ArrayList<String> prize;

    public Movie(String name, String description, String writer) {
        this.name=name;
        this.description=description;
        this.writer=writer;
        cast=new ArrayList<String>();
        prize=new ArrayList<String>();
    }

    public void addCast(String a) {cast.add(a);}
    public void addPrize(String a) {prize.add(a);}
    public int sizeCast() {return cast.size();}
    public int sizePrize() {return prize.size();}

    public String getName() {return name;}
    public String getDescription() {return description;}
    public String getWriter() {return writer;}
    public ArrayList<String> getCast() {return cast;}
    public ArrayList<String> getPrize() {return prize;}
}
