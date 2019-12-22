package com.example.temporal.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.temporal.models.SparseTrackingEntry;
import com.example.temporal.models.TrackingEntry;

import java.util.List;

@Dao
public interface TrackingEntryDAO {

    @Insert
    long insert(SparseTrackingEntry sparseTrackingEntry);

    @Update
    void update(SparseTrackingEntry sparseTrackingEntry);

    @Delete
    void delete(SparseTrackingEntry sparseTrackingEntry);

    @Transaction
    @Query("SELECT * FROM tracking_entry_table ORDER BY start_time ASC")
    LiveData<List<TrackingEntry>> getTrackingEntries();


}
