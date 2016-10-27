package com.netforceinfotech.admindashboard.Speaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.netforceinfotech.admindashboard.R;

public class Speaker extends AppCompatActivity implements View.OnClickListener{

    Button addSpeaker, editSpeaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);

     addSpeaker = (Button) findViewById(R.id.addSpeaker);

        editSpeaker= (Button) findViewById(R.id.editSpeaker);

        addSpeaker.setOnClickListener(this);
        editSpeaker.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.addSpeaker)
        {

            Intent i = new Intent(Speaker.this,AddSpeaker.class);
            startActivity(i);
        }else {

            Intent i = new Intent(Speaker.this,EditSpeaker.class);
            startActivity(i);
        }
    }
}
