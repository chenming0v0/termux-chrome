package com.termux.app.browser;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;
import com.termux.app.R;
import com.termux.app.settings.BrowserSettings;

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

        BrowserSettings settings = BrowserSettings.loadSettings(requireContext());

        switch (settings.getBrowserEngine()) {
            case BrowserSettings.ENGINE_CHROME_TABS:
                openWithChromeCustomTabs();
                break;
            case BrowserSettings.ENGINE_WEBVIEW:
                setupWebView(settings);
                break;
        }
    }

    private void openWithChromeCustomTabs() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setShowTitle(true);
        builder.setToolbarColor(getResources().getColor(android.R.color.black));
        builder.setEnableUrlBarHiding(true);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(requireContext(), Uri.parse(DEFAULT_URL));
    }

    private void setupWebView(BrowserSettings settings) {
        webView = new WebView(requireContext());
        ((ViewGroup) requireView()).addView(webView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(settings.isJavaScriptEnabled());
        webSettings.setDomStorageEnabled(settings.isDomStorageEnabled());
        webSettings.setDatabaseEnabled(settings.isDomStorageEnabled());
        
        if (settings.isCacheEnabled()) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setSupportZoom(settings.isZoomEnabled());
        webSettings.setBuiltInZoomControls(settings.isZoomEnabled());
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(DEFAULT_URL);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (webView != null) {
            webView.stopLoading();
            webView.loadUrl("about:blank");
            webView.clearCache(true);
            webView.destroy();
            webView = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }
}
