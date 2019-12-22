package com.example.temporal.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.temporal.models.TrackingEntryTagXRef;

import java.util.List;

@Dao
public interface TrackingEntryTagXRefDAO {

    @Insert
    void insert(TrackingEntryTagXRef xRef);

    @Update
    void update(TrackingEntryTagXRef xRef);

    @Delete
    void delete(TrackingEntryTagXRef xRef);

    @Query("SELECT tag_id FROM tracking_entry_tag_xref_table WHERE tracking_entry_tag_xref_table.entry_id = :entryId")
    List<Integer> getTagIdsFromEntry(int entryId);

}
