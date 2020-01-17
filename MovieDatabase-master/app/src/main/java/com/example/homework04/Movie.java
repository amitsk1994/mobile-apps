package com.example.homework04;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Movie implements Serializable {

    private String name;
    private String description ;
    private String genre ;
    private int rating ;
    private  int year;

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", year=" + year +
                ", imDb='" + imDb + '\'' +
                '}';
    }

    private String imDb ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return getRating() == movie.getRating() &&
                getYear() == movie.getYear() &&
                getName().equals(movie.getName()) &&
                getDescription().equals(movie.getDescription()) &&
                getGenre().equals(movie.getGenre()) &&
                getImDb().equals(movie.getImDb());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getGenre(), getRating(), getYear(), getImDb());
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImDb() {
        return imDb;
    }

    public void setImDb(String imDb) {
        this.imDb = imDb;
    }

    public Movie(String name, String description, String genre, int rating, int year, String imDb) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
        this.imDb = imDb;
    }








}
class SortbyYear implements Comparator<Movie>
{

    public int compare(Movie a, Movie b)
    {
        return a.getYear() - b.getYear();
    }
}
