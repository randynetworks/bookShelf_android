package com.rr.bookshelf;
import android.text.Editable;

import com.google.firebase.database.IgnoreExtraProperties;
import java.io.Serializable;
/**
 * Created by Herdi_WORK on 18.06.17.
 */

@IgnoreExtraProperties
public class Book {
    private String title;
    private String author;
    private String publisher;
    private String year;
    private Integer isComplete;

    public Book() {

    }

    public void setComplete(Integer complete) {
        isComplete = complete;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getComplete() {
        return isComplete;
    }

    public String getYear() {
        return year;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
