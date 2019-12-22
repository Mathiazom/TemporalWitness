package com.example.temporal.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.temporal.models.TrackingTag;
import com.example.temporal.repositories.TrackingTagRepository;

import java.util.List;

public class TrackingTagViewModel extends AndroidViewModel {

    private static final String TAG = "TW-TrackingTagVM";

    private final TrackingTagRepository trackingTagRepository;

    private final LiveData<List<TrackingTag>> trackingTags;


    public TrackingTagViewModel(@NonNull Application application) {
        super(application);

        trackingTagRepository = new TrackingTagRepository(application);

        trackingTags = trackingTagRepository.getTrackingTags();

    }

    public LiveData<List<TrackingTag>> getTrackingTags() {
        return trackingTags;
    }

    public void insertTag(final TrackingTag trackingTag) {
        trackingTagRepository.insertTag(trackingTag);
    }

    public void updateTag(final TrackingTag trackingTag) {
        trackingTagRepository.updateTag(trackingTag);
    }

    public void deleteTag(final TrackingTag trackingTag) {
        trackingTagRepository.deleteTag(trackingTag);
    }


}
