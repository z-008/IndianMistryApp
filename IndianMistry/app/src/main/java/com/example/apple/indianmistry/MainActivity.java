package com.example.apple.indianmistry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

  private Button getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getStarted =(Button)findViewById(R.id.button);
        getStarted.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, Signup.class));

    }



}
