package com.example.pillreminder.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.pillreminder.Database.CardDAO;
import com.example.pillreminder.Database.CardRoomDatabase;
import com.example.pillreminder.Model.CardData;

import java.util.List;

public class CardRepository {
    private final CardDAO mCardDao;
    private final LiveData<List<CardData>> mAllCards;

    public CardRepository(Application application) {
        CardRoomDatabase db = CardRoomDatabase.getDataBase(application);
        mCardDao = db.cardDAO();
        mAllCards = mCardDao.getAllCardValues();
    }

    public LiveData<List<CardData>> getAllDataBaseCards() {
        return mAllCards;
    }

    public void insert(CardData cardData) {
        new insertAsyncTask(mCardDao).execute(cardData);
    }

    public static class insertAsyncTask extends AsyncTask<CardData, Void, Void> {
        private CardDAO mAsyncTaskDao;

        insertAsyncTask(CardDAO cardDAO) {
            mAsyncTaskDao = cardDAO;
        }

        @Override
        protected Void doInBackground(final CardData... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void deleteItem(CardData cardData) {
        new deleteAsyncTask(mCardDao).execute(cardData);
    }

    private static class deleteAsyncTask extends AsyncTask<CardData, Void, Void> {
        private CardDAO mAsyncTaskDao;

        deleteAsyncTask(CardDAO cardDAO) {
            mAsyncTaskDao = cardDAO;
        }

        @Override
        protected Void doInBackground(final CardData... params) {
            mAsyncTaskDao.deleteItem(params[0]);
            return null;
        }
    }

    public void deleteById(Integer id) {
        new deleteByIdAsyncTask(mCardDao).execute(id);
    }

    private static class deleteByIdAsyncTask extends AsyncTask<Integer, Void, Void> {
        private CardDAO mAsyncTaskDao;

        deleteByIdAsyncTask(CardDAO cardDAO) {
            mAsyncTaskDao = cardDAO;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteItemByItemId(params[0]);
            return null;
        }
    }
}
