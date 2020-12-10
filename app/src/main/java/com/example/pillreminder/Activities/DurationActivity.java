package com.example.pillreminder.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pillreminder.Fragments.Duration.DatePickerFragmentStartDate;
import com.example.pillreminder.Fragments.Duration.DurationNumDaysFragment;
import com.example.pillreminder.Fragments.Duration.DurationUntilDateFragment;
import com.example.pillreminder.R;

import java.util.Calendar;

public class DurationActivity extends AppCompatActivity implements DurationUntilDateFragment.returnDurationDatePickerValue, DurationNumDaysFragment.returnDurationNumPickerValue {
    String durationDatePicker;
    String durationNumPicker;

    // We are displaying two different fragments by clicking on two different radio buttons
    // Namely, the fragment for the "Until Date" radio button and the fragment for the "For X amount of days" radio button
    // The "No end date" radio button just clears the fragment container which holds the two fragments
    private DurationUntilDateFragment fragmentA;
    private DurationNumDaysFragment fragmentB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duration);

        if (savedInstanceState == null) {
            fragmentA = DurationUntilDateFragment.newInstance();
            fragmentB = DurationNumDaysFragment.newInstance();
        }

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        final RadioButton noDate = findViewById(R.id.noEndDateRadioButton);
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

        // We want to navigate back to the medication form activity with the value the user chooses here
        // So we utilize intents to pass the value back
        Button durationIntentButton = findViewById(R.id.durationButton);
        durationIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DurationActivity.this, MedicationFormActivity.class);
                if (noDate.isChecked()) {
                    myIntent.putExtra("durationNoDate", "No end date");
                    startActivity(myIntent);
                } else if (xRadioButton.isChecked() && durationNumPicker != null) {
                    myIntent.putExtra("durationNumPicker", durationNumPicker);
                    startActivity(myIntent);
                } else if (untilRadioButton.isChecked() && durationDatePicker != null) {
                    myIntent.putExtra("durationDate", durationDatePicker);
                    startActivity(myIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select a radio button and input the corresponding value", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void removeFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (fragmentA.isAdded()) {
            ft.hide(fragmentA);
        }

        if (fragmentB.isAdded()) {
            ft.hide(fragmentB);
        }
        ft.commit();
    }

    public void displayNumDaysFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragmentB.isAdded()) {
            ft.show(fragmentB);
        } else {
            ft.add(R.id.fragmentContainer, fragmentB, "B");
        }

        if (fragmentA.isAdded()) {
            ft.hide(fragmentA);
        }
        ft.commit();
    }

    public void displayUntilDateFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragmentA.isAdded()) {
            ft.show(fragmentA);
        } else {
            ft.add(R.id.fragmentContainer, fragmentA, "A");
        }

        if (fragmentB.isAdded()) {
            ft.hide(fragmentB);
        }
        ft.commit();
    }

    // Method to show the date picker dialog for the "Start Date" clickable
    // An XML onClick attribute is set for the text view
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragmentStartDate();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // Process the date picker result for "Start Date"
    public void processDatePickerResult(int year, int month, int day) {
        // Month int returned by date picker starts at 0 for January
        String monthString = Integer.toString(month + 1);
        String dayString = Integer.toString(day);
        String yearString = Integer.toString(year);
        String dateMessage = (monthString + "/" + dayString + "/" + yearString);
        TextView startDate = findViewById(R.id.startDateBottomText);

        if (dateMessage.equals(currentDate())) {
            startDate.setText(getResources().getString(R.string.todayString));
        } else {
            startDate.setText(dateMessage);
        }
    }

    // Helper function to determine if the user has chosen the current day to start.
    public String currentDate() {
        final Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        String currentMonthString = Integer.toString(currentMonth + 1);
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