package com.example.chatpesa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    public String username, ename, password;
    public EditText usernameEd, enameEd, passwordEd;
    public FirebaseAuth firebaseAuth;
    public FirebaseUser firebaseUser;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public TextView submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameEd = findViewById(R.id.regUsername);
        enameEd = findViewById(R.id.regEname);
        passwordEd = findViewById(R.id.regpassword);
        //submit = findViewById(R.id.submit);
        submit = findViewById(R.id.submit);
        firebaseAuth=FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(),"at least  8 characters",Toast.LENGTH_LONG)
                validateCredentilas();
            }
        });
    }

    public void validateCredentilas() {
        username = usernameEd.getText().toString();
        ename = enameEd.getText().toString();
        password = passwordEd.getText().toString();
        // Toast.makeText(getApplicationContext(),"at least  8 characters",Toast.LENGTH_LONG).show();
        // validate the credentials before submitting to database


        if (username.isEmpty() || ename.isEmpty() || password.isEmpty()
        ) {
            Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
        } else if (password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "at least  8 characters", Toast.LENGTH_LONG).show();


        } else {
            // submit details to firebase


            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Submitting...");
            progressDialog.setMessage("Please wait till i finish registration");
            progressDialog.show();


            firebaseAuth.createUserWithEmailAndPassword(ename, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        try {
                            Toast.makeText(RegisterActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    else {
                        usernameEd.setText(username+ename);
                        Toast.makeText(RegisterActivity.this,"Failled",Toast.LENGTH_LONG).show();}
                    progressDialog.dismiss();


                }


            });

        }
    }
}