package com.termux.app.browser;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import com.termux.app.R;

public class BrowserFragment extends Fragment {

    private static final String DEFAULT_URL = "https://www.baidu.com";
    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(android.R.layout.activity_list_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int engineValue = BrowserEngineSelectorActivity.getSelectedBrowserEngine(requireContext());
        BrowserEngine engine = BrowserEngine.fromValue(engineValue);

        switch (engine) {
            case CHROME_CUSTOM_TABS:
                openWithChromeCustomTabs();
                break;
            case WEBVIEW:
                setupWebView();
                break;
            case CHROME_INTENT:
                openWithChromeIntent();
                break;
        }
    }

    private void openWithChromeCustomTabs() {
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setShowTitle(true);
            builder.setToolbarColor(getResources().getColor(android.R.color.black));

            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(requireContext(), Uri.parse(DEFAULT_URL));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(requireContext(), "未找到 Chrome 浏览器", Toast.LENGTH_SHORT).show();
            openWithChromeIntent();
        }
    }

    private void openWithChromeIntent() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(DEFAULT_URL));
            intent.setPackage("com.android.chrome");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(requireContext(), "未找到 Chrome 浏览器，使用系统默认浏览器", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(DEFAULT_URL));
            startActivity(intent);
        }
    }

    private void setupWebView() {
        webView = new WebView(requireContext());
        ((ViewGroup) requireView()).addView(webView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(DEFAULT_URL);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
    }
}
