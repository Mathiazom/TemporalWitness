package com.example.temporal.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tracking_activity_table")
public class TrackingActivity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "activity_id")
    private int id;

    private String name;

    public TrackingActivity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
