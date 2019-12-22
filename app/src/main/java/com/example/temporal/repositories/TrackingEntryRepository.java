package com.example.temporal.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.temporal.dao.TrackingEntryDAO;
import com.example.temporal.dao.TrackingEntryTagXRefDAO;
import com.example.temporal.database.TrackingDatabase;
import com.example.temporal.models.TrackingEntryTagXRef;
import com.example.temporal.models.TrackingEntry;
import com.example.temporal.models.TrackingTag;

import java.util.List;

public class TrackingEntryRepository {

    private static final String TAG = "TW-TrackingEntryRepo";

    private final TrackingEntryDAO trackingEntryDAO;

    private final TrackingEntryTagXRefDAO trackingEntryTagXRefDAO;


    public TrackingEntryRepository(Application application) {

        final TrackingDatabase database = TrackingDatabase.getInstance(application);

        trackingEntryDAO = database.trackingEntryDAO();

        trackingEntryTagXRefDAO = database.trackingEntryTagXRefDAO();

    }

    public void insertEntry(TrackingEntry trackingEntry) {
        new InsertTrackingEntryAsyncTask(trackingEntryDAO, trackingEntryTagXRefDAO).execute(trackingEntry);
    }

    public void updateEntry(TrackingEntry trackingEntry) {
        new UpdateTrackingEntryAsyncTask(trackingEntryDAO, trackingEntryTagXRefDAO).execute(trackingEntry);
    }

    public void deleteEntry(TrackingEntry trackingEntry) {
        new DeleteTrackingEntryAsyncTask(trackingEntryDAO, trackingEntryTagXRefDAO).execute(trackingEntry);
    }

    public LiveData<List<TrackingEntry>> getAllTrackingEntries() {
        return trackingEntryDAO.getTrackingEntries();
    }


    private static class InsertTrackingEntryAsyncTask extends AsyncTask<TrackingEntry, Void, Void> {

        private final TrackingEntryDAO trackingEntryDAO;
        private final TrackingEntryTagXRefDAO trackingEntryTagXRefDAO;

        private InsertTrackingEntryAsyncTask(TrackingEntryDAO trackingEntryDAO, TrackingEntryTagXRefDAO trackingEntryTagXRefDAO) {
            this.trackingEntryDAO = trackingEntryDAO;
            this.trackingEntryTagXRefDAO = trackingEntryTagXRefDAO;
        }


        @Override
        protected Void doInBackground(TrackingEntry... trackingEntries) {

            final TrackingEntry entry = trackingEntries[0];

            // Persist newly created tracking entry
            final int entryId = (int) trackingEntryDAO.insert(entry.getSparse());

            for (TrackingTag tag : entry.getTags()) {

                // Persist newly established entry/tag relation
                trackingEntryTagXRefDAO.insert(new TrackingEntryTagXRef(entryId, tag.getId()));

            }

            return null;
        }

    }

    private static class UpdateTrackingEntryAsyncTask extends AsyncTask<TrackingEntry, Void, Void> {

        private final TrackingEntryDAO trackingEntryDAO;
        private final TrackingEntryTagXRefDAO trackingEntryTagXRefDAO;

        private UpdateTrackingEntryAsyncTask(TrackingEntryDAO trackingEntryDAO, TrackingEntryTagXRefDAO trackingEntryTagXRefDAO) {
            this.trackingEntryDAO = trackingEntryDAO;
            this.trackingEntryTagXRefDAO = trackingEntryTagXRefDAO;
        }


        @Override
        protected Void doInBackground(TrackingEntry... trackingEntries) {

            final TrackingEntry entry = trackingEntries[0];

            trackingEntryDAO.update(entry.getSparse());

            updateEntryTagRelations(entry);

            return null;
        }

        private void updateEntryTagRelations(final TrackingEntry entry) {


            final List<Integer> tagIds = trackingEntryTagXRefDAO.getTagIdsFromEntry(entry.getId());


            for (TrackingTag tag : entry.getTags()) {

                final Integer tagId = tag.getId();

                if (tagIds.contains(tagId)) {

                    tagIds.remove(tagId);

                    continue;

                }

                // Persist newly established entry/tag relation
                trackingEntryTagXRefDAO.update(new TrackingEntryTagXRef(entry.getId(), tag.getId()));

            }

            // Any remaining tags in tagIds should not be persisted
            for (Integer tagId : tagIds) {

                // Stop persisting deleted entry/tag relation
                trackingEntryTagXRefDAO.delete(new TrackingEntryTagXRef(entry.getId(), tagId));

            }


        }

    }

    private static class DeleteTrackingEntryAsyncTask extends AsyncTask<TrackingEntry, Void, Void> {

        private final TrackingEntryDAO trackingEntryDAO;
        private final TrackingEntryTagXRefDAO trackingEntryTagXRefDAO;

        private DeleteTrackingEntryAsyncTask(TrackingEntryDAO trackingEntryDAO, TrackingEntryTagXRefDAO trackingEntryTagXRefDAO) {
            this.trackingEntryDAO = trackingEntryDAO;
            this.trackingEntryTagXRefDAO = trackingEntryTagXRefDAO;
        }


        @Override
        protected Void doInBackground(TrackingEntry... trackingEntries) {

            final TrackingEntry entry = trackingEntries[0];

            trackingEntryDAO.delete(entry.getSparse());

            // Stop persisting any tag relations with deleted entry
            for (TrackingTag tag : entry.getTags()) {
                trackingEntryTagXRefDAO.delete(new TrackingEntryTagXRef(entry.getId(), tag.getId()));
            }

            return null;
        }

    }


}
