package com.example.temporal.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.temporal.dao.TrackingEntryDAO;
import com.example.temporal.dao.TrackingEntryTagXRefDAO;
import com.example.temporal.dao.TrackingTagDAO;
import com.example.temporal.models.SparseTrackingEntry;
import com.example.temporal.models.TrackingActivity;
import com.example.temporal.models.TrackingEntryTagXRef;
import com.example.temporal.models.TrackingTag;


@Database(
        entities = {
                SparseTrackingEntry.class,
                TrackingTag.class,
                TrackingEntryTagXRef.class,
                TrackingActivity.class
        },
        version = 1,
        exportSchema = false
)
public abstract class TrackingDatabase extends RoomDatabase {


    private static TrackingDatabase instance;


    public abstract TrackingEntryDAO trackingEntryDAO();

    public abstract TrackingTagDAO trackingTagDAO();

    public abstract TrackingEntryTagXRefDAO trackingEntryTagXRefDAO();


    public static synchronized TrackingDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TrackingDatabase.class, "tracking_entries_database")
                    .fallbackToDestructiveMigration()
                    .build();

        }

        return instance;

    }

}
