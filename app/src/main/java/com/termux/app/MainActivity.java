package com.termux.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.termux.app.browser.BrowserFragment;
import com.termux.app.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private BrowserFragment browserFragment;
    private SettingsFragment settingsFragment;
    private LinearLayout terminalContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.termux.R.layout.activity_termux_with_nav);

        browserFragment = new BrowserFragment();
        settingsFragment = new SettingsFragment();

        bottomNavigationView = findViewById(com.termux.R.id.bottom_navigation);
        terminalContainer = findViewById(com.termux.R.id.terminal_container);
        
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == com.termux.R.id.navigation_terminal) {
                    showTerminalActivity();
                    return true;
                } else if (itemId == com.termux.R.id.navigation_browser) {
                    showBrowserFragment();
                    return true;
                } else if (itemId == com.termux.R.id.navigation_settings) {
                    showSettingsFragment();
                    return true;
                }
                return false;
            }
        });

        showTerminalActivity();
    }

    private void showTerminalActivity() {
        Intent intent = new Intent(this, TermuxActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void showBrowserFragment() {
        if (browserFragment == null) {
            browserFragment = new BrowserFragment();
        }
        replaceFragment(browserFragment);
    }

    private void showSettingsFragment() {
        if (settingsFragment == null) {
            settingsFragment = new SettingsFragment();
        }
        replaceFragment(settingsFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(com.termux.R.id.fragment_container, fragment);
        transaction.commit();
    }
}
