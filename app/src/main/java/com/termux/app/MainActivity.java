package com.termux.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.termux.app.browser.BrowserEngine;
import com.termux.app.browser.BrowserEngineSelectorActivity;
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

        checkFirstLaunch();

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

    private void checkFirstLaunch() {
        boolean isFirstLaunch = getSharedPreferences("Prefs", MODE_PRIVATE)
                .getBoolean("is_first_launch", true);

        if (isFirstLaunch) {
            showBrowserEngineSelector();
            getSharedPreferences("Prefs", MODE_PRIVATE)
                    .edit()
                    .putBoolean("is_first_launch", false)
                    .apply();
        }
    }

    private void showBrowserEngineSelector() {
        Intent intent = new Intent(this, BrowserEngineSelectorActivity.class);
        startActivity(intent);
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

    public void showSettingsMenu(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置");
        
        String[] items = {"切换浏览器内核", "关于"};
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    Intent intent = new Intent(this, BrowserEngineSelectorActivity.class);
                    startActivity(intent);
                    break;
                case 1:
                    showAboutDialog();
                    break;
            }
        });
        
        builder.show();
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("关于");
        builder.setMessage("Termux Chrome\n\n集成了多种浏览器内核：\n" +
                "• Chrome Custom Tabs\n" +
                "• WebView\n" +
                "• Chrome Intent\n\n" +
                "根据设备情况选择最适合的浏览器内核");
        builder.setPositiveButton("确定", null);
        builder.show();
    }
}
