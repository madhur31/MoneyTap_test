package com.example.madhur.moneytap.fragment;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.madhur.moneytap.BuildConfig;

public class ViewPageFragment extends Fragment {

    private static final String ARG_PAGEID = BuildConfig.APPLICATION_ID + ".pageid";

    private WebView mWebview;

    public static ViewPageFragment newInstance(String pageid) {
        Bundle lBundle = new Bundle();
        lBundle.putString(ARG_PAGEID, pageid);
        ViewPageFragment fragment = new ViewPageFragment();
        fragment.setArguments(lBundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mWebview = new WebView(getActivity());
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        mWebview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });

        String lPageID = "";

        if (getArguments() != null) {
            lPageID = getArguments().getString(ARG_PAGEID);
        }

        if (!lPageID.isEmpty())
            mWebview.loadUrl("https://en.m.wikipedia.org/?curid=" + lPageID);
        return mWebview;
    }
}
