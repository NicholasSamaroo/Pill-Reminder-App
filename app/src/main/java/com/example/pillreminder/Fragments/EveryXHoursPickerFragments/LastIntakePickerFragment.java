package com.example.pillreminder.Fragments.EveryXHoursPickerFragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.example.pillreminder.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LastIntakePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LastIntakePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private LastIntakePickerFragment.returnLastIntakePickerValue callback;

    public interface returnLastIntakePickerValue {
        public void lastIntakeHourAndMinutes(int hour,String minutesPrecede,int minutes, String amPm);
    }

    public LastIntakePickerFragment() {
        // Required empty public constructor
    }

    public static LastIntakePickerFragment newInstance(String param1, String param2) {
        return new LastIntakePickerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (LastIntakePickerFragment.returnLastIntakePickerValue) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement returnLastIntakePickerValue");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View inflateNumPicker = LayoutInflater.from(getContext()).inflate(R.layout.fragment_last_intake_picker, null);

        TimePicker lastIntakeTimePicker = inflateNumPicker.findViewById(R.id.lastIntakeTimePicker);
        lastIntakeTimePicker.setIs24HourView(true);

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getContext(),this,hour,minute,false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String amPm;
        int currentHour;
        String minutePrecede = "";

        if(minute < 10) {
            minutePrecede = "0";
        }

        if(hourOfDay < 12) {
            amPm = "AM";
            callback.lastIntakeHourAndMinutes(hourOfDay,minutePrecede,minute,amPm);
        } else if(hourOfDay == 12){
            amPm = "PM";
            callback.lastIntakeHourAndMinutes(hourOfDay,minutePrecede,minute,amPm);
        } else if(hourOfDay >= 13 && hourOfDay < 24) {
            amPm = "PM";
            currentHour = hourOfDay - 12;
            callback.lastIntakeHourAndMinutes(currentHour,minutePrecede,minute,amPm);
        }

    }
}