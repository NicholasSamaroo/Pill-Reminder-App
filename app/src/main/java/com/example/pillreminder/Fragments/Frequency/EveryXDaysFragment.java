package com.example.pillreminder.Fragments.Frequency;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pillreminder.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EveryXDaysFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// Fragment created by choosing the "Every X Days" option in the spinner located in the Medication Form activity
public class EveryXDaysFragment extends Fragment implements EveryXDaysNumPickerFragment.returnEveryXDaysNumPickerValue {
    TextView everyBelowText;

    private returnEveryXDaysValue callback;

    public interface returnEveryXDaysValue {
        public void everyXDaysValue(String val);
    }

    public EveryXDaysFragment() {
        // Required empty public constructor
    }

    public static EveryXDaysFragment newInstance(String param1, String param2) {
        return new EveryXDaysFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (returnEveryXDaysValue) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_every_x_days, container, false);
        TextView everyPickerListener = view.findViewById(R.id.every);
        everyBelowText= view.findViewById(R.id.everyBelowText);
        everyPickerListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNumPicker();
            }
        });
        return view;
    }

    @Override
    public void everyXDaysNumPickerValue(String val) {
        String everyBelow = val + " days";
        everyBelowText.setText(everyBelow);
        callback.everyXDaysValue(val);
    }

    public void displayNumPicker() {
        DialogFragment newFragment = new EveryXDaysNumPickerFragment();
        newFragment.setTargetFragment(this, 0);
        newFragment.show(getFragmentManager(), "numPicker");
    }
}