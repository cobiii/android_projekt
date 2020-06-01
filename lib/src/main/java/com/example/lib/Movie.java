package com.example.lib;

public class Movie {
    protected String title;
    protected String synopsis;
    protected String genre;
    protected String director;
    protected String writer;
    protected String runningTimeStr;
    protected String studio;
    protected String poster;
    protected String boxoffice;
    protected Double rating;
    protected Integer ratingCount;
    protected Integer year;


    public Movie(String title, String synopsis, String genre,String director,String writer,String runningTimeStr,String studio,String poster, String boxoffice, Double rating, Integer ratingCount, Integer year) {
        this.title=title;
        this.synopsis=synopsis;
        this.genre=genre;
        this.director=director;
        this.writer=writer;
        this.runningTimeStr=runningTimeStr;
        this.studio=studio;
        this.poster=poster;
        this.boxoffice=boxoffice;
        this.rating=rating;
        this.ratingCount=ratingCount;
        this.year=year;
    }

    public String getTitle() {return title;}
    public String getSynopsis() {return synopsis;}
    public String getGenre() {return genre;}
    public String getDirector() { return director; }
    public String getWriter() {return writer;}
    public String getRunningTimeStr() { return runningTimeStr; }
    public String getPoster() { return poster; }
    public String getStudio() { return studio; }
    public String getBoxoffice() { return boxoffice; }
    public Double getRating() { return rating; }
    public Integer getRatingCount() { return ratingCount; }
    public Integer getYear() { return year; }
}
