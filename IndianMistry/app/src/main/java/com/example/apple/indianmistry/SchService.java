package com.example.apple.indianmistry;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SchService extends AppCompatActivity  implements View.OnClickListener {

    private Spinner hour;
    private Spinner min;
    private Spinner ap;
    private TextView textViewDate;
    private Button buttonbook;
    private EditText editTextprob;
    private EditText editTextAdd;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    public String tag;
    static final int DATE_DIALOG_ID = 999;

    public int day=0,month=0,year=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sch_service);

        Bundle bundle = getIntent().getExtras();
        tag = bundle.getString("tag");

        hour= (Spinner)findViewById(R.id.spinnerHh);
        ArrayAdapter<String> adapter1= new ArrayAdapter<String>(SchService.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Hour));

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hour.setAdapter(adapter1);


        min= (Spinner)findViewById(R.id.spinnermm);
        ArrayAdapter<String>adapter2= new ArrayAdapter<String>(SchService.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Minute));

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        min.setAdapter(adapter2);


        ap= (Spinner)findViewById(R.id.spinnerap);
        ArrayAdapter<String>adapter3= new ArrayAdapter<String>(SchService.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.ap));

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ap.setAdapter(adapter3);


        textViewDate =(TextView)findViewById(R.id.textViewDate);
        buttonbook = (Button)findViewById(R.id.buttonbook);
        editTextprob = (EditText)findViewById(R.id.editTextProb);
        editTextAdd = (EditText)findViewById(R.id.editTextAdd);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        textViewDate.setOnClickListener(this);
        buttonbook.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v==textViewDate) {
            showDialog(DATE_DIALOG_ID);
        }

        if(v==buttonbook)
        {

                final String problem= editTextprob.getText().toString();
                final String address=editTextAdd.getText().toString();
                final String dd= String.valueOf(day);
                final String mm= String.valueOf(month);
                final String yy= String.valueOf(year);
                final String hh= hour.getSelectedItem().toString();
                final String ms= min.getSelectedItem().toString();
                final String aps= ap.getSelectedItem().toString();

            servicez srvc =new servicez(tag,problem,address,dd,mm,yy,hh,ms,aps);


            firebaseUser =Servicz.firebaseAuth.getCurrentUser();

            databaseReference.child(firebaseUser.getUid()).child("Booking").child(dd+mm+yy+hh+ms+aps).setValue(srvc);

            Toast.makeText(this, "Service Booked Successfully", Toast.LENGTH_SHORT).show();


            }





    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date

                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview

                textViewDate.setText(new StringBuilder().append(day)
                        .append("-").append(month+1).append("-").append(year)
                        .append(" "));





        }
    };




}
