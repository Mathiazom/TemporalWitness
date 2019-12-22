package com.example.temporal.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.temporal.utils.DateConverter;

import java.util.Date;

@Entity(tableName = "tracking_entry_table")
@TypeConverters({DateConverter.class})
public class SparseTrackingEntry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entry_id")
    private int id;

    @ColumnInfo(name = "activity_name")
    private final String activityName;

    @ColumnInfo(name = "start_time")
    private final Date startTime;

    @ColumnInfo(name = "end_time")
    private Date endTime;


    public SparseTrackingEntry(final String activityName, final Date startTime, final Date endTime) {
        this.activityName = activityName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getActivityName() {
        return activityName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

}
