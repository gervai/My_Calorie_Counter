package edu.ktu.calorie_counter.Model;

import android.widget.ListView;

import java.util.Comparator;

public class Listdata {

    public String id;
    public String title;
    public String calorie;

    public Listdata() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Listdata(String id, String title, String calorie) {
        this.id = id;
        this.title = title;
        this.calorie = calorie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }
}