package com.example.pillreminder.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.pillreminder.Fragments.Duration.DatePickerFragment;
import com.example.pillreminder.Fragments.Duration.DurationNumDaysFragment;
import com.example.pillreminder.Fragments.Duration.DurationUntilDateFragment;
import com.example.pillreminder.R;

import java.util.Calendar;

public class DurationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duration);

        RadioButton noDate = findViewById(R.id.noDate);
        noDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragments();
            }
        });

        RadioButton xRadioButton = findViewById(R.id.xDaysRadioButton);
        xRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumDaysFragment();
            }
        });

        RadioButton untilRadioButton = findViewById(R.id.untilRadioButton);
        untilRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayUntilDateFragment();
            }
        });
    }

    public void removeFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
        if(fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(fragment).commit();
        }
    }

    public void displayNumDaysFragment() {
        //Get the fragment manager in order to start transaction and check if other fragment is visible
        FragmentManager fragmentManager = getSupportFragmentManager();
        //DurationUntilDateFragment durationUntilDateFragment = (DurationUntilDateFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new DurationNumDaysFragment()).commit();

    }

    /*public void displayNumDaysFragmentHelper() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DurationNumDaysFragment durationNumDaysFragment = DurationNumDaysFragment.newInstance();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Add the fragment
        fragmentTransaction.add(R.id.fragmentContainer,durationNumDaysFragment).addToBackStack(null).commit();
    }*/

    public void displayUntilDateFragment() {
        //Get the fragment manager in order to start transaction and check if other fragment is visible
        FragmentManager fragmentManager = getSupportFragmentManager();
        //DurationNumDaysFragment durationNumDaysFragment = (DurationNumDaysFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new DurationUntilDateFragment()).commit();

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
}