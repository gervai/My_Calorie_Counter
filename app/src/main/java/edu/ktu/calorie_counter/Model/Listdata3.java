package edu.ktu.calorie_counter.Model;

public class Listdata3 {

    public String calorie;
    public String count;
    public String date;
    public String id;
    public String title;

    public Listdata3() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Listdata3(String calorie, String count, String date, String id, String title) {
        this.calorie = calorie;
        this.count = count;
        this.date = date;
        this.id = id;
        this.title = title;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}