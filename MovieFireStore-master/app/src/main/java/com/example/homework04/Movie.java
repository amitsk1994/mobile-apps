package com.example.homework04;

import android.os.Parcel;
import android.os.Parcelable;


import java.util.Comparator;
import java.util.Objects;

public class Movie implements Parcelable {

    private String name;
    private String description ;
    private String genre ;
    private int rating ;
    private  int year;

    public Movie(){
        //needed for firebase firestore
    }

    public Movie(String name, String description, String genre, int rating, int year, String imDb) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
        this.imDb = imDb;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    public Movie(Parcel parcel) {

        this.name = parcel.readString();
        this.description = parcel.readString();;
        this.genre = parcel.readString();;
        this.rating = parcel.readInt();;
        this.year = parcel.readInt();;
        this.imDb = parcel.readString();;
    }

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




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.description);
        parcel.writeString(this.genre);
        parcel.writeInt(this.rating);
        parcel.writeInt(this.year);
        parcel.writeString(this.imDb);
    }
}

