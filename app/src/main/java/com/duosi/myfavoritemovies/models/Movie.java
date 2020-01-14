package com.duosi.myfavoritemovies.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "movies", indices = @Index(value = {"imdbID"}, unique = true))
public class Movie implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int movie_id;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(@NonNull int movie_id) {
        this.movie_id = movie_id;
    }

    @NonNull
    @ColumnInfo(name = "title")
    private String Title;

    @ColumnInfo(name = "year")
    private String Year;

    @ColumnInfo(name = "imdbID")
    private String imdbID;

    @ColumnInfo(name = "type")
    private String Type;

    public Movie(String Title, String Year, String imdbID, String Type, String Poster) {
        this.Title = Title;
        this.Year = Year;
        this.imdbID = imdbID;
        this.Type = Type;
        this.Poster = Poster;
    }

    @Ignore
    public Movie() {

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    @ColumnInfo(name = "poster")
    private String Poster;

    @NonNull
    @Override
    public String toString() {
        return "title: " + Title + "/year: " + Year + "/imdbID: " + imdbID
                + "/Type: " + Type + "/Poster: " + Poster;
    }
}
