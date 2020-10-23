package com.example.pillreminder.Fragments.RemindEveryX;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.example.pillreminder.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstIntakePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstIntakePickerFragment extends DialogFragment {
    final int HOUR_MINUTES_MAX = 11;
    final int HOUR_MINUTES_AM_PM_MIN = 0;
    final int AM_PM_MAX = 1;

    private FirstIntakePickerFragment.returnFirstIntakePickerValue callback;

    public interface returnFirstIntakePickerValue {
        public void hourAndMinutes(String hour, String minutes, String amPm);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstIntakePickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstIntakePickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstIntakePickerFragment newInstance(String param1, String param2) {
        FirstIntakePickerFragment fragment = new FirstIntakePickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (FirstIntakePickerFragment.returnFirstIntakePickerValue) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement returnFirstIntakePickerValue");
        }
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2); */
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View inflateNumPicker = LayoutInflater.from(getContext()).inflate(R.layout.fragment_first_intake_picker, null);

        final NumberPicker hourPicker = inflateNumPicker.findViewById(R.id.hours);
        hourPicker.setMaxValue(HOUR_MINUTES_MAX);
        hourPicker.setMinValue(HOUR_MINUTES_AM_PM_MIN);
        final String[] pickerValues = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        hourPicker.setDisplayedValues(pickerValues);

        final NumberPicker minutePicker = inflateNumPicker.findViewById(R.id.minutes);
        minutePicker.setMaxValue(HOUR_MINUTES_MAX);
        minutePicker.setMinValue(HOUR_MINUTES_AM_PM_MIN);
        final String[] minutePickerValues = {"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};

        minutePicker.setDisplayedValues(minutePickerValues);

        final NumberPicker amPmPicker = inflateNumPicker.findViewById(R.id.amOrPm);
        amPmPicker.setMaxValue(AM_PM_MAX);
        amPmPicker.setMinValue(HOUR_MINUTES_AM_PM_MIN);
        final String[] amPmPickerValues = {"AM", "PM"};

        amPmPicker.setDisplayedValues(amPmPickerValues);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflateNumPicker)
                .setTitle(R.string.first_intake)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String hourValue = pickerValues[hourPicker.getValue()];
                        final String minuteValue = minutePickerValues[minutePicker.getValue()];
                        final String amPmValue = amPmPickerValues[amPmPicker.getValue()];
                        callback.hourAndMinutes(hourValue, minuteValue, amPmValue);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }
}