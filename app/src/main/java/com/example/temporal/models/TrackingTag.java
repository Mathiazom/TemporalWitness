package com.example.temporal.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tracking_tag_table")
public class TrackingTag {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tag_id")
    private int id;

    private final String name;

    public TrackingTag(final String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
