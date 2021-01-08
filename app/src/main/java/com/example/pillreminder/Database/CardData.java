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

    private String alarmType;
    private String medicationName;
    private String alarmText;
    private String alarmTime;
    private ArrayList<Integer> specificDaysOfWeekId;

    public CardData(CardData cardToCopy) {
        this.alarmID = cardToCopy.alarmID;
        this.alarmType = cardToCopy.alarmType;
        this.medicationName = cardToCopy.medicationName;
        this.alarmText = cardToCopy.alarmText;
        this.alarmTime = cardToCopy.alarmTime;

        if(cardToCopy.specificDaysOfWeekId != null){
            this.specificDaysOfWeekId = cardToCopy.specificDaysOfWeekId;
        }
    }

    public CardData(int alarmID, String alarmType, String medicationName, String alarmText, String alarmTime) {
        this.alarmID = alarmID;
        this.alarmType = alarmType;
        this.medicationName = medicationName;
        this.alarmText = alarmText;
        this.alarmTime = alarmTime;
    }

    public CardData(int alarmID, ArrayList<Integer> ids, String alarmType, String medicationName, String alarmText, String alarmTime) {
        this.alarmID = alarmID;
        this.specificDaysOfWeekId = ids;
        this.alarmType = alarmType;
        this.medicationName = medicationName;
        this.alarmText = alarmText;
        this.alarmTime = alarmTime;
    }

    protected CardData(Parcel in) {
        alarmID = in.readInt();
        alarmType = in.readString();
        medicationName = in.readString();
        alarmText = in.readString();
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
    public String getAlarmType() { return this.alarmType; }
    public String getMedicationName() { return this.medicationName; }
    public String getAlarmText() { return this.alarmText; }
    public String getAlarmTime() { return this.alarmTime; }

    public void setSpecificDaysOfWeekId(ArrayList<Integer> ids) { this.specificDaysOfWeekId = ids; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(alarmID);
        dest.writeString(alarmType);
        dest.writeString(medicationName);
        dest.writeString(alarmText);
        dest.writeString(alarmTime);
    }
}
