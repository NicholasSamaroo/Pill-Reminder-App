package com.example.pillreminder.Activities;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pillreminder.AlarmReceiver;
import com.example.pillreminder.Fragments.Frequency.DailyEveryXHours;
import com.example.pillreminder.Fragments.Frequency.EveryXDaysFragment;
import com.example.pillreminder.Fragments.Frequency.SpecificDaysFragment;
import com.example.pillreminder.Fragments.InventoryNumPickerFragment;
import com.example.pillreminder.Model.CardData;
import com.example.pillreminder.R;

import java.util.ArrayList;
import java.util.Calendar;


public class MedicationFormActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, InventoryNumPickerFragment.returnInventoryNumPickerValue, SpecificDaysFragment.returnDaysOfWeekArrayList,
        EveryXDaysFragment.returnEveryXDaysValue {

    private int alarmID;
    private int reminderHour = -1;
    private int reminderMinute = -1;
    private int everyXDaysNumber = 0;

    private TextView inventoryBelowText;
    private ImageButton imageButtonOne;
    private Button firstButton;

    private final StringBuilder daysOfWeek = new StringBuilder();
    private final ArrayList<Integer> alarmIdForDaysOfWeek = new ArrayList<>();
    private ArrayList<Integer> finalDaysOfWeek = null;

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private Intent notifyIntent;
    private SharedPreferences sharedPref;

    private Calendar calendar;
    private EditText medicationName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);

        // Restore the alarm ID from shared preferences in order to create unique pending intents
        sharedPref = MedicationFormActivity.this.getPreferences(Context.MODE_PRIVATE);
        alarmID = sharedPref.getInt("reminderIntegerId", 0);

        TextView durationListener = findViewById(R.id.durationID);
        durationListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This feature will be implemented in the near future", Toast.LENGTH_LONG).show();
            }
        });

           /*durationListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicationFormActivity.this, DurationActivity.class));
            }
        });

        TextView durationBelowText = findViewById(R.id.durationBelowText);

        // Get user input from duration activity and set the text view below "Duration" to that value
        String durationValue;
        Intent intent = getIntent();
        if (intent.hasExtra("durationNoDate")) {
            durationBelowText.setText(intent.getStringExtra("durationNoDate"));
        } else if (intent.hasExtra("durationNumPicker")) {
            durationBelowText.setText(intent.getStringExtra("durationNumPicker"));
        } else if (intent.hasExtra("durationDate")) {
            durationValue = "Until " + intent.getStringExtra("durationDate");
            durationBelowText.setText(durationValue);
        } */

        // Instantiate the text view below "Inventory" so we can set it to the number the user chooses
        // via our implementation of inventory number picker fragment which has callback methods to return the number
        inventoryBelowText = findViewById(R.id.inventoryBelowText);
        TextView inventoryListener = findViewById(R.id.inventory);
        inventoryListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This feature will be implemented in the near future", Toast.LENGTH_LONG).show();
                //displayInventoryNumPickerFragment();
            }
        });

        // Spinner for fragments; string array defined in strings.xml
        final Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.frequency_of_intake_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Inflate custom row for choosing reminder time
        // Only one reminder is allowed at this time so we check the container to see if there is a view already in there
        // If not, we set the onClick listener for the button to set the time and we disable the 'X' button so the user can't close the alarm
        final TextView addReminderClickable = findViewById(R.id.addReminder);
        final LinearLayout container = findViewById(R.id.addReminderContainer);
        final LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        addReminderClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View firstLayout = inflater.inflate(R.layout.add_reminder_row_layout, container, false);

                if (container.getChildCount() >= 1) {
                    Toast.makeText(getApplicationContext(), "Only one reminder is allowed at this time", Toast.LENGTH_LONG).show();
                } else {
                    container.addView(firstLayout);
                    firstButton = firstLayout.findViewById(R.id.reminder);
                    imageButtonOne = firstLayout.findViewById(R.id.closeReminder);
                    imageButtonOne.setClickable(false);
                    firstButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reminderTimePicker();
                        }
                    });
                }
            }
        });

        // Button to navigate back to the main activity
        // Before we navigate back to the main activity we need to make sure of a few things
        // The edit text is not empty and the user has chosen a time for the alarm
        // Each different alarm type has varying checks to make sure the user has filled out all pertinent information

        medicationName = findViewById(R.id.medicationName);
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            // Things to check for before we create the alarm
            // Edit text needs to be filled out
            // Duration needs to be chosen
            // Frequency needs to be chosen (i.e value from the spinner)
            // Reminder time needs to be chose from custom layout
            @Override
            public void onClick(View v) {
                switch (spinner.getSelectedItemPosition()) {
                    case 0:
                        setDailyAlarm();
                        break;
                    case 1:
                        setDailyEveryXHoursAlarm();
                        break;
                    case 2:
                        setEveryXDaysAlarm();
                        break;
                    case 3:
                        setSpecificDaysOfWeekAlarm();
                        break;
                }
            }
        });
    }

    private void setDailyAlarm() {
        if (reminderHour != -1 && reminderMinute != -1 && !(medicationName.getText().toString().isEmpty())) {
            notifyIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
            notifyIntent.putExtra("notificationTime", firstButton.getText().toString());
            notifyIntent.putExtra("notificationMedicationName", medicationName.getText().toString());
            alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), alarmID, notifyIntent, 0);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, reminderHour);
            calendar.set(Calendar.MINUTE, reminderMinute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

            CardData cardData = new CardData(alarmID, "Daily", medicationName.getText().toString(), "Daily", firstButton.getText().toString());
            alarmID++;
            sharedPref = MedicationFormActivity.this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("reminderIntegerId", alarmID);
            editor.apply();

            Intent formToMain = new Intent();
            formToMain.putExtra("daily", cardData);
            setResult(Activity.RESULT_OK, formToMain);
            finish();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please ensure you have entered your medication name and have chosen a time for your Daily alarm", Toast.LENGTH_LONG).show();
        }
    }

    private void setDailyEveryXHoursAlarm() {
        // Daily every X hours
        Toast.makeText(getApplicationContext(), "The \"Daily every X hours\" feature will be implemented in the near future", Toast.LENGTH_LONG).show();
    }

    private void setEveryXDaysAlarm() {
        // Every X DAYS
        if (reminderHour != -1 && reminderMinute != -1 && !(medicationName.getText().toString().isEmpty())) {
            notifyIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
            notifyIntent.putExtra("notificationTime", firstButton.getText().toString());
            notifyIntent.putExtra("notificationMedicationName", medicationName.getText().toString());
            alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), alarmID, notifyIntent, 0);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, reminderHour);
            calendar.set(Calendar.MINUTE, reminderMinute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, everyXDaysNumber);
            }

            if (everyXDaysNumber == 0) {
                Toast.makeText(getApplicationContext(), "Please choose a number for the 'every' interval", Toast.LENGTH_LONG).show();
                return;
            }

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * everyXDaysNumber, alarmIntent);

            CardData cardData = new CardData(alarmID, "Every", medicationName.getText().toString(), "Every " + everyXDaysNumber + " days", firstButton.getText().toString());
            alarmID++;
            sharedPref = MedicationFormActivity.this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("reminderIntegerId", alarmID);
            editor.apply();

            Intent formToMain = new Intent();
            formToMain.putExtra("everyXDays", cardData);
            setResult(Activity.RESULT_OK, formToMain);
            finish();

        } else {
            Toast.makeText(getApplicationContext(),
                    "Please ensure you have entered your medication name and have chosen a time for the alarm", Toast.LENGTH_LONG).show();
        }
    }

    private void setSpecificDaysOfWeekAlarm() {
        // SPECIFIC DAYS OF THE WEEK
        if (reminderHour != -1 && reminderMinute != -1 && !(medicationName.getText().toString().isEmpty())) {
            if (finalDaysOfWeek == null || finalDaysOfWeek.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please select a day of the week", Toast.LENGTH_LONG).show();
                return;
            } else if (finalDaysOfWeek.size() == 7) {
                // If all days of the week are selected then it is really a daily alarm, so call the daily alarm method
                setDailyAlarm();
            } else {
                notifyIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                notifyIntent.putExtra("notificationTime", firstButton.getText().toString());
                notifyIntent.putExtra("notificationMedicationName", medicationName.getText().toString());
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                if (!alarmIdForDaysOfWeek.isEmpty()) {
                    alarmIdForDaysOfWeek.clear();
                }
                for (Integer i : finalDaysOfWeek) {
                    Log.e("test", String.valueOf(i));
                    calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.DAY_OF_WEEK, i);
                    calendar.set(Calendar.HOUR_OF_DAY, reminderHour);
                    calendar.set(Calendar.MINUTE, reminderMinute);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);

                    if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                        calendar.add(Calendar.DAY_OF_YEAR, 7);
                    }

                    alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), alarmID, notifyIntent, 0);
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmIntent);
                    alarmIdForDaysOfWeek.add(alarmID);
                    alarmID++;
                }
            }

            formDaysOfWeekString();
            CardData cardData = new CardData(alarmID, alarmIdForDaysOfWeek, "daysOfWeek", medicationName.getText().toString(), daysOfWeek.toString(), firstButton.getText().toString());
            alarmID++;
            sharedPref = MedicationFormActivity.this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("reminderIntegerId", alarmID);
            editor.apply();

            Intent formToMain = new Intent();
            formToMain.putExtra("specificDaysOfWeek", cardData);
            setResult(Activity.RESULT_OK, formToMain);
            finish();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please ensure you have entered your medication name and have chosen a time for the alarm", Toast.LENGTH_LONG).show();
        }
    }

    // Form a string containing the days of the week the user has chosen so it can be displayed in the card back in the main activity
    private void formDaysOfWeekString() {
        if (finalDaysOfWeek.size() == 5 && finalDaysOfWeek.contains(1) && finalDaysOfWeek.contains(2) && finalDaysOfWeek.contains(3)
                && finalDaysOfWeek.contains(4) && finalDaysOfWeek.contains(5)) {
            daysOfWeek.append("Weekdays");
        } else if (finalDaysOfWeek.size() == 2 && finalDaysOfWeek.contains(6) && finalDaysOfWeek.contains(7)) {
            daysOfWeek.append("Weekends");
        } else {
            for (Integer i : finalDaysOfWeek) {
                switch (i) {
                    case 1:
                        daysOfWeek.append("Sun.");
                        break;
                    case 2:
                        daysOfWeek.append("Mon.");
                        break;
                    case 3:
                        daysOfWeek.append("Tues.");
                        break;
                    case 4:
                        daysOfWeek.append("Wed.");
                        break;
                    case 5:
                        daysOfWeek.append("Thurs.");
                        break;
                    case 6:
                        daysOfWeek.append("Fri.");
                        break;
                    case 7:
                        daysOfWeek.append("Sat.");
                        break;
                }
            }
        }
    }

    // Time picker for the custom row
    private void reminderTimePicker() {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePicker = new TimePickerDialog(MedicationFormActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                reminderHour = hourOfDay;
                reminderMinute = minute;
                firstButton.setText(formattedTime(hourOfDay, minute));
            }
        }, hour, minute, false);
        timePicker.show();
    }

    // Helper function to format the 24 hour clock format to 12 hour clock format
    private String formattedTime(int hour, int minute) {
        String amPm;
        int currentHour;
        String minutePrecede = "";
        String finalTime = "";

        if (minute < 10) {
            minutePrecede = "0";
        }

        if (hour == 0) {
            amPm = "AM";
            finalTime = finalTime + "12" + ":" + minutePrecede + minute + " " + amPm;
            return finalTime;
        }

        if (hour < 12) {
            amPm = "AM";
            finalTime = finalTime + hour + ":" + minutePrecede + minute + " " + amPm;
        } else if (hour == 12) {
            amPm = "PM";
            finalTime = finalTime + hour + ":" + minutePrecede + minute + " " + amPm;
        } else if (hour >= 13 && hour < 24) {
            amPm = "PM";
            currentHour = hour - 12;
            finalTime = finalTime + currentHour + ":" + minutePrecede + minute + " " + amPm;
        }
        return finalTime;
    }

    // On selecting a value from the spinner show the necessary fragment
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;
        Fragment fragment;
        switch (position) {
            case 0:
                fragmentManager = getSupportFragmentManager();
                fragment = fragmentManager.findFragmentById(R.id.formFragmentContainer);
                if (fragment != null) {
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(fragment).commit();
                }
                break;
            case 1:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.formFragmentContainer, new DailyEveryXHours()).commit();
                break;
            case 2:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.formFragmentContainer, new EveryXDaysFragment()).commit();
                break;
            case 3:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.formFragmentContainer, new SpecificDaysFragment()).commit();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void displayInventoryNumPickerFragment() {
        DialogFragment newFragment = new InventoryNumPickerFragment();
        newFragment.show(getSupportFragmentManager(), "numPicker");
    }

    @Override
    public void InventoryNumPickerValue(int val) {
        String pillNumber = val + " pill(s) remaining";
        inventoryBelowText.setText(pillNumber);
    }

    @Override
    public void daysOfWeekArrayList(ArrayList<Integer> days) {
        finalDaysOfWeek = new ArrayList<>(days);
    }

    // Implemented method to get the value the user has chosen in the "Every X Days" fragment so we can make the alarm for this option
    @Override
    public void everyXDaysValue(String val) {
        everyXDaysNumber = Integer.parseInt(val);
    }
}