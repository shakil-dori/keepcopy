package com.example.bolirepeat;


import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Data.class},version = 1)
public abstract class Database extends RoomDatabase {
public abstract DataDao dataDao();
}
