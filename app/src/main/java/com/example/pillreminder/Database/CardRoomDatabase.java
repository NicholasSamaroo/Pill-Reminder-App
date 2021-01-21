package com.example.pillreminder.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.pillreminder.Model.CardData;

@Database(entities = {CardData.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CardRoomDatabase extends RoomDatabase {

    public abstract CardDAO cardDAO();
    private static CardRoomDatabase INSTANCE;

    public static CardRoomDatabase getDataBase(final Context context) {
        if(INSTANCE == null) {
            synchronized (CardRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CardRoomDatabase.class, "card_database").build();
                }
            }
        }
        return INSTANCE;
    }
}
