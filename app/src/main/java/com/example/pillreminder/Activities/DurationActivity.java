package com.example.pillreminder.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pillreminder.Fragments.DatePickerFragment;
import com.example.pillreminder.Fragments.DurationNumDaysFragment;
import com.example.pillreminder.Fragments.DurationUntilDateFragment;
import com.example.pillreminder.R;

import java.util.Calendar;

public class DurationActivity extends AppCompatActivity implements DurationUntilDateFragment.returnDurationDatePickerValue, DurationNumDaysFragment.returnDurationNumPickerValue {
    String durationDatePicker;
    String durationNumPicker;
    private DurationUntilDateFragment fragmentA;
    private DurationNumDaysFragment fragmentB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duration);

        if(savedInstanceState == null) {
            fragmentA = DurationUntilDateFragment.newInstance();
            fragmentB = DurationNumDaysFragment.newInstance();
        }

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        final RadioButton noDate = findViewById(R.id.noDate);
        noDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragments();
            }
        });

        final RadioButton xRadioButton = findViewById(R.id.xDaysRadioButton);
        xRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumDaysFragment();
            }
        });

        final RadioButton untilRadioButton = findViewById(R.id.untilRadioButton);
        untilRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayUntilDateFragment();
            }
        });

        Button durationIntentButton = (Button) findViewById(R.id.durationButton);
        durationIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DurationActivity.this, MedicationFormActivity.class);
                if(noDate.isChecked()) {
                    myIntent.putExtra("durationNoDate", "No end date");
                    startActivity(myIntent);
                } else if(xRadioButton.isChecked() && durationNumPicker != null) {
                    myIntent.putExtra("durationNumPicker", durationNumPicker);
                    startActivity(myIntent);
                }
                else if(untilRadioButton.isChecked() && durationDatePicker != null){
                    myIntent.putExtra("durationDate", durationDatePicker);
                    startActivity(myIntent);
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Please select a radio button and input the corresponding value";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }


    public void removeFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(fragmentA.isAdded()){
            ft.hide(fragmentA);
        }

        if(fragmentB.isAdded()) {
            ft.hide(fragmentB);
        }
        ft.commit();
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if(fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(fragment).commit();
        }*/
    }

    public void displayNumDaysFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(fragmentB.isAdded()) {
            ft.show(fragmentB);
        } else {
            ft.add(R.id.fragmentContainer,fragmentB,"B");
        }

        if(fragmentA.isAdded()) {
            ft.hide(fragmentA);
        }
        ft.commit();
        /*
        DurationNumDaysFragment fragment = (DurationNumDaysFragment) getSupportFragmentManager().findFragmentByTag("numDays");
        if(fragment != null && fragment.isAdded()) {

        } else {
            //Get the fragment manager in order to start transaction and check if other fragment is visible
            FragmentManager fragmentManager = getSupportFragmentManager();
            //DurationUntilDateFragment durationUntilDateFragment = (DurationUntilDateFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, new DurationNumDaysFragment(), "numDays");
            fragmentTransaction.commit();
        }*/

    }

    /*public void displayNumDaysFragmentHelper() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DurationNumDaysFragment durationNumDaysFragment = DurationNumDaysFragment.newInstance();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Add the fragment
        fragmentTransaction.add(R.id.fragmentContainer,durationNumDaysFragment).addToBackStack(null).commit();
    }*/

    public void displayUntilDateFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(fragmentA.isAdded()) {
            ft.show(fragmentA);
        } else {
            ft.add(R.id.fragmentContainer,fragmentA,"A");
        }

        if(fragmentB.isAdded()) {
            ft.hide(fragmentB);
        }
        ft.commit();
        /*DurationUntilDateFragment fragment = (DurationUntilDateFragment) getSupportFragmentManager().findFragmentByTag("untilDate");
        if(fragment != null && fragment.isAdded()) {

        } else {
            //Get the fragment manager in order to start transaction and check if other fragment is visible
            FragmentManager fragmentManager = getSupportFragmentManager();
            //DurationNumDaysFragment durationNumDaysFragment = (DurationNumDaysFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, new DurationUntilDateFragment(), "untilDate");
            fragmentTransaction.commit();
        }*/

    }

    /*public void displayUntilDateFragmentHelper() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DurationUntilDateFragment durationUntilDateFragment = DurationUntilDateFragment.newInstance();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Add the Fragment
        fragmentTransaction.replace(R.id.fragmentContainer,durationUntilDateFragment).addToBackStack(null).commit();
    }*/

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        // Month int returned by date picker starts at 0 for January
        String monthString = Integer.toString(month+1);
        String dayString = Integer.toString(day);
        String yearString = Integer.toString(year);
        String dateMessage = (monthString + "/" + dayString + "/" + yearString);
        TextView startDate = findViewById(R.id.startDate);

        if(dateMessage.equals(currentDate())) {
            startDate.setText(getResources().getString(R.string.todayString));
        } else {
            startDate.setText(dateMessage);
        }
    }

    public String currentDate() {
        final Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        String currentMonthString = Integer.toString(currentMonth+1);
        String currentDayString = Integer.toString(currentDay);
        String currentYearString = Integer.toString(currentYear);
        return currentMonthString + "/" + currentDayString + "/" + currentYearString;
    }

    @Override
    public void durationDatePickerValue(String val) {
        durationDatePicker = val;
    }

    @Override
    public void durationNumPickerValue(String val) {
        durationNumPicker = val;
    }
}