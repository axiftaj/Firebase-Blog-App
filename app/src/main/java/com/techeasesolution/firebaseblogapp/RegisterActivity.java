package com.techeasesolution.firebaseblogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText registerEmail , regiserPassword ;
    Button registerBtn ;
    TextView alreadyhaveAnAccount ;

    FirebaseAuth mAuth ;
    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmail = findViewById(R.id.register_email);
        regiserPassword = findViewById(R.id.register_passord);
        registerBtn = findViewById(R.id.register_btn);
        alreadyhaveAnAccount = findViewById(R.id.already_have_an_account);

        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        progressDialog = new ProgressDialog(this);
        

        alreadyhaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this , MainActivity.class));
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registerEmail.getText().toString().trim();
                String password = regiserPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    registerEmail.setError("Email is required");
                }else if (TextUtils.isEmpty(password)){
                    regiserPassword.setError("Password is required");
                }else if (password.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password leng must be greater than 6", Toast.LENGTH_SHORT).show();
                }else {
                    registerUser(email , password);
                }
            }
        });


    }

    private void registerUser(String email, String password) {
        progressDialog.setTitle("Please wait..");
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email , password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(RegisterActivity.this , HomeActivity.class));
                            Toast.makeText(RegisterActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
