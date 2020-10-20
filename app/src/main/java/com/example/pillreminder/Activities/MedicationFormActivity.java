package com.example.pillreminder.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pillreminder.Fragments.Frequency.DailyAddReminderFragment;
import com.example.pillreminder.Fragments.Frequency.DailyEveryXHours;
import com.example.pillreminder.Fragments.Frequency.EveryXDaysFragment;
import com.example.pillreminder.Fragments.Frequency.SpecificDaysFragment;
import com.example.pillreminder.Fragments.InventoryNumPickerFragment;
import com.example.pillreminder.R;

public class MedicationFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, InventoryNumPickerFragment.returnInventoryNumPickerValue {
    TextView inventoryBelowText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        TextView durationListener = findViewById(R.id.durationID);
        durationListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicationFormActivity.this, DurationActivity.class));
            }
        });

        inventoryBelowText = findViewById(R.id.inventoryBelowText);
        TextView inventoryListener = findViewById(R.id.inventory);
        inventoryListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayInventoryNumPickerFragment();
            }
        });


        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.frequency_of_intake_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.formFragmentContainer);
                if(fragment != null) {
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.hide(fragment).commit();
                }
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.formFragmentContainer, new DailyAddReminderFragment()).commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.formFragmentContainer, new DailyEveryXHours()).commit();
                break;
            case 3:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.formFragmentContainer, new EveryXDaysFragment()).commit();
                break;
            case 4:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.formFragmentContainer, new SpecificDaysFragment()).commit();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void displayInventoryNumPickerFragment() {
        DialogFragment newFragment = new InventoryNumPickerFragment();
        newFragment.show(getSupportFragmentManager(), "numPicker");
    }

    @Override
    public void InventoryNumPickerValue(int val) {
        String pillNumber = val + " pill(s) remaining";
        inventoryBelowText.setText(pillNumber);
    }
}