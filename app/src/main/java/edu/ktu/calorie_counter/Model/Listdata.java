package edu.ktu.calorie_counter.Model;

import android.widget.ListView;

import java.util.Comparator;

public class Listdata {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String id;
    public String title;
    public String desc;

    public Listdata() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Listdata(String id, String title, String desc) {
        this.id=id;
        this.title = title;
        this.desc = desc;

    }

    public static final Comparator<Listdata> By_TITLE_ASCENDING = new Comparator<Listdata>() {
        @Override
        public int compare(Listdata o1, Listdata o2) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
    };

    public static final Comparator<Listdata> By_TITLE_DESCENDING = new Comparator<Listdata>() {
        @Override
        public int compare(Listdata o1, Listdata o2) {
            return o2.getTitle().compareTo(o1.getTitle());
        }
    };

}