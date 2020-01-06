package com.example.temporal.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.temporal.R;
import com.example.temporal.models.SparseTrackingEntry;
import com.example.temporal.models.TrackingEntry;
import com.example.temporal.models.TrackingTag;
import com.example.temporal.testing.AssertionException;
import com.example.temporal.utils.DateUtils;
import com.example.temporal.utils.InputUtils;
import com.example.temporal.viewmodels.TrackingEntryViewModel;
import com.example.temporal.viewmodels.TrackingTagViewModel;
import com.example.temporal.views.SelectTagView;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewTrackingEntryFragment extends BottomSheetDialogFragment {


    private static final String TAG = "TW-NewTrackingEntryFrag";

    private View view;

    private TrackingEntryViewModel trackingEntryViewModel;
    private TrackingTagViewModel trackingTagViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trackingEntryViewModel = ViewModelProviders.of(requireActivity()).get(TrackingEntryViewModel.class);
        trackingTagViewModel = ViewModelProviders.of(requireActivity()).get(TrackingTagViewModel.class);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.new_tracking_entry_fragment, container, false);



        return view;

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        trackingTagViewModel.getTrackingTags().observe(requireActivity(), new Observer<List<TrackingTag>>() {
            @Override
            public void onChanged(List<TrackingTag> trackingTags) {

                if (trackingTags == null) {

                    Log.i(TAG, "No available tags");

                    trackingTags = new ArrayList<>();

                }

                // Display possible tags

                final LayoutInflater inflater = LayoutInflater.from(requireContext());

                final FlexboxLayout tagsContainer = view.findViewById(R.id.new_entry_tags_container);
                tagsContainer.removeAllViews();

                int[] selectedTagIds = null;

                if (getArguments() != null) {
                    selectedTagIds = NewTrackingEntryFragmentArgs.fromBundle(getArguments()).getSelectedTagIds();
                }

                if (selectedTagIds == null) {
                    selectedTagIds = new int[]{};
                }

                Log.i(TAG, "Selected tags: " + Arrays.toString(selectedTagIds));

                for (TrackingTag tag : trackingTags) {

                    final SelectTagView tagView = (SelectTagView) inflater.inflate(R.layout.select_tag_template, tagsContainer, false);

                    tagView.setTrackingTag(tag);
                    tagView.setIsSelected(false);

                    for (int id : selectedTagIds) {
                        if (id == tag.getId()) {
                            tagView.setIsSelected(true);
                        }
                    }

                    tagsContainer.addView(tagView);

                }

            }
        });


        displayDefaultEntryValues();

        final EditText activityNameEdit = view.findViewById(R.id.activity_name_input);

        InputUtils.showKeyboardForView(activityNameEdit.getContext(), activityNameEdit);

        view.findViewById(R.id.new_entry_bottom_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TrackingEntry newEntry = retrieveEntryFromInput();
                trackingEntryViewModel.insertEntry(newEntry);

                trackingEntryViewModel.setIsTracking(true);

                final NavDirections newToRecentEntries = NewTrackingEntryFragmentDirections
                        .actionNewTrackingEntryFragmentToRecentTrackingEntriesFragment();

                Navigation.findNavController(view).navigate(newToRecentEntries);

            }
        });


        view.findViewById(R.id.add_new_tracking_tag_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final NavDirections newEntryToNewTag = NewTrackingEntryFragmentDirections
                        .actionNewTrackingEntryFragmentToNewTrackingTagFragment();

                Navigation.findNavController(view).navigate(newEntryToNewTag);

            }
        });


        view.findViewById(R.id.new_entry_start_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final NavDirections newEntryToTimePicker = NewTrackingEntryFragmentDirections
                        .actionNewTrackingEntryFragmentToTimePickerFragment();

                Navigation.findNavController(view).navigate(newEntryToTimePicker);

            }
        });

        view.findViewById(R.id.new_entry_start_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final NavDirections newEntryToDatePicker = NewTrackingEntryFragmentDirections
                        .actionNewTrackingEntryFragmentToDatePickerFragment();

                Navigation.findNavController(view).navigate(newEntryToDatePicker);

            }
        });

    }


    private void displayDefaultEntryValues() {

        // Default start time

        final String currentTimeString = DateUtils.currentTimeString() + " (current time)";

        ((TextView) view.findViewById(R.id.new_entry_start_time))
                .setText(currentTimeString);

    }


    private TrackingEntry retrieveEntryFromInput() {

        final String activityName = ((EditText) view.findViewById(R.id.activity_name_input)).getText().toString();

        final FlexboxLayout tagsContainer = view.findViewById(R.id.new_entry_tags_container);

        final List<TrackingTag> selectedTags = new ArrayList<>();

        for (int i = 0; i < tagsContainer.getChildCount(); i++) {

            try {

                final SelectTagView tagView = (SelectTagView) tagsContainer.getChildAt(i);

                if (tagView.isSelected()) {

                    selectedTags.add(tagView.getTrackingTag());

                }

            } catch (ClassCastException e) {

                throw new AssertionException("Tags container contained non-tag view: " + e);

            }

        }

        Log.i(TAG, "retrieveEntryFromInput: selected tags: " + selectedTags);

        // TODO: Get actual selected start time
        final Date startTime = Calendar.getInstance().getTime();


        return new TrackingEntry(
                new SparseTrackingEntry(
                        activityName,
                        startTime,
                        null
                ),
                selectedTags
        );

    }


}
