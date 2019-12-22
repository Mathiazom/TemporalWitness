package com.example.temporal.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.temporal.models.TrackingTag;

import java.util.List;

@Dao
public interface TrackingTagDAO {


    @Insert
    void insert(TrackingTag trackingTag);

    @Update
    void update(TrackingTag trackingTag);

    @Delete
    void delete(TrackingTag trackingTag);


    @Transaction
    @Query("SELECT * FROM tracking_tag_table")
    LiveData<List<TrackingTag>> getTrackingTags();

}
