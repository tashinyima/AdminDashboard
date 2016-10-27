package com.netforceinfotech.admindashboard.Content;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.netforceinfotech.admindashboard.R;

public class Content extends AppCompatActivity implements View.OnClickListener {

    Button addContent, editContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        addContent = (Button) findViewById(R.id.addContent);
        editContent = (Button) findViewById(R.id.editContent);
        addContent.setOnClickListener(this);
        editContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        if(v.getId()==R.id.addContent)
        {
            Intent i = new Intent(Content.this,AddContent.class);

            startActivity(i);

        }else{

            Intent i = new Intent(Content.this,EditContent.class);

            startActivity(i);
        }

    }
}
