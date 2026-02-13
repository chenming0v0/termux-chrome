package com.termux.app.browser;

public enum BrowserEngine {
    CHROME_CUSTOM_TABS("Chrome Custom Tabs", "使用设备上的 Chrome 浏览器（推荐）", 0),
    WEBVIEW("WebView", "Android 内置 WebView（兼容性好）", 1),
    CHROME_INTENT("Chrome Intent", "跳转到 Chrome 浏览器应用", 2);

    private final String displayName;
    private final String description;
    private final int value;

    BrowserEngine(String displayName, String description, int value) {
        this.displayName = displayName;
        this.description = description;
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public static BrowserEngine fromValue(int value) {
        for (BrowserEngine engine : values()) {
            if (engine.value == value) {
                return engine;
            }
        }
        return CHROME_CUSTOM_TABS;
    }
}
