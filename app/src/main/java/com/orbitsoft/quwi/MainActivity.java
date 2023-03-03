package com.orbitsoft.quwi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToAuth() {
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host);

        NavInflater navInflater = navHostFragment.getNavController().getNavInflater();
        NavGraph graph = navInflater.inflate(R.navigation.navigation);
        graph.setStartDestination(R.id.authFragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(graph).build();
        navHostFragment.getNavController().setGraph(graph);
        NavigationUI.navigateUp(navHostFragment.getNavController(), appBarConfiguration);
    }
}