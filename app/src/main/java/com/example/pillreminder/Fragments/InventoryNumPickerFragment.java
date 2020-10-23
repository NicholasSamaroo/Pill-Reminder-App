package com.example.pillreminder.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.pillreminder.Fragments.RemindEveryX.DoseNumPickerFragment;
import com.example.pillreminder.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InventoryNumPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InventoryNumPickerFragment extends DialogFragment {
    private InventoryNumPickerFragment.returnInventoryNumPickerValue callback;

    public interface returnInventoryNumPickerValue {
        public void InventoryNumPickerValue(int val);
    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InventoryNumPickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InventoryNumPickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InventoryNumPickerFragment newInstance(String param1, String param2) {
        InventoryNumPickerFragment fragment = new InventoryNumPickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (returnInventoryNumPickerValue) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View inflateNumPicker = LayoutInflater.from(getContext()).inflate(R.layout.fragment_inventory_num_picker, null);

        final EditText pillEditText = inflateNumPicker.findViewById(R.id.pillEditText);
        pillEditText.setGravity(Gravity.CENTER);
        pillEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AlertDialog dialog = (AlertDialog) getDialog();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String test = pillEditText.getText().toString();
                if(test.equals("")){
                    AlertDialog dialog = (AlertDialog) getDialog();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflateNumPicker)
                .setMessage("Input the amount of pills you currently have. We will send an alert when you are running low on medication")
                .setTitle(R.string.inventory)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //try {
                            final int inventoryValue = Integer.parseInt(pillEditText.getText().toString());
                            callback.InventoryNumPickerValue(inventoryValue);
                        //}
                        //catch (NumberFormatException e) {
                            dismiss();
                        //}
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

    @Override
    public void onResume() {
        super.onResume();
        // disable positive button by default
        AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }
}