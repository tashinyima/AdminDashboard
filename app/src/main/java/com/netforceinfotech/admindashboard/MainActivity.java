package com.netforceinfotech.admindashboard;


import com.netforceinfotech.admindashboard.Categories.Category;
import com.netforceinfotech.admindashboard.Content.Content;
import com.netforceinfotech.admindashboard.Speaker.Speaker;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;




public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button speakerButton , categoryButton,contentButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speakerButton = (Button) findViewById(R.id.speakerButton);
        categoryButton = (Button) findViewById(R.id.categoryButton);
        contentButton = (Button) findViewById(R.id.contentButton);
        speakerButton.setOnClickListener(this);
        categoryButton.setOnClickListener(this);
        contentButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.categoryButton){

            Intent i = new Intent(MainActivity.this,Category.class);

            startActivity(i);
        } else if(v.getId()==R.id.speakerButton){

            Intent i = new Intent(MainActivity.this,Speaker.class);

            startActivity(i);
        }else{


            Intent i = new Intent(MainActivity.this,Content.class);
            startActivity(i);
        }



    }
}
