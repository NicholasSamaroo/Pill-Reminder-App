package com.example.pillreminder.Fragments;

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
 * Use the {@link DurationNumDaysFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DurationNumDaysFragment extends Fragment implements DurationNumPickerFragment.returnNumPickerValue {
    private DurationNumDaysFragment.returnDurationNumPickerValue callback;

    public interface returnDurationNumPickerValue {
        public void durationNumPickerValue(String val);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;
    TextView tv;


    public DurationNumDaysFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DurationNumDaysFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DurationNumDaysFragment newInstance() {
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //fragment.setArguments(args);
        return new DurationNumDaysFragment();
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
        callback = (returnDurationNumPickerValue) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_duration_num_days, container, false);
        TextView durationTextView = view.findViewById(R.id.fragmentDuration);
        tv = view.findViewById(R.id.fragmentDurationText);
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
        /*FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.numPickerFragmentContainer, new DurationNumPickerFragment()).commit();*/
    }

    @Override
    public void numPickerValue(int val) {
        String durationText = "For " + Integer.toString(val) + " day(s)";
        tv.setText(durationText);
        callback.durationNumPickerValue(durationText);
    }
}