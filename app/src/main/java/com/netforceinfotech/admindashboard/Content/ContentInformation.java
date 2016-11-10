package com.netforceinfotech.admindashboard.Content;

/**
 * Created by xyz on 11/10/2016.
 */

public class ContentInformation {

    String title,date,location,category,speaker;
    String description;

    public ContentInformation(String title, String date, String location, String category, String speaker, String description) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.category = category;
        this.speaker = speaker;
        this.description = description;
    }
}
