package com.example.pillreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pillreminder.Model.CardData;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {

    public class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView medicationName;
        public TextView spinnerValue;
        public TextView reminderTime;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

            medicationName = itemView.findViewById(R.id.medicationName);
            spinnerValue = itemView.findViewById(R.id.spinnerValue);
            reminderTime = itemView.findViewById(R.id.reminderTime);
        }
    }
    private List<CardData> mCardViewValues;

    public CardViewAdapter(List<CardData> cards) {
        mCardViewValues = cards;
    }

    @NonNull
    @Override
    public CardViewAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View cardView = inflater.inflate(R.layout.card_layout,parent,false);
        return new CardViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardData cardData = mCardViewValues.get(position);

        TextView medicationTextView = holder.medicationName;
        medicationTextView.setText(cardData.getMedicationName());

        TextView reminderTypeTextView = holder.spinnerValue;
        reminderTypeTextView.setText(cardData.getAlarmText());

        TextView reminderTimeTextView = holder.reminderTime;
        reminderTimeTextView.setText(cardData.getAlarmTime());
    }

    public void setCards(List<CardData> cards){
        mCardViewValues = cards;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCardViewValues != null) {
            return mCardViewValues.size();
        } else return 0;
    }

    public CardData getCardValue(int position) {
        return mCardViewValues.get(position);
    }
}
