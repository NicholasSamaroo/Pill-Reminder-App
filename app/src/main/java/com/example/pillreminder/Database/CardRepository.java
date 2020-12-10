package com.example.pillreminder.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.pillreminder.Database.CardDAO;
import com.example.pillreminder.Database.CardData;
import com.example.pillreminder.Database.CardRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CardRepository {
    private CardDAO mCardDao;
    private LiveData<List<CardData>> mAllCards;

    CardRepository(Application application) {
        CardRoomDatabase db = CardRoomDatabase.getDataBase(application);
        mCardDao = db.cardDAO();
        mAllCards = mCardDao.getAllCardValues();
    }

    LiveData<List<CardData>> getAllCards() {
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

    public List<CardData> getAllSavedCardValues() throws ExecutionException, InterruptedException {
        return new getAllSavedCardValuesAsyncTask(mCardDao).execute().get();
    }

    private static class getAllSavedCardValuesAsyncTask extends AsyncTask<Void,Void,List<CardData> > {
        private CardDAO mAsyncTaskDao;

        getAllSavedCardValuesAsyncTask(CardDAO cardDAO) {
            mAsyncTaskDao = cardDAO;
        }
        @Override
        protected List<CardData> doInBackground(Void... cards) {
            return mAsyncTaskDao.getSavedCardValues();
        }
    }
}
