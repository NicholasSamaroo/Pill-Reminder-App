package com.example.pillreminder.Fragments.Duration;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pillreminder.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DurationUntilDateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// Fragment for the "Until Date" Radio Button
// This fragment is created by clicking on the "Until Date" radio button
public class DurationUntilDateFragment extends Fragment implements DatePickerFragmentUntilDate.returnDatePicker {
    private DurationUntilDateFragment.returnDurationDatePickerValue callback;

    public interface returnDurationDatePickerValue {
        public void durationDatePickerValue(String val);
    }

    TextView untilTextViewBottomText;

    public DurationUntilDateFragment() {
        // Required empty public constructor
    }

    public static DurationUntilDateFragment newInstance() {
        return new DurationUntilDateFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (returnDurationDatePickerValue) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_duration_until_date, container, false);
        TextView untilTextView = view.findViewById(R.id.fragmentUntilDateTextView);
        untilTextViewBottomText = view.findViewById(R.id.fragmentUntilDateBottomText);
        untilTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDatePicker();
            }
        });
        return view;
    }



    public void displayDatePicker() {
        DialogFragment picker = new DatePickerFragmentUntilDate();
        picker.setTargetFragment(this,0);
        picker.show(getFragmentManager(),"datePicker");
    }

    public void datePickerValues(int year, int month, int dayOfMonth) {
        // Month int returned by date picker starts at 0 for January
        String monthString = Integer.toString(month+1);
        String dayString = Integer.toString(dayOfMonth);
        String yearString = Integer.toString(year);
        String dateMessage = (monthString + "/" + dayString + "/" + yearString);


        if(dateMessage.equals(currentDate())) {
            untilTextViewBottomText.setText(getResources().getString(R.string.todayString));
            callback.durationDatePickerValue(getResources().getString(R.string.todayString));
        } else {
            untilTextViewBottomText.setText(dateMessage);
            callback.durationDatePickerValue(dateMessage);
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
