package com.example.temporal.utils;

import androidx.annotation.Nullable;

import com.example.temporal.models.TrackingEntry;
import com.example.temporal.models.TrackingTag;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiffUtility {


    public static boolean trackingEntriesHaveSameProperties(@Nullable TrackingEntry entry1, @Nullable TrackingEntry entry2){

        // At least one entry is null
        if ((entry1 == null || entry2 == null)){

            // True if both entries are null, false if exactly one is null
            return entry1 == null && entry2 == null;

        }

        return entry1.getActivityName().equals(entry2.getActivityName())
                && entry1.getStartTime().getTime() == entry2.getStartTime().getTime()
                && (
                        (entry1.getEndTime() == null && entry2.getEndTime() == null)
                        || (entry1.getEndTime().getTime() == entry2.getStartTime().getTime())
                   )
                && DiffUtility.tagListsHaveEqualContent(entry1.getTags(), entry2.getTags());

    }

    private static boolean tagListsHaveEqualContent(@Nullable List<TrackingTag> tags1, @Nullable List<TrackingTag> tags2){


        // At least one list is null
        if ((tags1 == null || tags2 == null)){

            // True if both lists are null, false if exactly one is null
            return tags1 == null && tags2 == null;

        }

        if (tags1.size() != tags2.size()) {

            return false;

        }

        final Comparator<TrackingTag> comparator = new Comparator<TrackingTag>() {
            @Override
            public int compare(TrackingTag o1, TrackingTag o2) {
                return o1.getId() - o2.getId();
            }
        };

        // Remove order differences
        Collections.sort(tags1, comparator);
        Collections.sort(tags2, comparator);

        for (int i = 0; i < tags1.size(); i++) {

            if (!(tags1.get(i).getName().equals(tags2.get(i).getName()))){

                // At lest one element does not have the same content in the other list
                return false;

            }

        }

        // All elements have the same content
        return true;

    }


}
