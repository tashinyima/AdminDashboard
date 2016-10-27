package com.netforceinfotech.admindashboard.Categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.netforceinfotech.admindashboard.R;

public class Category extends AppCompatActivity implements View.OnClickListener{

    Button addCategoryButton, editCategoryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);



        addCategoryButton = (Button) findViewById(R.id.addCategory);
        editCategoryButton =(Button) findViewById(R.id.editCategory);

        addCategoryButton.setOnClickListener(this);
        editCategoryButton.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.addCategory)
        {

            Intent i = new Intent(Category.this,AddCategories.class);
            startActivity(i);
        }else {

            Intent i = new Intent(Category.this,EditCategories.class);
            startActivity(i);
        }
    }
}
