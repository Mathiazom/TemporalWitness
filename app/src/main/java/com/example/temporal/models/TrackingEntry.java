package com.example.temporal.models;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.Date;
import java.util.List;

public class TrackingEntry {

    @Embedded
    private final SparseTrackingEntry entry;

    @Relation(
            parentColumn = "entry_id",
            entityColumn = "tag_id",
            associateBy = @Junction(TrackingEntryTagXRef.class)
    )
    private final List<TrackingTag> tags;

    public TrackingEntry(SparseTrackingEntry entry, List<TrackingTag> tags) {

        this.entry = entry;
        this.tags = tags;

    }

    public SparseTrackingEntry getSparse() {
        return entry;
    }

    public List<TrackingTag> getTags() {
        return tags;
    }


    // Revealing methods from contained SparseTrackingEntry

    public int getId() {
        return entry.getId();
    }

    public String getActivityName() {
        return entry.getActivityName();
    }

    public Date getStartTime() {
        return entry.getStartTime();
    }

    public Date getEndTime() {
        return entry.getEndTime();
    }

    public void setEndTime(Date endTime) {
        entry.setEndTime(endTime);
    }



}
