package com.example.pillreminder.Fragments.Duration;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pillreminder.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DurationNumDaysFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// Fragment for the "For X amount of days" radio button
// This fragment is created by clicking on the "For X amount of days" radio button
public class DurationNumDaysFragment extends Fragment implements DurationNumPickerFragment.returnNumPickerValue {
    private DurationNumDaysFragment.returnDurationNumPickerValue callback;

    public interface returnDurationNumPickerValue {
        public void durationNumPickerValue(String val);
    }

    TextView durationTextViewBottomText;


    public DurationNumDaysFragment() {
        // Required empty public constructor
    }
    public static DurationNumDaysFragment newInstance() {
        return new DurationNumDaysFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (returnDurationNumPickerValue) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_duration_num_days, container, false);
        TextView durationTextView = view.findViewById(R.id.fragmentDurationTextView);
        durationTextViewBottomText = view.findViewById(R.id.fragmentDurationBottomText);
        durationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumPicker();
            }
        });
        return view;
    }

    public void displayNumPicker() {
        DialogFragment newFragment = new DurationNumPickerFragment();
        newFragment.setTargetFragment(this, 0);
        newFragment.show(getFragmentManager(), "numPicker");
    }

    @Override
    public void numPickerValue(String val) {
        String durationText = "For " + val + " day(s)";
        durationTextViewBottomText.setText(durationText);
        callback.durationNumPickerValue(durationText);
    }
}