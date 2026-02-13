package com.termux.app.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.termux.app.R;

public class SettingsFragment extends Fragment {

    private RadioGroup browserEngineRadioGroup;
    private CheckBox checkboxEnableJavaScript;
    private CheckBox checkboxEnableDomStorage;
    private CheckBox checkboxEnableCache;
    private CheckBox checkboxEnableZoom;
    private MaterialButton buttonSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        loadSettings();
        setupListeners();
        updateWebViewSettingsVisibility();
    }

    private void initViews(View view) {
        browserEngineRadioGroup = view.findViewById(R.id.browser_engine_radio_group);
        checkboxEnableJavaScript = view.findViewById(R.id.checkbox_enable_javascript);
        checkboxEnableDomStorage = view.findViewById(R.id.checkbox_enable_dom_storage);
        checkboxEnableCache = view.findViewById(R.id.checkbox_enable_cache);
        checkboxEnableZoom = view.findViewById(R.id.checkbox_enable_zoom);
        buttonSave = view.findViewById(R.id.button_save);
    }

    private void loadSettings() {
        BrowserSettings settings = BrowserSettings.loadSettings(requireContext());

        int engineValue = settings.getBrowserEngine();
        switch (engineValue) {
            case 0:
                browserEngineRadioGroup.check(R.id.radio_chrome_tabs);
                break;
            case 1:
                browserEngineRadioGroup.check(R.id.radio_webview);
                break;
        }

        checkboxEnableJavaScript.setChecked(settings.isJavaScriptEnabled());
        checkboxEnableDomStorage.setChecked(settings.isDomStorageEnabled());
        checkboxEnableCache.setChecked(settings.isCacheEnabled());
        checkboxEnableZoom.setChecked(settings.isZoomEnabled());
    }

    private void setupListeners() {
        browserEngineRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                updateWebViewSettingsVisibility();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
    }

    private void updateWebViewSettingsVisibility() {
        int selectedId = browserEngineRadioGroup.getCheckedRadioButtonId();
        boolean isWebView = (selectedId == R.id.radio_webview);

        checkboxEnableJavaScript.setEnabled(isWebView);
        checkboxEnableDomStorage.setEnabled(isWebView);
        checkboxEnableCache.setEnabled(isWebView);
        checkboxEnableZoom.setEnabled(isWebView);
    }

    private void saveSettings() {
        BrowserSettings settings = new BrowserSettings();

        int selectedId = browserEngineRadioGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.radio_chrome_tabs) {
            settings.setBrowserEngine(BrowserSettings.ENGINE_CHROME_TABS);
        } else if (selectedId == R.id.radio_webview) {
            settings.setBrowserEngine(BrowserSettings.ENGINE_WEBVIEW);
        }

        settings.setJavaScriptEnabled(checkboxEnableJavaScript.isChecked());
        settings.setDomStorageEnabled(checkboxEnableDomStorage.isChecked());
        settings.setCacheEnabled(checkboxEnableCache.isChecked());
        settings.setZoomEnabled(checkboxEnableZoom.isChecked());

        settings.saveSettings(requireContext());

        Toast.makeText(requireContext(), "设置已保存", Toast.LENGTH_SHORT).show();
    }
}
