package com.example.apple.indianmistry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ServiceSelected extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView4;
    private TextView textViewService;
    private Button buttonBnow;
    private Button buttonSched;
    public String tag;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_selected);

        Bundle bundle = getIntent().getExtras();
        tag = bundle.getString("tag");
        imageView4 = (ImageView)findViewById(R.id.imageView4);
        textViewService = (TextView)findViewById(R.id.textView15);
        buttonBnow = (Button)findViewById(R.id.buttonBnow);
        buttonSched = (Button)findViewById(R.id.buttonSched);
        buttonBnow.setOnClickListener(this);
        buttonSched.setOnClickListener(this);

        if(tag.equals("plumber"))
        {
            Log.i("sdasd","hello");
            imageView4.setImageResource(R.drawable.plumber);
            textViewService.setText("Plumber");
        }

        else if(tag.equals("electrician"))
        {
            imageView4.setImageResource(R.drawable.electric);
            textViewService.setText("Electrician");
        }

        else if(tag.equals("carservice"))
        {
            imageView4.setImageResource(R.drawable.car);
            textViewService.setText("Car/Bike Service");
        }

        else if(tag.equals("cleaning"))
        {
            imageView4.setImageResource(R.drawable.broom);
            textViewService.setText("Cleaning");
        }

        else if(tag.equals("photographer"))
        {
            imageView4.setImageResource(R.drawable.camera);
            textViewService.setText("Photography");
        }

        else if(tag.equals("Dj"))
        {
            imageView4.setImageResource(R.drawable.dj);
            textViewService.setText("Sound System/Dj");
        }




    }

    @Override
    public void onClick(View v) {
        if(v==buttonSched)
        {
            Intent intent = new Intent(ServiceSelected.this,SchService.class);
            intent.putExtra("tag",tag);
            startActivity(intent);
        }

        if(v==buttonBnow)
        {
            Intent intent = new Intent(ServiceSelected.this,Booknow.class);
            intent.putExtra("tag",tag);
            startActivity(intent);
        }

    }
}
