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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temporal.R;
import com.example.temporal.adapters.RecentEntriesRecyclerAdapter;
import com.example.temporal.models.TrackingEntry;
import com.example.temporal.models.TrackingTag;
import com.example.temporal.testing.AssertionException;
import com.example.temporal.viewmodels.TrackingEntryViewModel;
import com.example.temporal.viewmodels.TrackingTagViewModel;

import java.util.List;

public class RecentTrackingEntriesFragment extends Fragment {


    private final static String TAG = "TW-RecentEntriesFrag";

    private TrackingEntryViewModel trackingEntryViewModel;
    private TrackingTagViewModel trackingTagViewModel;

    private RecentEntriesRecyclerAdapter recentEntriesRecyclerAdapter;
    private RecyclerView recentEntriesRecyclerView;

    private View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        trackingEntryViewModel = ViewModelProviders.of(requireActivity()).get(TrackingEntryViewModel.class);
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

        trackingEntryViewModel.getTrackingEntries().observe(requireActivity(), new Observer<List<TrackingEntry>>() {
            @Override
            public void onChanged(List<TrackingEntry> trackingEntries) {

                recentEntriesRecyclerAdapter.submitList(trackingEntries);

                if(getContext() != null){

                    final RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(requireContext());
                    smoothScroller.setTargetPosition(recentEntriesRecyclerAdapter.getItemCount());

                    if (recentEntriesRecyclerView.getLayoutManager() == null){
                        throw new AssertionException("Recycler view manager was null");
                    }

                    recentEntriesRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);

                }


                Log.i(TAG,"Tracking entries change observed: " + trackingEntries.toString());
            }
        });

        trackingEntryViewModel.getIsTracking().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isTracking) {

                view.findViewById(R.id.new_entry_button_container).setVisibility(isTracking ? View.GONE : View.VISIBLE);

                view.findViewById(R.id.end_tracking_container).setVisibility(isTracking ? View.VISIBLE : View.GONE);

            }
        });

        view.findViewById(R.id.new_entry_button_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final NavDirections recentToNewEntry = RecentTrackingEntriesFragmentDirections
                        .actionRecentTrackingEntriesFragmentToNewTrackingEntryFragment();

                Navigation.findNavController(view).navigate(recentToNewEntry);

            }
        });

        view.findViewById(R.id.end_tracking_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackingEntryViewModel.stopCurrentTracking();
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
