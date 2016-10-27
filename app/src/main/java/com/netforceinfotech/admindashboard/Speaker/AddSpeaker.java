package com.netforceinfotech.admindashboard.Speaker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.netforceinfotech.admindashboard.MainActivity;
import com.netforceinfotech.admindashboard.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddSpeaker extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final int TAKE_PHOTO_CODE = 100;
    private static final int PICK_IMAGE = 101;
    private static final String IMAGE_DIRECTORY_NAME = "choephel";
    private Context context;
    private EditText birthdayEditText;
    private Button profileButton;
//    private Button getProfileButtonTake;
    private String filePath;
    private Uri fileUri;
    private ImageView imageViewDp;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button submitButton;
    private EditText nameEditText;
    private EditText description;
    private EditText birthplace;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_speaker);
        context = this;
        //FireBase Database Instance
        firebaseDatabase = FirebaseDatabase.getInstance();
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        description = (EditText) findViewById(R.id.descriptionEditText);
        birthplace = (EditText) findViewById(R.id.birthplaceEditText);

        //Wait here
        databaseReference = firebaseDatabase.getReference();
        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        imageViewDp = (ImageView) findViewById(R.id.imageViewDp);
        birthdayEditText = (EditText) findViewById(R.id.dobEditText);
        birthdayEditText.setOnClickListener(this);
        profileButton = (Button) findViewById(R.id.profileButton);
        profileButton.setOnClickListener(this);
//        getProfileButtonTake = (Button) findViewById(R.id.profileButtonTake);
//        getProfileButtonTake.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.dobEditText) {

            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    AddSpeaker.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.show(getFragmentManager(), "Datepickerdialog");

            Log.i("DAte", "sdfasfasd");
        } else if (v.getId() == R.id.profileButton) {
            pickPictureIntent();

        }else if(v.getId()==R.id.submitButton){

             SpeakerInformation();


        }

    }

    private void SpeakerInformation() {




        String name = nameEditText.getText().toString().trim();
        String des = description.getText().toString().trim();
        String  dob = birthdayEditText.getText().toString().trim();
        String birth = birthplace.getText().toString().trim();
//        ImageView img = imageViewDp.getDrawable();

        // get Image URL ...

        SpeakerInformation speaker = new SpeakerInformation(name,birth,des,dob);

//        databaseReference.setValue(speaker);

        // Push function is for saving and storing an array of data...


        databaseReference.child("Speaker").push().setValue(speaker);



        Toast.makeText(getApplicationContext(),"Sucessful",Toast.LENGTH_LONG).show();
// this is the reset or empty the field so that we can add data...
        nameEditText.setText("");
        description.setText("");
        birthdayEditText.setText("");
        birthplace.setText("");

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        birthdayEditText.setText(date);
    }

    private void takePictureIntent() {
        Intent cameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri();
        filePath = fileUri.getPath();

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    private void pickPictureIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private void getPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            String[] permission = {
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            ActivityCompat.requestPermissions(this,
                    permission, 1);


        }
    }

    private static File getOutputMediaFile() {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            } else {
                Log.d(IMAGE_DIRECTORY_NAME, mediaStorageDir.getAbsolutePath());
            }

        } else {
            Log.d(IMAGE_DIRECTORY_NAME, mediaStorageDir.getAbsolutePath());
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile = null;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
        Log.i("result imagepath", mediaFile.getAbsolutePath());


        return mediaFile;
    }

    public Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    Log.i("result picture", "clicked");
                    filePath = fileUri.getPath();
                    Glide.with(context).load(fileUri).into(imageViewDp);
                }
                break;
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {

                    Uri uri = data.getData();
                    filePath = getPath(uri);

                    Log.i("kresult", filePath);

                    Glide.with(context).load(filePath).into(imageViewDp);

                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @SuppressLint("NewApi")
    private String getPath(Uri uri) {
        if (uri == null) {
            return null;
        }

        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor;
        if (Build.VERSION.SDK_INT > 19) {
            // Will return "image:x*"
            String wholeID = DocumentsContract.getDocumentId(uri);
            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];
            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, sel, new String[]{id}, null);
        } else {
            cursor = context.getContentResolver().query(uri, projection, null, null, null);
        }
        String path = null;
        try {
            int column_index = cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index).toString();
            cursor.close();
        } catch (NullPointerException e) {

        }
        return path;
    }

    private File savebitmap(String filePath) {
        File file = new File(filePath);
        String extension = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmOptions);
        OutputStream outStream = null;
        try {
            // make a new bitmap from your file
            outStream = new FileOutputStream(file);
            if (extension.equalsIgnoreCase("png")) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, outStream);
            } else if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, outStream);
            } else {
                return null;
            }
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;


    }




}
