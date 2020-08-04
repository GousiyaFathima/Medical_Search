package com.example.medsearch;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class adminRegis extends AppCompatActivity {

    private static final String TAG = "adminRegis";
    private FirebaseAuth fAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText PharName, street, place, no, mail, pwd;
    private Button register;
    private TextView rlog;
    private String adminID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_regis);

        PharName = findViewById(R.id.editText1);
        place = findViewById(R.id.editText3);
        street = findViewById(R.id.editText2);
        no = findViewById(R.id.editText4);
        mail = findViewById(R.id.editText5);
        pwd = findViewById(R.id.editText6);
        register = findViewById(R.id.button3);
        rlog = findViewById(R.id.textView5);
        fAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String m = mail.getText().toString();
                String p = pwd.getText().toString();
                final String sn = PharName.getText().toString();
                final String ad = street.getText().toString();
                final String pl = place.getText().toString();
                final String pn = no.getText().toString();
                if (TextUtils.isEmpty(m)) {
                    mail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(p) || p.length() < 6) {
                    pwd.setError("Invalid password");
                    return;
                }
                if (TextUtils.isEmpty(sn)) {
                    PharName.setError("Pharmacy Name is required");
                    return;
                }
                if (TextUtils.isEmpty(ad)) {
                    street.setError("Street is required");
                    return;
                }
                if (TextUtils.isEmpty(pl)) {
                    place.setError("Place is required");
                    return;
                }
                if (TextUtils.isEmpty(pn)) {
                    no.setError("Phone Number is required");
                }
                fAuth.createUserWithEmailAndPassword(m, p)
                        .addOnCompleteListener(adminRegis.this, new OnCompleteListener<AuthResult>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(adminRegis.this, "registered successfully.Login to continue", Toast.LENGTH_SHORT).show();
                                    adminID = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                    DocumentReference dr = db.collection("admins").document(adminID);
                                    Map<String, Object> admin = new HashMap<>();
                                    admin.put("PharmacyName", sn);
                                    admin.put("email", m);
                                    admin.put("Street", ad);
                                    admin.put("place", pl);
                                    admin.put("PhoneNumber", pn);
                                    dr.set(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                        }
                                    });
                                    Intent it = new Intent(adminRegis.this, adminLog.class);
                                    startActivity(it);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(adminRegis.this, "registration failed", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "onComplete: Failed=" + Objects.requireNonNull(task.getException()).getMessage());

                                }
                            }
                        });
            }
        });

        rlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(adminRegis.this, adminLog.class);
                startActivity(it);
            }
        });
    }
}