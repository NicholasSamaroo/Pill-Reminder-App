package com.example.pillreminder.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pillreminder.Model.CardData;
import com.example.pillreminder.Repository.CardRepository;

import java.util.List;

public class CardViewModel extends AndroidViewModel {
    private final CardRepository cardRepository;
    private final LiveData<List<CardData>> mAllWords;

    public CardViewModel(Application application) {
        super(application);
        cardRepository = new CardRepository(application);
        mAllWords = cardRepository.getAllDataBaseCards();
    }

    public LiveData<List<CardData>> getAllCards() {
        return mAllWords;
    }

    public void insert(CardData cardData) {
        cardRepository.insert(cardData);
    }
    public void deleteItem(CardData cardData) {
        cardRepository.deleteItem(cardData);
    }
    public void deleteItemById(Integer integer) {
        cardRepository.deleteById(integer);
    }
}
