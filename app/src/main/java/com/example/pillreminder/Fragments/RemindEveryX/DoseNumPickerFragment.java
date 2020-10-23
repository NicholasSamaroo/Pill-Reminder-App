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

import com.example.pillreminder.Fragments.Duration.DurationNumPickerFragment;
import com.example.pillreminder.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoseNumPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoseNumPickerFragment extends DialogFragment {
    final int PICKER_MAX = 11;
    final int PICKER_MIN = 0;
    private DoseNumPickerFragment.returnNumPickerValue callback;

    public interface returnNumPickerValue {
        public void numPickerValue(String val);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoseNumPickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoseNumPickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoseNumPickerFragment newInstance(String param1, String param2) {
        DoseNumPickerFragment fragment = new DoseNumPickerFragment();
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
            callback = (DoseNumPickerFragment.returnNumPickerValue) getTargetFragment();
        } catch(ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement returnNumPickerValue");
        }
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View inflateNumPicker = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dose_num_picker,null);

        final NumberPicker picker = inflateNumPicker.findViewById(R.id.doseNumPicker);
        picker.setMaxValue(PICKER_MAX);
        picker.setMinValue(PICKER_MIN);
        final String[] pickerValues = new String[12];

        for(int i = 1; i <= 12; i++) {
            pickerValues[i - 1] = i + "";
        }
        picker.setWrapSelectorWheel(false);
        picker.setDisplayedValues(pickerValues);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflateNumPicker)
                .setTitle(R.string.dose)
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