package com.netforceinfotech.admindashboard.Content;

/**
 * Created by xyz on 11/10/2016.
 */

public class SortByCategory {

    String categoryname,speaker;
    Integer i;
    String date,location,title;

    public SortByCategory(String categoryname, String date, String location, String title,String speaker) {
        this.categoryname = categoryname;
        this.date = date;
        this.location = location;
        this.title = title;
        this.speaker=speaker;
    }
}
