package com.example.pillreminder.Fragments.Duration;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

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
public class DurationUntilDateFragment extends Fragment implements DatePickerFragmentUntilDate.returnDatePicker {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

    TextView date;

    public DurationUntilDateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DurationUntilDateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DurationUntilDateFragment newInstance() {
        DurationUntilDateFragment fragment = new DurationUntilDateFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_duration_until_date, container, false);
        TextView untilTextView = view.findViewById(R.id.fragmentDurationUntilDate);
        date = view.findViewById(R.id.fragmentUntilDateText);
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
            date.setText(getResources().getString(R.string.todayString));
        } else {
            date.setText(dateMessage);
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
