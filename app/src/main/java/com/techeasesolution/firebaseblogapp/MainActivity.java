package com.techeasesolution.firebaseblogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //init
    private EditText loginEmail , loginPassowrd ;
    Button loginBtn ;
    TextView forgotPassword , needAnAccount ;
    FirebaseAuth auth ;
    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEmail = findViewById(R.id.login_email);
        loginPassowrd = findViewById(R.id.login_passord);
        loginBtn = findViewById(R.id.login_btn);
        forgotPassword = findViewById(R.id.forgot_password);
        needAnAccount = findViewById(R.id.need_an_account);

        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();


        //click on forgot password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , ForgotPasswordActivity.class));
            }
        });

        needAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , RegisterActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = loginEmail.getText().toString().trim();
                String password = loginPassowrd.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    loginEmail.setError("Email is required");
                }else if (TextUtils.isEmpty(password)){
                    loginPassowrd.setError("Password is required");
                }else{
                    Login(email , password);
                }
            }
        });

    }

    private void Login(String email, String password) {
        progressDialog.setTitle("please wait...");
        progressDialog.show();
        auth.signInWithEmailAndPassword(email , password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this , HomeActivity.class));
                        }else {
                            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    //here we will check if the user is already login take to the home activity
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null){
            startActivity(new Intent(MainActivity.this , HomeActivity.class));
        }

    }


}
/* Forgot Password with firebase
    1. first we create new activity to asked for user email when he enter the email
    we will user sendPasswordResetEmail send the link to user to reset the email
    we will send him an link to change the password
* */