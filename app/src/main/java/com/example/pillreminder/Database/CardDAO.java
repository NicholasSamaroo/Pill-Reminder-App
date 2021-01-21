package com.example.pillreminder.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pillreminder.Model.CardData;

import java.util.List;

@Dao
public interface CardDAO {

    @Insert
    void insert(CardData cardData);

    @Delete
    void deleteItem(CardData cardData);

    @Query("DELETE FROM card_table WHERE alarmID = :itemId")
    void deleteItemByItemId(int itemId);

    @Query("SELECT * FROM card_table ORDER BY alarmID ASC")
    LiveData<List<CardData>> getAllCardValues();

    @Query("SELECT * FROM card_table")
    List<CardData> getSavedCardValues();
}
