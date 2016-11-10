package com.netforceinfotech.admindashboard.Categories;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.netforceinfotech.admindashboard.R;

import java.util.HashMap;
import java.util.Map;

public class AddCategories extends AppCompatActivity implements View.OnClickListener {
    private EditText cattitleEditText, desEditText;
    private Button catButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference _category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categories);

        cattitleEditText = (EditText) findViewById(R.id.titleEditText);
        desEditText = (EditText) findViewById(R.id.descriptionEditText);
        catButton = (Button) findViewById(R.id.catButton);
        catButton.setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.catButton) {

            sendMessage();
        }

    }

//    private void CategoryInformation() {
//
//        String title = cattitleEditText.getText().toString().trim();
//        String desc = desEditText.getText().toString().trim();
//
//        CategoryInformation cat = new CategoryInformation(title, desc);
//
//        databaseReference.child("Categories").push().setValue(cat);
//
//
//        Toast.makeText(getApplicationContext(), "Sucessful", Toast.LENGTH_LONG).show();
//
//        cattitleEditText.setText("");
//        desEditText.setText("");
//
//
//    }

    public void sendMessage() {
        DatabaseReference _root = FirebaseDatabase.getInstance().getReference();
        try {
            _category = _root.child("Categories");
        } catch (Exception ex) {
            Map<String, Object> category = new HashMap<String, Object>();
            category.put("Categories", "");
            _root.updateChildren(category);

        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(cattitleEditText.getText().toString().toLowerCase(), "");
        _category.updateChildren(map);
        _category.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference _childRef = _category.child(cattitleEditText.getText().toString().toLowerCase());
                Map<String, Object> mapChild = new HashMap<String, Object>();
                mapChild.put("title", cattitleEditText.getText().toString());
                mapChild.put("descritption", desEditText.getText().toString());
                _childRef.updateChildren(mapChild);
                _childRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Toast.makeText(getApplicationContext(), "inserted Successfully", Toast.LENGTH_SHORT).show();
                        cattitleEditText.getText().clear();
                        desEditText.getText().clear();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
