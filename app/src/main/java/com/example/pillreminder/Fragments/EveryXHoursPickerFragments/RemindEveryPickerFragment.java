package com.example.pillreminder.Fragments.EveryXHoursPickerFragments;

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
 * Use the {@link RemindEveryPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RemindEveryPickerFragment extends DialogFragment {

    final int PICKER_MAX = 12;
    final int PICKER_MIN = 0;

    private RemindEveryPickerFragment.returnRemindEveryPickerValue callback;

    public interface returnRemindEveryPickerValue {
        public void remindEveryPickerValue(String val);
    }

    public RemindEveryPickerFragment() {
        // Required empty public constructor
    }

    public static RemindEveryPickerFragment newInstance(String param1, String param2) {
        return new RemindEveryPickerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (RemindEveryPickerFragment.returnRemindEveryPickerValue) getTargetFragment();
        } catch(ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement returnRemindEveryPickerValue");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View inflateNumPicker = LayoutInflater.from(getContext()).inflate(R.layout.fragment_remind_every_picker, null);

        final NumberPicker picker = inflateNumPicker.findViewById(R.id.remindEveryPicker);
        picker.setMaxValue(PICKER_MAX);
        picker.setMinValue(PICKER_MIN);
        final String[] pickerValues = {"0.5 hours", "1 hour", "2 hours", "3 hours", "4 hours", "5 hours", "6 hours", "7 hours", "8 hours", "9 hours",
                "10 hours", "11 hours", "12 hours"};

        picker.setWrapSelectorWheel(false);
        picker.setDisplayedValues(pickerValues);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflateNumPicker)
                .setTitle(R.string.duration)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String pickerValue = pickerValues[picker.getValue()];
                        callback.remindEveryPickerValue(pickerValue);
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