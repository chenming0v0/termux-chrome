package com.termux.app.browser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.termux.app.R;

public class BrowserEngineSelectorActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "BrowserPrefs";
    public static final String KEY_BROWSER_ENGINE = "browser_engine";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showBrowserEngineDialog();
    }

    private void showBrowserEngineDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择浏览器内核");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_browser_engine_selector, null);
        builder.setView(dialogView);

        RadioGroup radioGroup = dialogView.findViewById(R.id.browser_engine_radio_group);

        int selectedEngine = getSelectedBrowserEngine(this);
        for (BrowserEngine engine : BrowserEngine.values()) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(engine.getDisplayName() + "\n" + engine.getDescription());
            radioButton.setId(engine.getValue());
            radioButton.setChecked(engine.getValue() == selectedEngine);
            radioGroup.addView(radioButton);
        }

        builder.setPositiveButton("确定", (dialog, which) -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            BrowserEngine selected = BrowserEngine.fromValue(selectedId);
            saveBrowserEngine(this, selected.getValue());
            finish();
        });

        builder.setCancelable(false);
        builder.show();
    }

    public static int getSelectedBrowserEngine(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_BROWSER_ENGINE, BrowserEngine.CHROME_CUSTOM_TABS.getValue());
    }

    public static void saveBrowserEngine(Context context, int engineValue) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_BROWSER_ENGINE, engineValue).apply();
    }
}
