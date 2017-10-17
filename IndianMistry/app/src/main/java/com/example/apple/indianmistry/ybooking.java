package com.example.apple.indianmistry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ybooking extends AppCompatActivity {

    static ArrayList<String> booking = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    private DatabaseReference rootRef;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ybooking);



        ListView listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,booking);
        arrayAdapter.clear();
        arrayAdapter.notifyDataSetChanged();


        listView.setAdapter(arrayAdapter);

        firebaseUser =Servicz.firebaseAuth.getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid()).child("Booking");

        rootRef.addValueEventListener(new ValueEventListener() {



            @Override
            public void onDataChange(DataSnapshot snapshot) {




                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    servicez servc = postSnapshot.getValue(servicez.class);

                     booking.add(servc.getType()+" on "+ servc.getDay() +"/" + servc.getMon()+"/"+servc.getYear());



                }
                arrayAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });





    }
}
