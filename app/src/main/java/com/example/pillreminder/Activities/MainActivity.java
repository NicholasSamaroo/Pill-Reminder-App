package com.example.pillreminder.Activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.pillreminder.AlarmReceiver;
import com.example.pillreminder.Database.CardData;
import com.example.pillreminder.CardViewAdapter;
import com.example.pillreminder.Database.CardViewModel;
import com.example.pillreminder.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    final int LAUNCH_FORM_ACTIVITY = 1;
    CardViewAdapter adapter;
    ArrayList<CardData> cardData = new ArrayList<>();
    CardData holder;
    CardViewModel cardViewModel;
    RecyclerView cardViewValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel.class);

        // Load any Card objects from the database back into the recycler view
        try {
            cardViewValues = findViewById(R.id.my_recycler_view);
            cardData = (ArrayList<CardData>) cardViewModel.getAllSavedCardValues();
            adapter = new CardViewAdapter(cardData);
            cardViewValues.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            cardViewValues.setLayoutManager(linearLayoutManager);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // A Live Data observer which will update the adapter if we insert or delete an item from the database
        cardViewModel.getAllWords().observe(this, new Observer<List<CardData>>() {
            @Override
            public void onChanged(List<CardData> liveCardData) {
                adapter.setCards(liveCardData);
            }
        });

        ExtendedFloatingActionButton fab = findViewById(R.id.extendedFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navigation = new Intent(MainActivity.this, MedicationFormActivity.class);
                startActivityForResult(navigation, LAUNCH_FORM_ACTIVITY);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            /* On swipe of the card we want to:
             *   Delete the item from the recycler view
             *   Delete the item from the database
             *   Cancel any alarms associated with the card
             * An alert dialog is used to ask for confirmation before the above is executed */

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure you want to cancel this reminder?")
                        .setTitle("Cancel Reminder")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CardData delete = new CardData(adapter.getCardValue(viewHolder.getAdapterPosition()));
                                Intent intent;
                                PendingIntent pendingIntent;
                                AlarmManager alarmManager;

                                if (delete.getSpecificDaysOfWeekId() != null) {
                                    ArrayList<Integer> intermediate = delete.getSpecificDaysOfWeekId();
                                    intent = new Intent(getApplicationContext(), AlarmReceiver.class);

                                    for (Integer i : intermediate) {
                                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), i, intent, 0);
                                        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                        alarmManager.cancel(pendingIntent);
                                    }
                                } else {
                                    intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), delete.getAlarmID(), intent, 0);
                                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                    alarmManager.cancel(pendingIntent);
                                }
                                cardViewModel.deleteItem(delete);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        // Attach the helper variable to the recycler view in order to have swipe capability
        helper.attachToRecyclerView(cardViewValues);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_FORM_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.hasExtra("daily")) {
                    holder = new CardData((CardData) data.getParcelableExtra("daily"));
                } else if (data.hasExtra("everyXDays")) {
                    holder = new CardData((CardData) data.getParcelableExtra("everyXDays"));
                } else if (data.hasExtra("specificDaysOfWeek")) {
                    holder = new CardData((CardData) data.getParcelableExtra("specificDaysOfWeek"));
                }
                cardViewModel.insert(holder);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}