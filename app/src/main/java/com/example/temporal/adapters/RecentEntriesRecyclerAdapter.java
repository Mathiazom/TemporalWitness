package com.example.temporal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temporal.R;
import com.example.temporal.models.TrackingEntry;
import com.example.temporal.models.TrackingTag;
import com.example.temporal.utils.DateUtils;
import com.example.temporal.utils.DiffUtility;
import com.example.temporal.views.EntryTagView;

import java.text.DateFormat;

public class RecentEntriesRecyclerAdapter extends ListAdapter<TrackingEntry, RecentEntriesRecyclerAdapter.TrackingEntryHolder> {


    private static final String TAG = "TW-RecentEntriesAdapter";


    public RecentEntriesRecyclerAdapter(){
        super(DIFF_CALLBACK);
    }


    private static final DiffUtil.ItemCallback<TrackingEntry> DIFF_CALLBACK = new DiffUtil.ItemCallback<TrackingEntry>() {

        @Override
        public boolean areItemsTheSame(@NonNull TrackingEntry oldItem, @NonNull TrackingEntry newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TrackingEntry oldItem, @NonNull TrackingEntry newItem) {
            return DiffUtility.trackingEntriesHaveSameProperties(oldItem, newItem);
        }

    };



    @NonNull
    @Override
    public TrackingEntryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrackingEntryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_entry_adapter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrackingEntryHolder holder, int position) {

        final TrackingEntry trackingEntry = getItem(position);

        holder.entryTitle.setText(trackingEntry.getActivityName());

        final DateFormat format = DateFormat.getTimeInstance(DateFormat.SHORT);


        String timestamps = format.format(trackingEntry.getStartTime());

        if (trackingEntry.getEndTime() != null) {

            timestamps += " - " + format.format(trackingEntry.getEndTime());

            holder.trackingMarker.setVisibility(View.GONE);

            holder.entryDuration.setText(DateUtils.formatTimeInterval(trackingEntry.getStartTime(), trackingEntry.getEndTime()));

            holder.entryDuration.setVisibility(View.VISIBLE);

        } else {

            holder.trackingMarker.setVisibility(View.VISIBLE);

            holder.entryDuration.setVisibility(View.GONE);

        }

        holder.entryTimestamps.setText(timestamps);


        holder.entryTagsContainer.removeAllViews();

        for (TrackingTag tag : getItem(holder.getAdapterPosition()).getTags()){

            final EntryTagView tagView = (EntryTagView) LayoutInflater.from(holder.itemView.getContext())
                    .inflate(
                            R.layout.entry_tag_template,
                            holder.entryTagsContainer,
                            false
                    );
            tagView.setTrackingTag(tag);
            holder.entryTagsContainer.addView(tagView);

        }

    }


    class TrackingEntryHolder extends RecyclerView.ViewHolder {

        private final TextView entryTitle;
        private final TextView entryTimestamps;
        private final TextView entryDuration;
        private final TextView trackingMarker;
        private final LinearLayout entryTagsContainer;

        TrackingEntryHolder(@NonNull View itemView) {

            super(itemView);

            entryTitle = itemView.findViewById(R.id.entry_title);
            entryTimestamps = itemView.findViewById(R.id.entry_timestamps);
            entryDuration = itemView.findViewById(R.id.entry_duration);

            trackingMarker = itemView.findViewById(R.id.entry_tracking_marker);

            entryTagsContainer = itemView.findViewById(R.id.entry_tags_container);

        }
    }

}
