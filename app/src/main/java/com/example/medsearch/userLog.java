package com.example.medsearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class userLog extends AppCompatActivity {

    private EditText et1, et2;
    private Button login, register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log);
        et1 = findViewById(R.id.editText1);
        et2 = findViewById(R.id.editText2);
        login = findViewById(R.id.button1);
        register = findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();

       /* if(mAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }*/
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = et1.getText().toString();
                String pwd = et1.getText().toString();
                if (pwd.length() < 6) {
                    et1.setError("Password must be atleast 6 characters");
                    return;
                }
                mAuth.createUserWithEmailAndPassword(mail, pwd).addOnCompleteListener(userLog.this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Registered successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et1.getText().toString();
                String pass = et1.getText().toString();
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(userLog.this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent it = new Intent(userLog.this, MainActivity.class);
                            startActivity(it);
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}