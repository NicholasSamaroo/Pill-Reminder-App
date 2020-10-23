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
public class DurationNumPickerFragment extends DialogFragment {
    private returnNumPickerValue callback;

    public interface returnNumPickerValue {
        public void numPickerValue(String val);
    }

    final int PICKER_MAX = 364;
    final int PICKER_MIN = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DurationNumPickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DurationNumPickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DurationNumPickerFragment newInstance(String param1, String param2) {
        DurationNumPickerFragment fragment = new DurationNumPickerFragment();
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
            callback = (returnNumPickerValue) getTargetFragment();
        } catch(ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement returnNumPickerValue");
        }
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_duration_num_picker, container, false);
        NumberPicker picker = view.findViewById(R.id.numPicker);
        picker.setMaxValue(PICKERMAX);
        picker.setMinValue(PICKERMIN);
        String[] pickerValues = new String[PICKERMAX];

        for(int i = 1; i <= PICKERMAX; i++) {
            pickerValues[i - 1] = i + "";
        }
        picker.setWrapSelectorWheel(false);
        picker.setDisplayedValues(pickerValues);

        return view;
    }*/

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