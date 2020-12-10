package com.example.pillreminder.Fragments.Frequency;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pillreminder.Fragments.EveryXHoursPickerFragments.DoseNumPickerFragment;
import com.example.pillreminder.Fragments.EveryXHoursPickerFragments.FirstIntakePickerFragment;
import com.example.pillreminder.Fragments.EveryXHoursPickerFragments.LastIntakePickerFragment;
import com.example.pillreminder.Fragments.EveryXHoursPickerFragments.RemindEveryPickerFragment;
import com.example.pillreminder.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyEveryXHours#newInstance} factory method to
 * create an instance of this fragment.
 */

// Fragment created by choosing the "Daily every X hours" option in the spinner located in the Medication Form activity
public class DailyEveryXHours extends Fragment implements DoseNumPickerFragment.returnNumPickerValue,
        RemindEveryPickerFragment.returnRemindEveryPickerValue, FirstIntakePickerFragment.returnFirstIntakePickerValue, LastIntakePickerFragment.returnLastIntakePickerValue {

    TextView doseBelowText;
    TextView remindEveryBelowText;
    TextView firstIntakeBelowText;
    TextView lastIntakeBelowText;

    public DailyEveryXHours() {
        // Required empty public constructor
    }

    public static DailyEveryXHours newInstance() {
        return new DailyEveryXHours();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_every_x_hours, container, false);

        TextView dose = view.findViewById(R.id.dose);
        doseBelowText = view.findViewById(R.id.doseBelowText);
        TextView remindEvery = view.findViewById(R.id.remindEvery);
        remindEveryBelowText = view.findViewById(R.id.remindEveryBelowText);
        TextView firstIntake = view.findViewById(R.id.firstIntake);
        firstIntakeBelowText = view.findViewById(R.id.firstIntakeBelowText);
        TextView lastIntake = view.findViewById(R.id.lastIntake);
        lastIntakeBelowText = view.findViewById(R.id.lastIntakeBelowText);

        dose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDoseNumberPicker();
            }
        });

        remindEvery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRemindEveryPicker();
            }
        });

        firstIntake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFirstIntakePicker();
            }
        });

        lastIntake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLastIntakePicker();
            }
        });

        return view;
    }

    public void displayDoseNumberPicker() {
        DialogFragment newFragment = new DoseNumPickerFragment();
        newFragment.setTargetFragment(this, 0);
        newFragment.show(getFragmentManager(), "numPicker");
    }

    public void displayFirstIntakePicker() {
        DialogFragment newFragment = new FirstIntakePickerFragment();
        newFragment.setTargetFragment(this, 0);
        newFragment.show(getFragmentManager(), "numPicker");
    }

    public void displayLastIntakePicker() {
        DialogFragment newFragment = new LastIntakePickerFragment();
        newFragment.setTargetFragment(this, 0);
        newFragment.show(getFragmentManager(), "numPicker");
    }

    public void displayRemindEveryPicker() {
        DialogFragment newFragment = new RemindEveryPickerFragment();
        newFragment.setTargetFragment(this, 0);
        newFragment.show(getFragmentManager(), "numPicker");
    }

    @Override
    public void numPickerValue(String val) {
        String doseText = val + " pill(s)";
        doseBelowText.setText(doseText);
    }

    @Override
    public void remindEveryPickerValue(String val) {
        String hours;
        if (val.equals("0")) {
            hours = "0.5 hours";
            remindEveryBelowText.setText(hours);
        } else {
            remindEveryBelowText.setText(val);
        }
    }

    @Override
    public void firstIntakeHourAndMinutes(int hour, String minutePrecede, int minutes, String amPm) {
        String time;
        if(hour == 0) {
            time = "12" + ":" + minutePrecede + minutes + " " + amPm;
        } else {
            time = hour + ":" + minutePrecede + minutes + " " + amPm;
        }
        firstIntakeBelowText.setText(time);
    }

    @Override
    public void lastIntakeHourAndMinutes(int hour, String minutePrecede, int minutes, String amPm) {
        String time;
        if(hour == 0) {
            time = "12" + ":" + minutePrecede + minutes + " " + amPm;
        } else {
            time = hour + ":" + minutePrecede + minutes + " " + amPm;
        }
        lastIntakeBelowText.setText(time);
    }
}