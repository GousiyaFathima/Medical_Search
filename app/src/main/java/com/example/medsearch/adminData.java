package com.example.medsearch;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class adminData extends AppCompatActivity {

    private EditText et;
    private TextView tv;
    private ListView lv;
    private Button ad, del, log;
    private FirebaseAuth fbAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String adID;

    private ArrayList<String> al;
    private ArrayAdapter<String> adap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_data);

        fbAuth = FirebaseAuth.getInstance();
        et = findViewById(R.id.editText1);
        ad = findViewById(R.id.add);
        del = findViewById(R.id.delete);
        log = findViewById(R.id.logout1);
        tv = findViewById(R.id.textView9);
        lv = findViewById(R.id.listview1);
        lv.setVisibility(View.GONE);

        Intent it = getIntent();
        adID = it.getStringExtra("adminid");


        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pro = et.getText().toString();
                if (TextUtils.isEmpty(pro)) {
                    et.setError("Product name is required");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("medicine", pro);

                db.collection("admins").document(adID)
                        .collection("medicines").add(map)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(adminData.this, "Product added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(adminData.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dpro = et.getText().toString();
                if (TextUtils.isEmpty(dpro)) {
                    et.setError("Product name is required");
                    return;
                }
                db.collection("admins").document(adID)
                        .collection("medicines").whereEqualTo("medicine", dpro).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                WriteBatch batch = db.batch();
                                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot snapshot : snapshotList) {
                                    batch.delete(snapshot.getReference());
                                }
                                batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(adminData.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(adminData.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                al = new ArrayList<>();
                db.collection("admins").document(adID).collection("medicines").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot snapshot : snapshotList) {
                                    String med = snapshot.getString("medicine");
                                    al.add(med);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(adminData.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                adap = new ArrayAdapter<String>(adminData.this, android.R.layout.simple_list_item_1, al);
                lv.setAdapter(adap);
                lv.setVisibility(View.VISIBLE);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbAuth.signOut();
                startActivity(new Intent(getApplicationContext(), adminLog.class));
                finish();
            }
        });


    }
}