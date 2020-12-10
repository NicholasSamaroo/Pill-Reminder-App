package com.example.pillreminder.Fragments.Duration;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.example.pillreminder.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DurationNumPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// Num picker dialog for the "Duration" clickable text view
// This dialog is created when you click on the "Duration" text view found in the fragment spawned by clicking on the "For X amount of days" radio button
public class DurationNumPickerFragment extends DialogFragment {
    private returnNumPickerValue callback;

    public interface returnNumPickerValue {
        public void numPickerValue(String val);
    }

    final int PICKER_MAX = 364;
    final int PICKER_MIN = 0;

    public DurationNumPickerFragment() {
        // Required empty public constructor
    }

    public static DurationNumPickerFragment newInstance(String param1, String param2) {
        return new DurationNumPickerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (returnNumPickerValue) getTargetFragment();
        } catch(ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement returnNumPickerValue");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View inflateNumPicker = LayoutInflater.from(getContext()).inflate(R.layout.fragment_duration_num_picker,null);

        final NumberPicker picker = inflateNumPicker.findViewById(R.id.numPicker);
        picker.setMaxValue(PICKER_MAX);
        picker.setMinValue(PICKER_MIN);
        final String[] pickerValues = new String[PICKER_MAX];

        for(int i = 1; i <= PICKER_MAX; i++) {
            pickerValues[i - 1] = i + "";
        }
        picker.setWrapSelectorWheel(false);
        picker.setDisplayedValues(pickerValues);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflateNumPicker)
                .setTitle(R.string.duration)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String pickerValue = pickerValues[picker.getValue()];
                        callback.numPickerValue(pickerValue);
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