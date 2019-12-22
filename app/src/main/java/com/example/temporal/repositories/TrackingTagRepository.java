package com.example.temporal.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.temporal.dao.TrackingTagDAO;
import com.example.temporal.database.TrackingDatabase;
import com.example.temporal.models.TrackingTag;

import java.util.List;

public class TrackingTagRepository {

    private final TrackingTagDAO trackingTagDAO;


    public TrackingTagRepository(Application application) {

        final TrackingDatabase database = TrackingDatabase.getInstance(application);

        this.trackingTagDAO = database.trackingTagDAO();

    }


    public LiveData<List<TrackingTag>> getTrackingTags(){
        return trackingTagDAO.getTrackingTags();
    }


    public void insertTag(TrackingTag trackingTag){
        new InsertTagAsyncTask(trackingTagDAO).execute(trackingTag);
    }

    public void updateTag(TrackingTag trackingTag){
        new UpdateTagAsyncTask(trackingTagDAO).execute(trackingTag);
    }

    public void deleteTag(TrackingTag trackingTag){
        new DeleteTagAsyncTask(trackingTagDAO).execute(trackingTag);
    }


    private static class InsertTagAsyncTask extends AsyncTask<TrackingTag, Void, Void>{

        private final TrackingTagDAO trackingTagDAO;

        private InsertTagAsyncTask(TrackingTagDAO trackingTagDAO) {
            this.trackingTagDAO = trackingTagDAO;
        }

        @Override
        protected Void doInBackground(TrackingTag... trackingTags) {

            final TrackingTag trackingTag = trackingTags[0];

            trackingTagDAO.insert(trackingTag);

            return null;
        }

    }

    private static class UpdateTagAsyncTask extends AsyncTask<TrackingTag, Void, Void>{

        private final TrackingTagDAO trackingTagDAO;

        private UpdateTagAsyncTask(TrackingTagDAO trackingTagDAO) {
            this.trackingTagDAO = trackingTagDAO;
        }

        @Override
        protected Void doInBackground(TrackingTag... trackingTags) {

            final TrackingTag trackingTag = trackingTags[0];

            trackingTagDAO.update(trackingTag);

            return null;
        }

    }

    private static class DeleteTagAsyncTask extends AsyncTask<TrackingTag, Void, Void>{

        private final TrackingTagDAO trackingTagDAO;

        private DeleteTagAsyncTask(TrackingTagDAO trackingTagDAO) {
            this.trackingTagDAO = trackingTagDAO;
        }

        @Override
        protected Void doInBackground(TrackingTag... trackingTags) {

            final TrackingTag trackingTag = trackingTags[0];

            trackingTagDAO.delete(trackingTag);

            return null;
        }

    }







}
