package com.example.pillreminder.Fragments.Duration;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.widget.DatePicker;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatePickerFragmentUntilDate#newInstance} factory method to
 * create an instance of this fragment.
 */

// Date picker fragment for the "Until Date" radio button.
// This dialog is created by clicking on the "Until" text view found in the "Until Date" radio button
public class DatePickerFragmentUntilDate extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    Context mContext;
    private returnDatePicker callback;

    public interface returnDatePicker {
        public void datePickerValues(int year, int month, int dayOfMonth);
    }

    public DatePickerFragmentUntilDate() {
        // Required empty public constructor
    }

    public static DatePickerFragmentUntilDate newInstance() {
        DatePickerFragmentUntilDate fragment = new DatePickerFragmentUntilDate();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (returnDatePicker) getTargetFragment();
        } catch(ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement returnDatePicker");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,DatePickerFragmentUntilDate.this,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        callback.datePickerValues(year,month,dayOfMonth);

    }


}