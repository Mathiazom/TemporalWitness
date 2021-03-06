package com.example.temporal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.example.temporal.fragments.RecentTrackingEntriesFragment;
import com.example.temporal.fragments.RecentTrackingEntriesFragmentDirections;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp();
    }

}
