package com.example.pillreminder.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pillreminder.R;

public class MedicationFormActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);


        TextView durationBelowText = findViewById(R.id.durationBelowText);

        String durationValue;
        Intent intent = getIntent();
        if(intent.hasExtra("durationNoDate")) {
            durationBelowText.setText(intent.getStringExtra("durationNoDate"));
        } else if(intent.hasExtra("durationNumPicker")) {
            durationBelowText.setText(intent.getStringExtra("durationNumPicker"));
        } else if(intent.hasExtra("durationDate")) {
            durationValue = "Until " + intent.getStringExtra("durationDate");
            durationBelowText.setText(durationValue);
        }

        TextView durationListener = (TextView) findViewById(R.id.durationID);
        durationListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicationFormActivity.this, DurationActivity.class));
            }
        });
    }
}