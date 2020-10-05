package com.example.pillreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView durationListener = (TextView) findViewById(R.id.durationID);
        durationListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFragment();
            }
        });
    }

    public void displayFragment() {
        DurationFragment fragment = DurationFragment.newInstance("t","t");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}