package com.example.temporal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.temporal.fragments.RecentEntriesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        final RecentEntriesFragment recentEntriesFragments = new RecentEntriesFragment();

        fragmentManager.beginTransaction()
                .add(R.id.main_fragment_frame,recentEntriesFragments)
                .commit();
    }

}
