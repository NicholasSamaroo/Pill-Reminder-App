package com.example.pillreminder.Fragments.Frequency;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.pillreminder.Fragments.Duration.DurationNumPickerFragment;
import com.example.pillreminder.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpecificDaysFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// Fragment created by choosing the "Specific days of the week" option located in the spinner back in the Medication Form Activity
public class SpecificDaysFragment extends Fragment implements View.OnClickListener {
    ArrayList<Integer> daysOfWeek = new ArrayList<>();
    private SpecificDaysFragment.returnDaysOfWeekArrayList callback;

    public interface returnDaysOfWeekArrayList {
        public void daysOfWeekArrayList(ArrayList<Integer> days);
    }

    public SpecificDaysFragment() {
        // Required empty public constructor
    }

    public static SpecificDaysFragment newInstance(String param1, String param2) {
        return new SpecificDaysFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (returnDaysOfWeekArrayList) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_specific_days, container, false);

        CheckBox monday = view.findViewById(R.id.monday);
        monday.setOnClickListener(this);

        CheckBox tuesday = view.findViewById(R.id.tuesday);
        tuesday.setOnClickListener(this);

        CheckBox wednesday = view.findViewById(R.id.wednesday);
        wednesday.setOnClickListener(this);

        CheckBox thursday = view.findViewById(R.id.thursday);
        thursday.setOnClickListener(this);

        CheckBox friday = view.findViewById(R.id.friday);
        friday.setOnClickListener(this);

        CheckBox saturday = view.findViewById(R.id.saturday);
        saturday.setOnClickListener(this);

        CheckBox sunday = view.findViewById(R.id.sunday);
        sunday.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        boolean checked = ((CheckBox) v).isChecked();

        switch (v.getId()) {
            case R.id.sunday:
                if (checked) {
                    if (!daysOfWeek.contains(1)) {
                        daysOfWeek.add(1);
                    }
                } else {
                    daysOfWeek.remove(Integer.valueOf(1));
                }
                break;
            case R.id.monday:
                if (checked) {
                    if (!daysOfWeek.contains(2)) {
                        daysOfWeek.add(2);
                    }
                } else {
                    daysOfWeek.remove(Integer.valueOf(2));
                }
                break;
            case R.id.tuesday:
                if (checked) {
                    if (!daysOfWeek.contains(3)) {
                        daysOfWeek.add(3);
                    }
                } else {
                    daysOfWeek.remove(Integer.valueOf(3));
                }
                break;
            case R.id.wednesday:
                if (checked) {
                    if (!daysOfWeek.contains(4)) {
                        daysOfWeek.add(4);
                    }
                } else {
                    daysOfWeek.remove(Integer.valueOf(4));
                }
                break;
            case R.id.thursday:
                if (checked) {
                    if (!daysOfWeek.contains(5)) {
                        daysOfWeek.add(5);
                    }
                } else {
                    daysOfWeek.remove(Integer.valueOf(5));
                }
                break;
            case R.id.friday:
                if (checked) {
                    if (!daysOfWeek.contains(6)) {
                        daysOfWeek.add(6);
                    }
                } else {
                    daysOfWeek.remove(Integer.valueOf(6));
                }
                break;
            case R.id.saturday:
                if (checked) {
                    if (!daysOfWeek.contains(7)) {
                        daysOfWeek.add(7);
                    }
                } else {
                    daysOfWeek.remove(Integer.valueOf(7));
                }
                break;
        }
        //Log.e("test", String.valueOf(daysOfWeek));
        callback.daysOfWeekArrayList(daysOfWeek);
    }

}
