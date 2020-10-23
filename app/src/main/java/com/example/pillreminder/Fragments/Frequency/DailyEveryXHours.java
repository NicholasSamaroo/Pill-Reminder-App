package com.example.pillreminder.Fragments.Frequency;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pillreminder.Fragments.Duration.DurationNumPickerFragment;
import com.example.pillreminder.Fragments.RemindEveryX.DoseNumPickerFragment;
import com.example.pillreminder.Fragments.RemindEveryX.FirstIntakePickerFragment;
import com.example.pillreminder.Fragments.RemindEveryX.LastIntakePickerFragment;
import com.example.pillreminder.Fragments.RemindEveryX.RemindEveryPickerFragment;
import com.example.pillreminder.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyEveryXHours#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyEveryXHours extends Fragment implements DoseNumPickerFragment.returnNumPickerValue, RemindEveryPickerFragment.returnRemindEveryPickerValue, FirstIntakePickerFragment.returnFirstIntakePickerValue, LastIntakePickerFragment.returnLastIntakePickerValue {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView doseBelowText;
    TextView remindEveryBelowText;
    TextView firstIntakeBelowText;
    TextView lastIntakeBelowText;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DailyEveryXHours() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyEveryXHours.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyEveryXHours newInstance(String param1, String param2) {
        DailyEveryXHours fragment = new DailyEveryXHours();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

    public void displayRemindEveryPicker() {
        DialogFragment newFragment = new RemindEveryPickerFragment();
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
    public void hourAndMinutes(String hour, String minutes, String amPm) {
        String time = hour + ": " + minutes + " " + amPm;
        firstIntakeBelowText.setText(time);
    }

    @Override
    public void lastIntakeHourAndMinutes(String hour, String minutes, String amPm) {
        String time = hour + ": " + minutes + " " + amPm;
        lastIntakeBelowText.setText(time);
    }
}