package com.example.temporal.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.temporal.R;
import com.example.temporal.models.TrackingTag;
import com.example.temporal.utils.InputUtils;
import com.example.temporal.viewmodels.TrackingEntryViewModel;
import com.example.temporal.viewmodels.TrackingTagViewModel;

import java.util.List;

import static com.example.temporal.fragments.NewTrackingTagFragmentDirections.*;

public class NewTrackingTagFragment extends Fragment {

    private static final String TAG = "TW-NewTrackingTagFrag";

    private TrackingEntryViewModel trackingEntryViewModel;
    private TrackingTagViewModel trackingTagViewModel;

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

        this.view = inflater.inflate(R.layout.new_tracking_tag_fragment, container, false);

        return view;

    }



    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        displayDefaultEntryValues();

        final EditText tagNameEdit = view.findViewById(R.id.tag_name_input);

        InputUtils.showKeyboardForView(view.getContext(), tagNameEdit);

        view.findViewById(R.id.create_new_tracking_tag_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final TrackingTag trackingTag = retrieveTagFromInput();

                trackingTagViewModel.insertTag(trackingTag);

                final NavDirections newTagToNewEntry =
                        actionNewTrackingTagFragmentToNewTrackingEntryFragment();

                Navigation.findNavController(view).navigate(newTagToNewEntry);

            }
        });

    }



    private void displayDefaultEntryValues(){


        final String defaultColHex = "#212121";

        ((TextView) view.findViewById(R.id.new_tag_color)).setText(defaultColHex);



    }



    private TrackingTag retrieveTagFromInput(){


        final String tagName = ((EditText) view.findViewById(R.id.tag_name_input)).getText().toString();

        final String colHex = ((TextView) view.findViewById(R.id.new_tag_color)).getText().toString();

        return new TrackingTag(tagName, colHex);


    }



}
