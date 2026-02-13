package com.termux.app;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.termux.app.browser.BrowserFragment;
import com.termux.app.terminal.TerminalFragment;
import com.termux.app.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TerminalFragment terminalFragment;
    private BrowserFragment browserFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termux_with_nav);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_terminal) {
                    showTerminalFragment();
                    return true;
                } else if (itemId == R.id.navigation_browser) {
                    showBrowserFragment();
                    return true;
                }
                return false;
            }
        });

        terminalFragment = new TerminalFragment();
        browserFragment = new BrowserFragment();

        showTerminalFragment();
    }

    private void showTerminalFragment() {
        if (terminalFragment == null) {
            terminalFragment = new TerminalFragment();
        }
        replaceFragment(terminalFragment);
    }

    private void showBrowserFragment() {
        if (browserFragment == null) {
            browserFragment = new BrowserFragment();
        }
        replaceFragment(browserFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
