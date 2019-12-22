package com.example.temporal.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temporal.R;
import com.example.temporal.adapters.RecentEntriesRecyclerAdapter;
import com.example.temporal.models.SparseTrackingEntry;
import com.example.temporal.models.TrackingActivity;
import com.example.temporal.models.TrackingEntry;
import com.example.temporal.models.TrackingTag;
import com.example.temporal.testing.AssertionException;
import com.example.temporal.viewmodels.TrackingEntryViewModel;
import com.example.temporal.viewmodels.TrackingTagViewModel;

import java.util.Calendar;
import java.util.List;

public class RecentEntriesFragment extends Fragment {


    private final static String TAG = "TW-RecentEntriesFrag";

    private TrackingEntryViewModel trackingEntriesViewModel;
    private TrackingTagViewModel trackingTagViewModel;

    private RecentEntriesRecyclerAdapter recentEntriesRecyclerAdapter;
    private RecyclerView recentEntriesRecyclerView;

    private View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        trackingEntriesViewModel = ViewModelProviders.of(requireActivity()).get(TrackingEntryViewModel.class);
        trackingTagViewModel = ViewModelProviders.of(requireActivity()).get(TrackingTagViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.recent_entries_fragment,container,false);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recentEntriesRecyclerView = initEntriesRecyclerView();

        trackingTagViewModel.getTrackingTags().observe(requireActivity(), new Observer<List<TrackingTag>>() {
            @Override
            public void onChanged(List<TrackingTag> trackingTags) {

                final StringBuilder tagsStringBuilder = new StringBuilder();

                for (TrackingTag trackingTag: trackingTags) {
                    tagsStringBuilder.append(trackingTag.getName());
                }

                Toast.makeText(view.getContext(), tagsStringBuilder.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        trackingEntriesViewModel.getTrackingEntries().observe(requireActivity(), new Observer<List<TrackingEntry>>() {
            @Override
            public void onChanged(List<TrackingEntry> trackingEntries) {

                recentEntriesRecyclerAdapter.submitList(trackingEntries);

                final RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(requireContext());
                smoothScroller.setTargetPosition(recentEntriesRecyclerAdapter.getItemCount());

                if (recentEntriesRecyclerView.getLayoutManager() == null){
                    throw new AssertionException("Recycler view manager was null");
                }

                recentEntriesRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);

                Log.i(TAG,"Tracking entries change observed: " + trackingEntries.toString());
            }
        });

        trackingEntriesViewModel.getIsTracking().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isTracking) {

                view.findViewById(R.id.new_entry_container).setVisibility(isTracking ? View.GONE : View.VISIBLE);

                view.findViewById(R.id.end_tracking_container).setVisibility(isTracking ? View.VISIBLE : View.GONE);

            }
        });

        view.findViewById(R.id.new_entry_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: Implement actual entry creation process

                final LiveData<List<TrackingTag>> tags = trackingTagViewModel.getTrackingTags();

                final List<TrackingTag> entryTags = tags.getValue();

                final List<TrackingEntry> entries = trackingEntriesViewModel.getTrackingEntries().getValue();

                if (entryTags == null || entries == null || entryTags.size() <= entries.size()){

                    trackingTagViewModel.insertTag(new TrackingTag(String.valueOf(Math.random())));

                    return;

                }

                Log.i(TAG, "onClick: "+ entryTags);

                trackingEntriesViewModel.insertEntry(new TrackingEntry(
                        new SparseTrackingEntry(
                                "Writing",
                                Calendar.getInstance().getTime(),
                                null
                        ),
                        entryTags.subList(entries.size(),entries.size()+1)
                ));

                trackingEntriesViewModel.setIsTracking(true);

            }
        });

        view.findViewById(R.id.end_tracking_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackingEntriesViewModel.stopCurrentTracking();
            }
        });

    }


    private RecyclerView initEntriesRecyclerView(){

        final RecyclerView recentEntriesRecycler = view.findViewById(R.id.recent_entries_recycler);

        recentEntriesRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recentEntriesRecycler.setHasFixedSize(true);

        recentEntriesRecyclerAdapter = new RecentEntriesRecyclerAdapter();
        recentEntriesRecycler.setAdapter(recentEntriesRecyclerAdapter);

        return recentEntriesRecycler;

    }


}
