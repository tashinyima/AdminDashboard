package com.netforceinfotech.admindashboard.Content;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;


import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.netforceinfotech.admindashboard.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddContent extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Spinner cateSpinner,speakerSpinner;
    private EditText adddateEditText;
    private VideoView contentVideoView;
    private Button uploadVideoButton,submitContentButton;
    private String selectedPath = "";
    private static final int SELECT_VIDEO = 3;
    private Context context;
    private Firebase msgreference;
    private List<String> categorylist = new ArrayList<String>();
    private List<String> speakerlist = new ArrayList<String>();
    private List<String> speakeridlist= new ArrayList<String>();
    private ArrayAdapter<String> categoryAdapter;
    private ArrayAdapter<String> speakerAdapter;
    private ArrayAdapter<String> speakeridAdapter;
    private EditText titleEditText,locationEditText,descriptionEditText;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        adddateEditText = (EditText) findViewById(R.id.adddateEditText);
        context = this;
        adddateEditText.setOnClickListener(this);
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        locationEditText = (EditText) findViewById(R.id.locationEditText);
        speakerSpinner = (Spinner) findViewById(R.id.speakerSpinner);
        cateSpinner = (Spinner) findViewById(R.id.categorySpinner);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        contentVideoView = (VideoView) findViewById(R.id.contentVideoView);
        uploadVideoButton = (Button) findViewById(R.id.uploadVideoButton);
        submitContentButton = (Button) findViewById(R.id.submitContent);
        submitContentButton.setOnClickListener(this);

        uploadVideoButton.setOnClickListener(this);

        HttpHeader();

        CategorySpinner();
        SpeakerSpinner();


    }

    private void SpeakerSpinner() {



        speakerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, speakerlist);
        speakerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speakerSpinner.setAdapter(speakerAdapter);

    }

    private void HttpHeader() {
        // to retrieve data from Firebase....
        Firebase.setAndroidContext(this);
        msgreference = new Firebase("https://admindashboard-54f26.firebaseio.com");
    }

    private void CategorySpinner() {




        categoryAdapter = new ArrayAdapter<String>(this,

                android.R.layout.simple_spinner_item, categorylist);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cateSpinner.setAdapter(categoryAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();

        AddCategories();
        AddSpeakers();

    }

    private void AddSpeakers() {

        Firebase speakerfirebase = msgreference.child("Speakers");
        speakerfirebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                SpeakerAddData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void SpeakerAddData(DataSnapshot dataSnapshot) {

        String name = dataSnapshot.child("name").getValue(String.class);
        String key =dataSnapshot.getKey();


        Log.i("Echoo", "" + name+key);
        if (!speakerlist.contains(name)) {
            speakerlist.add(name);
            speakerAdapter.notifyDataSetChanged();
        }
//        if(!speakeridlist.contains(key)){
//            speakeridlist.add(key);
//            speakeridAdapter.notifyDataSetChanged();
//        }


    }

    private void AddCategories() {

        Firebase msgfirebase = msgreference.child("Categories");

        msgfirebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                addToList(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void addToList(DataSnapshot dataSnapshot) {


        String title = dataSnapshot.child("title").getValue(String.class);
        Log.i("Echoo", "" + title);
        if (!categorylist.contains(title)) {
            categorylist.add(title);
            categoryAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.adddateEditText) {


            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    AddContent.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.show(getFragmentManager(), "Datepickerdialog");


        } else if (v.getId() == R.id.uploadVideoButton) {

//       call the function to upload vide from android phone to server

            UploadVideo();

        } else if(v.getId()== R.id.submitContent){

            Toast.makeText(getApplicationContext(),"Hello content",Toast.LENGTH_LONG).show();


            SubmitContent();

        }


    }



    private void SubmitContent() {


        final String title =titleEditText.getText().toString().trim();
        final String location=locationEditText.getText().toString().trim();
        final String  date=adddateEditText.getText().toString().trim();
        final String category=cateSpinner.getSelectedItem().toString().trim();
        final String  speaker=speakerSpinner.getSelectedItem().toString().trim();
        String  descrip =descriptionEditText.getText().toString().trim();


        ContentInformation content= new ContentInformation(title,date,location,category,speaker,descrip);


        databaseReference.child("Content").push().setValue(content).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"Sucessful",Toast.LENGTH_LONG).show();
                // Clean fields after update...

                titleEditText.getText().clear();
                locationEditText.getText().clear();
                adddateEditText.getText().clear();
                descriptionEditText.getText().clear();

               SortByCategory sortCat= new SortByCategory(category,date,location,title,speaker);
                databaseReference.child("SortByCategory").child(category.toUpperCase()).push().setValue(sortCat).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"This is just a test",Toast.LENGTH_LONG).show();
                    }
                });

              SortBySpeaker sortSpeaker= new SortBySpeaker(speaker,title);
                databaseReference.child("Sort_By_Speaker").child(speaker).setValue(sortSpeaker).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Ok Done Speaker but Problme",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });






    }




    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        adddateEditText.setText(date);
    }


    // video upload method

    private void UploadVideo() {

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_VIDEO) {
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                System.out.println("SELECT_VIDEO Path : " + selectedPath);

                Toast.makeText(getApplicationContext(), "asdfsadfsd ", Toast.LENGTH_LONG).show();

                contentVideoView.setVideoURI(selectedImageUri);
                contentVideoView.start();
//
//             doFileUpload();


            }
        }

    }

    private String getPath(Uri uri) {

        Cursor cursor;
        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = managedQuery(uri, projection, null, null, null);   Deprecatted
        cursor = context.getContentResolver().query(uri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void doFileUpload() {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;
        String lineEnd = "rn";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        String responseFromServer = "";
        String urlString = "http://your_website.com/upload_audio_test/upload_audio.php";
        try {
            //------------------ CLIENT REQUEST
            FileInputStream fileInputStream = new FileInputStream(new File(selectedPath));
            // open a URL connection to the Servlet
            URL url = new URL(urlString);
            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            //  dos.writeBytes("Content-Disposition: form-data; name="uploadedfile";filename="" + selectedPath + """ + lineEnd);
            dos.writeBytes(lineEnd);
            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // close streams
            Log.e("Debug", "File is written");
            fileInputStream.close();
            dos.flush();
            dos.close();
        } catch (MalformedURLException ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        } catch (IOException ioe) {
            Log.e("Debug", "error: " + ioe.getMessage(), ioe);
        }
        //------------------ read the SERVER RESPONSE
        try {
            inStream = new DataInputStream(conn.getInputStream());
            String str;

            while ((str = inStream.readLine()) != null) {
                Log.e("Debug", "Server Response " + str);
            }
            inStream.close();

        } catch (IOException ioex) {
            Log.e("Debug", "error: " + ioex.getMessage(), ioex);
        }
    }
}






