package com.example.bolirepeat;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Data {
    @PrimaryKey(autoGenerate = true)
    int id;


    @ColumnInfo
    String title;
    @ColumnInfo
    String note;



    public Data(int id, String title, String note) {
        this.id = id;
        this.title = title;
        this.note = note;
    }

    public Data(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public Data() {
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

}
