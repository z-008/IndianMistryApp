package com.example.apple.indianmistry;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Booknow extends AppCompatActivity  implements View.OnClickListener {


    private Button buttonbooknow;
    private EditText editTextprob;
    private EditText editTextAdd;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    public String tag;
    public Date date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booknow);

        Bundle bundle = getIntent().getExtras();
        tag = bundle.getString("tag");




        buttonbooknow = (Button)findViewById(R.id.buttonbooknow);
        editTextprob = (EditText)findViewById(R.id.editTextProb);
        editTextAdd = (EditText)findViewById(R.id.editTextAdd);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        buttonbooknow.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v==buttonbooknow)
        {

            //for date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = sdf.parse(sdf.format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            //for hour minute and am-pm

            Calendar c = Calendar.getInstance();
            //System.out.println("Current time =&gt; "+c.getTime());

            SimpleDateFormat df = new SimpleDateFormat("HH");
            final String h = df.format(c.getTime());

            SimpleDateFormat mint = new SimpleDateFormat("mm");
            final String mi = mint.format(c.getTime());

            SimpleDateFormat ampm = new SimpleDateFormat("aa");
            final String apm = ampm.format(c.getTime());



        // Now formattedDate have current date/time




            final String problem= editTextprob.getText().toString();
            final String address=editTextAdd.getText().toString();
            final String dd= String.valueOf(day);
            final String mm= String.valueOf(month+1);
            final String yy= String.valueOf(year);



            servicez srvc =new servicez(tag,problem,address,dd,mm,yy,h,mi,apm);

            firebaseUser =Servicz.firebaseAuth.getCurrentUser();


            databaseReference.child(firebaseUser.getUid()).child("Booking").child(dd+mm+yy+h+mi+apm).setValue(srvc);

            Toast.makeText(this, "Service Booked Successfully", Toast.LENGTH_SHORT).show();


        }





    }





}
