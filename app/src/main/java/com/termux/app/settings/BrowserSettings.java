package com.termux.app.settings;

import android.content.Context;
import android.content.SharedPreferences;

public class BrowserSettings {

    public static final int ENGINE_CHROME_TABS = 0;
    public static final int ENGINE_WEBVIEW = 1;
    public static final int ENGINE_EDGE = 2;

    private static final String PREFS_NAME = "BrowserSettings";
    private static final String KEY_BROWSER_ENGINE = "browser_engine";
    private static final String KEY_JAVASCRIPT_ENABLED = "javascript_enabled";
    private static final String KEY_DOM_STORAGE_ENABLED = "dom_storage_enabled";
    private static final String KEY_CACHE_ENABLED = "cache_enabled";
    private static final String KEY_ZOOM_ENABLED = "zoom_enabled";

    private int browserEngine = ENGINE_CHROME_TABS;
    private boolean javaScriptEnabled = true;
    private boolean domStorageEnabled = true;
    private boolean cacheEnabled = true;
    private boolean zoomEnabled = true;

    public int getBrowserEngine() {
        return browserEngine;
    }

    public void setBrowserEngine(int browserEngine) {
        this.browserEngine = browserEngine;
    }

    public boolean isJavaScriptEnabled() {
        return javaScriptEnabled;
    }

    public void setJavaScriptEnabled(boolean javaScriptEnabled) {
        this.javaScriptEnabled = javaScriptEnabled;
    }

    public boolean isDomStorageEnabled() {
        return domStorageEnabled;
    }

    public void setDomStorageEnabled(boolean domStorageEnabled) {
        this.domStorageEnabled = domStorageEnabled;
    }

    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    public boolean isZoomEnabled() {
        return zoomEnabled;
    }

    public void setZoomEnabled(boolean zoomEnabled) {
        this.zoomEnabled = zoomEnabled;
    }

    public static BrowserSettings loadSettings(Context context) {
        BrowserSettings settings = new BrowserSettings();
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        settings.browserEngine = prefs.getInt(KEY_BROWSER_ENGINE, ENGINE_CHROME_TABS);
        settings.javaScriptEnabled = prefs.getBoolean(KEY_JAVASCRIPT_ENABLED, true);
        settings.domStorageEnabled = prefs.getBoolean(KEY_DOM_STORAGE_ENABLED, true);
        settings.cacheEnabled = prefs.getBoolean(KEY_CACHE_ENABLED, true);
        settings.zoomEnabled = prefs.getBoolean(KEY_ZOOM_ENABLED, true);

        return settings;
    }

    public void saveSettings(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(KEY_BROWSER_ENGINE, browserEngine);
        editor.putBoolean(KEY_JAVASCRIPT_ENABLED, javaScriptEnabled);
        editor.putBoolean(KEY_DOM_STORAGE_ENABLED, domStorageEnabled);
        editor.putBoolean(KEY_CACHE_ENABLED, cacheEnabled);
        editor.putBoolean(KEY_ZOOM_ENABLED, zoomEnabled);

        editor.apply();
    }
}
