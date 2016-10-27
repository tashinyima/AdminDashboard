package com.netforceinfotech.admindashboard.Login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.netforceinfotech.admindashboard.MainActivity;
import com.netforceinfotech.admindashboard.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

   private Button registerButton;
   private TextView signInTextView;
    private  EditText emailEditText, passwordEditText;
   private ProgressDialog progressDialog;
   private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog =new ProgressDialog(this);

        // initialize the firebase Auth to create datas...
        firebaseAuth =FirebaseAuth.getInstance();

      registerButton = (Button) findViewById(R.id.registerButton);
      emailEditText = (EditText) findViewById(R.id.emailEditText);
      passwordEditText = (EditText) findViewById(R.id.passwordEditText);
      signInTextView = (TextView) findViewById(R.id.existingUserTextView);
        registerButton.setOnClickListener(this);
        signInTextView.setOnClickListener(this);


    }

    public void UserRegister()
    {

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {
            // No Email empty

            Toast.makeText(getApplicationContext(),"Please enter the email",Toast.LENGTH_LONG).show();

            return;  // return is to stop the fuction to excute further

        }
        if(TextUtils.isEmpty(password)){

            // No Empty password..

            Toast.makeText(getApplicationContext(),"Please Enter Password", Toast.LENGTH_LONG).show();
            return;

        }
        // if the above two if condition pass it means the user has passed the correct email and password.

        progressDialog.setMessage("Your are registering...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplication(),"Succesful",Toast.LENGTH_LONG).show();

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);

                        }else
                        {
                            Toast.makeText(getApplication(),"UnSuccesful",Toast.LENGTH_LONG).show();


                        }
                    }
                });



    }
    @Override
    public void onClick(View v) {

        if(v==registerButton){

           UserRegister();


        }

        if(v==signInTextView) {
             // redirect to loging page


            }
        }


    }

