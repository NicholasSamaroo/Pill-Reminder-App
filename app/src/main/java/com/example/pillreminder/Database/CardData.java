package com.example.pillreminder.Database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "card_table")
public class CardData implements Parcelable {

    @PrimaryKey
    private int alarmID;

    private String medicationName;
    private String alarmType;
    private String alarmTime;
    private ArrayList<Integer> specificDaysOfWeekId;

    public CardData(CardData cardToCopy) {
        this.alarmID = cardToCopy.alarmID;
        this.medicationName = cardToCopy.medicationName;
        this.alarmType = cardToCopy.alarmType;
        this.alarmTime = cardToCopy.alarmTime;

        if(cardToCopy.specificDaysOfWeekId != null){
            this.specificDaysOfWeekId = cardToCopy.specificDaysOfWeekId;
        }
    }

    public CardData(int alarmID, String medicationName, String alarmType, String alarmTime) {
        this.alarmID = alarmID;
        this.medicationName = medicationName;
        this.alarmType = alarmType;
        this.alarmTime = alarmTime;
    }

    public CardData(ArrayList<Integer> ids, String medicationName, String alarmType, String alarmTime) {
        this.specificDaysOfWeekId = ids;
        this.medicationName = medicationName;
        this.alarmType = alarmType;
        this.alarmTime = alarmTime;
    }

    protected CardData(Parcel in) {
        alarmID = in.readInt();
        medicationName = in.readString();
        alarmType = in.readString();
        alarmTime = in.readString();
    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };

    public int getAlarmID() { return this.alarmID; }
    public ArrayList<Integer> getSpecificDaysOfWeekId() { return this.specificDaysOfWeekId; }
    public String getMedicationName() { return this.medicationName; }
    public String getAlarmType() { return this.alarmType; }
    public String getAlarmTime() { return this.alarmTime; }

    public void setSpecificDaysOfWeekId(ArrayList<Integer> ids) { this.specificDaysOfWeekId = ids; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(alarmID);
        dest.writeString(medicationName);
        dest.writeString(alarmType);
        dest.writeString(alarmTime);
    }
}
