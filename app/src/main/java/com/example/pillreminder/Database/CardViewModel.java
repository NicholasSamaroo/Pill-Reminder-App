package com.example.pillreminder.Database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CardViewModel extends AndroidViewModel {
    private CardRepository cardRepository;
    private LiveData<List<CardData>> mAllWords;

    public CardViewModel(Application application) {
        super(application);
        cardRepository = new CardRepository(application);
        mAllWords = cardRepository.getAllCards();
    }

    public LiveData<List<CardData>> getAllWords(){return mAllWords;}

    public void insert(CardData cardData){cardRepository.insert(cardData);}
    public void deleteItem(CardData cardData){cardRepository.deleteItem(cardData);}
    public void deleteItemById(Integer integer){cardRepository.deleteById(integer);}
    public List<CardData> getAllSavedCardValues() throws ExecutionException, InterruptedException {return cardRepository.getAllSavedCardValues();}
}
