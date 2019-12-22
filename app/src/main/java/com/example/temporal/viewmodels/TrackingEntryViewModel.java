package com.example.temporal.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.temporal.models.TrackingEntry;
import com.example.temporal.repositories.TrackingEntryRepository;
import com.example.temporal.testing.AssertionException;

import java.util.Calendar;
import java.util.List;

public class TrackingEntryViewModel extends AndroidViewModel {

    private static final String TAG = "TW-TrackingEntryVM";

    private final TrackingEntryRepository trackingEntryRepository;

    private final LiveData<List<TrackingEntry>> trackingEntries;

    private final MutableLiveData<Boolean> isTracking;

    public TrackingEntryViewModel(@NonNull Application application) {
        super(application);

        trackingEntryRepository = new TrackingEntryRepository(application);
        trackingEntries = trackingEntryRepository.getAllTrackingEntries();

        isTracking = new MutableLiveData<>();
        final List<TrackingEntry> entries = trackingEntries.getValue();
        isTracking.setValue(entries != null && entries.get(entries.size()-1).getEndTime() != null);

    }

    public void insertEntry(TrackingEntry trackingEntry){
        trackingEntryRepository.insertEntry(trackingEntry);
    }

    public void updateEntry(TrackingEntry trackingEntry){
        trackingEntryRepository.updateEntry(trackingEntry);
    }

    public void deleteEntry(TrackingEntry trackingEntry){
        trackingEntryRepository.deleteEntry(trackingEntry);
    }

    public LiveData<List<TrackingEntry>> getTrackingEntries(){
        return trackingEntries;
    }

    public void setIsTracking(boolean tracking){

        isTracking.setValue(tracking);

    }

    public LiveData<Boolean> getIsTracking(){
        return isTracking;
    }

    public void stopCurrentTracking(){

        final List<TrackingEntry> entries = trackingEntries.getValue();

        if (entries == null){
            throw new AssertionException("Tried to stop tracking when tracking entries did not exist");
        }

        final TrackingEntry currentEntry = entries.get(entries.size()-1);
        currentEntry.setEndTime(Calendar.getInstance().getTime());

        updateEntry(currentEntry);

        setIsTracking(false);

    }

}
