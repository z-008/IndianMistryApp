package com.example.apple.indianmistry;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference,rootref;
    private TextView textView11;
    private TextView textView12;
    private TextView textView13;
    private EditText editTextname;
    private EditText editTextemail;
    private Button buttonEdit;
    private Button buttonEditDone;
    public UserInfo user,user2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        textView11 = (TextView)findViewById(R.id.textView11);
        textView12 = (TextView)findViewById(R.id.textView12);
        textView13 = (TextView)findViewById(R.id.textView13);
        editTextname = (EditText)findViewById(R.id.editTextname);
        editTextemail =(EditText)findViewById(R.id.editTextemail);
        buttonEdit = (Button)findViewById(R.id.buttonEdit);
        buttonEditDone = (Button)findViewById(R.id.buttonEditDone);
        user = new UserInfo();
        user2 = new UserInfo();

        buttonEdit.setOnClickListener(this);
        buttonEditDone.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser =Servicz.firebaseAuth.getCurrentUser();
        rootref =FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid()).child("UserInfo");


        rootref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //  for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                user = snapshot.getValue(UserInfo.class);
                textView11.setText(user.getName());
                textView12.setText(user.getEmail());
                textView13.setText(user.getPhone());

            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
       /*
        * You may print the error message.
               **/
            }
        });



//
//        Log.i(user.getEmail(),"sdsd");
//        Log.i(user.getName(),"sds");
//        Log.i("Hello","121212");


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id==R.id.logout);
        {
            Servicz.firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(),Signup.class));
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if(v==buttonEdit)
        {
            textView11.setVisibility(View.INVISIBLE);
            textView12.setVisibility(View.INVISIBLE);
            textView13.setVisibility(View.INVISIBLE);
            buttonEdit.setVisibility(View.INVISIBLE);
            editTextname.setVisibility(View.VISIBLE);
            editTextemail.setVisibility(View.VISIBLE);
            buttonEditDone.setVisibility(View.VISIBLE);

        }

        if(v==buttonEditDone)
        {

            user2.setName(editTextname.getText().toString().trim());
            user2.setEmail(editTextemail.getText().toString().trim());
            user2.setPhone(user.getPhone());

            if(user2.getEmail()=="")
            {
                user2.setEmail(user.getEmail());
            }


            if(user2.getName()=="")
            {
                user2.setName(user.getName());
            }


            databaseReference.child(firebaseUser.getUid()).child("UserInfo").setValue(user2);
            Toast.makeText(this, "Edited Successfully!", Toast.LENGTH_SHORT).show();
        }

    }
}