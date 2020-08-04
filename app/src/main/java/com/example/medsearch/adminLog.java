package com.example.medsearch;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class adminLog extends AppCompatActivity {

    private EditText et1, et2;
    private Button login;
    private TextView reg;
    private FirebaseAuth fbAuth;
    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_log);
        et1 = findViewById(R.id.editText1);
        et2 = findViewById(R.id.editText2);
        login = findViewById(R.id.button1);
        reg = findViewById(R.id.textView6);
        fbAuth = FirebaseAuth.getInstance();

        /*if(fbAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),adminData.class));
            finish();
        }*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et1.getText().toString();
                String pwd = et2.getText().toString();
                fbAuth.signInWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(adminLog.this, new OnCompleteListener<AuthResult>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    ID = Objects.requireNonNull(fbAuth.getCurrentUser()).getUid();

                                    Intent it = new Intent(adminLog.this, adminData.class);
                                    it.putExtra("adminid", ID);
                                    startActivity(it);

                                } else {
                                    Toast.makeText(adminLog.this, "login failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(adminLog.this, adminRegis.class);
                startActivity(it);
            }
        });
    }
}