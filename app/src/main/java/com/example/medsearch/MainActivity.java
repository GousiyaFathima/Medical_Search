package com.example.medsearch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText prod, loc;
    private Button log, sch;
    private ListView lv;
    private FirebaseAuth fbAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ProductListAdapter adap;
    private List<Product> prol;
    private int i = 1;
    //ArrayList<String []> al=new ArrayList<String []>();
    //ArrayAdapter<String []> ad;
    //HashMap<String, String> data=new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbAuth = FirebaseAuth.getInstance();
        prod = findViewById(R.id.editText1);
        loc = findViewById(R.id.editText2);
        log = findViewById(R.id.button);
        sch = findViewById(R.id.button1);
        lv = findViewById(R.id.listview);
        lv.setVisibility(View.GONE);


        sch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String p = prod.getText().toString();
                String l = loc.getText().toString();

                db.collection("admins").whereEqualTo("place", l).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                                prol = new ArrayList<>();

                                for (DocumentSnapshot snapshot : snapshotList) {
                                    final String pn = snapshot.getString("PharmacyName");
                                    final String st = snapshot.getString("Street");
                                    final String pl = snapshot.getString("place");
                                    final String pno = snapshot.getString("PhoneNumber");

                                    db.collection("admins").document(snapshot.getId())
                                            .collection("medicines").whereEqualTo("medicine", p).get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots1) {
                                                    List<DocumentSnapshot> snapshotList1 = queryDocumentSnapshots1.getDocuments();
                                                    if (!snapshotList1.isEmpty()) {
                                                        prol.add(new Product(i, pn, st, pl, pno));
                                                        i++;
                                                        //Toast.makeText(MainActivity.this,""+pn,Toast.LENGTH_LONG).show();
                                                    }
                                                    //else{
                                                    //  Toast.makeText(MainActivity.this,"else",Toast.LENGTH_SHORT).show();
                                                    //}

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(MainActivity.this, "Not Available", Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                }

                                adap = new ProductListAdapter(getApplicationContext(), prol);
                                lv.setAdapter(adap);
                                lv.setVisibility(View.VISIBLE);

                                /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    }
                                });*/
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Nothing found", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbAuth.signOut();
                startActivity(new Intent(getApplicationContext(), userLog.class));
                finish();
            }
        });

    }


}