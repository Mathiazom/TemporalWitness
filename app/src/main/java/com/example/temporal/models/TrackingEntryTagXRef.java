package com.example.temporal.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(
        tableName = "tracking_entry_tag_xref_table",
        primaryKeys = {
                "entry_id",
                "tag_id"
        }
)
public class TrackingEntryTagXRef {

    @ColumnInfo(name = "entry_id", index = true)
    private final int entryId;

    @ColumnInfo(name = "tag_id", index = true)
    private final int tagId;

    public TrackingEntryTagXRef(int entryId, int tagId) {
        this.entryId = entryId;
        this.tagId = tagId;
    }

    public int getEntryId() {
        return entryId;
    }

    public int getTagId() {
        return tagId;
    }

}
