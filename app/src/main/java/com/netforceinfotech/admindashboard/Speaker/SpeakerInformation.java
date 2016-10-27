package com.netforceinfotech.admindashboard.Speaker;

import android.media.Image;

import java.util.Date;

/**
 * Created by xyz on 10/26/2016.
 */

public class SpeakerInformation {
    public String name;
    public String birthplace;
    public String dob;
    public String description;
//    public Image profileimage;

    public  SpeakerInformation()
    {


    }

    public SpeakerInformation(String name, String birthplace, String dob, String description) {
        this.name = name;
        this.birthplace = birthplace;
        this.dob = dob;
        this.description = description;
//        this.profileimage = profileimage;
    }

}
