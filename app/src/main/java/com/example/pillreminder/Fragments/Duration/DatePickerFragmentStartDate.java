package com.example.pillreminder.Fragments.Duration;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.widget.DatePicker;

import com.example.pillreminder.Activities.DurationActivity;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatePickerFragmentStartDate#newInstance} factory method to
 * create an instance of this fragment.
 */

// Date Picker dialog for the "Start Date" clickable text view in the Duration activity
public class DatePickerFragmentStartDate extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static DatePickerFragmentStartDate newInstance() {
        return new DatePickerFragmentStartDate();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        DurationActivity activity = (DurationActivity) getActivity();
        activity.processDatePickerResult(year,month,dayOfMonth);

    }
}