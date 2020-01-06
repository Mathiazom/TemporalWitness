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

    @ColumnInfo(name = "col_hex")
    private final String colHex;

    public TrackingTag(final String name, final String colHex) {
        this.name = name;
        this.colHex = colHex;
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

    public String getColHex() {
        return colHex;
    }
}
