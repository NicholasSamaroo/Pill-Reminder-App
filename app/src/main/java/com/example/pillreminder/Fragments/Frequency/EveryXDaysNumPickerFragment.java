package com.example.pillreminder.Fragments.Frequency;

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
 * Use the {@link EveryXDaysNumPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
// Dialog created by clicking on the "Every" text view located in the "Every X Days Fragment"
public class EveryXDaysNumPickerFragment extends DialogFragment {
    final int PICKER_MAX = 28;
    final int PICKER_MIN = 0;
    private EveryXDaysNumPickerFragment.returnEveryXDaysNumPickerValue callback;

    public interface returnEveryXDaysNumPickerValue {
        public void everyXDaysNumPickerValue(String val);
    }

    public EveryXDaysNumPickerFragment() {
        // Required empty public constructor
    }

    public static EveryXDaysNumPickerFragment newInstance(String param1, String param2) {
        return new EveryXDaysNumPickerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (EveryXDaysNumPickerFragment.returnEveryXDaysNumPickerValue) getTargetFragment();
        } catch(ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement returnEveryXDaysNumPickerValue");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View inflateNumPicker = LayoutInflater.from(getContext()).inflate(R.layout.fragment_every_x_days_num_picker,null);

        final NumberPicker picker = inflateNumPicker.findViewById(R.id.everyXDaysNumPicker);
        picker.setMaxValue(PICKER_MAX);
        picker.setMinValue(PICKER_MIN);
        final String[] pickerValues = new String[PICKER_MAX + 1];

        for(int i = 0, days = 2; i <= PICKER_MAX; i++,days++) {
            pickerValues[i] = days + "";
        }
        picker.setDisplayedValues(pickerValues);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflateNumPicker)
                .setTitle(R.string.every)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String pickerValue = pickerValues[picker.getValue()];
                        callback.everyXDaysNumPickerValue(pickerValue);
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