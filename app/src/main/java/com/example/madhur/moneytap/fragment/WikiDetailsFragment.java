package com.example.madhur.moneytap.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.madhur.moneytap.BuildConfig;
import com.example.madhur.moneytap.R;
import com.example.madhur.moneytap.activity.SearchActivity;
import com.example.madhur.moneytap.adapter.SearchListAdapter;
import com.example.madhur.moneytap.datahandler.GetSearchResultDataHandler;
import com.example.madhur.moneytap.response.SearchPages;
import com.example.madhur.moneytap.response.SearchResultResponse;

import java.util.ArrayList;
import java.util.List;

public class WikiDetailsFragment extends android.support.v4.app.Fragment implements SearchListAdapter.OnItemClickListener {

    private static final String ARG_QUERY = BuildConfig.APPLICATION_ID + ".query";
    private static final String ARG_FETCH_DB = BuildConfig.APPLICATION_ID + ".fetchDB";

    private View mView;
    private RecyclerView recyclerView;
    private ProgressBar mProgressBar;
    private SearchListAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private int mOffset;

    private String mQuery;
    private boolean mFetchDB;
    private List<SearchPages> searchResults = new ArrayList<>();
    private boolean isLoading;

    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {

        boolean scrolling;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                scrolling = false;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            Log.d("Scrolling", String.valueOf(scrolling) + "  loading" + String.valueOf(isLoading));
            if (!scrolling && !isLoading) {
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && mOffset != -1) {
                    Log.d("Scroll", "Make network call");
                    makeSearchRequest(mQuery, mOffset);
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    public static WikiDetailsFragment newInstance(String query, boolean fetchFromDB) {
        Bundle lBundle = new Bundle();
        lBundle.putString(ARG_QUERY, query);
        lBundle.putBoolean(ARG_FETCH_DB, fetchFromDB);
        WikiDetailsFragment fragment = new WikiDetailsFragment();
        fragment.setArguments(lBundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_details, container, false);
        initViews();
        return mView;
    }


    private void initViews() {
        mProgressBar = mView.findViewById(R.id.progress_bar);
        recyclerView = mView.findViewById(R.id.search_list);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new SearchListAdapter(searchResults, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        showHideRecyclerView();
        recyclerView.addOnScrollListener(listener);
        ((SearchActivity)getActivity()).setToolbar(mQuery.toUpperCase());
    }

    private void showHideRecyclerView() {
        if (recyclerView == null) {
            return;
        }
        if (searchResults.size() == 0) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            mQuery = getArguments().getString(ARG_QUERY);
            mFetchDB = getArguments().getBoolean(ARG_FETCH_DB);
        }
        makeSearchRequest(mQuery, 0);
    }

    private void makeSearchRequest(String query, int offset) {
        Log.d("makeSearchRequest", "Requesting");
        isLoading = true;
        if (offset == -1) {
            return;
        }
        GetSearchResultDataHandler handler = new GetSearchResultDataHandler(query, offset) {
            @Override
            public void resultReceivedSearchResult(int resultCode, String errorCode, SearchResultResponse response) {
                isLoading = false;
                Log.d("makeSearchRequest", String.valueOf(isLoading) + "= false");
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.GONE);
                }
                if (resultCode == 200) {
                    searchResults.addAll(response.getQuery());
                    if(response.getContinueResponse() != null) {
                        mOffset = response.getContinueResponse().getGpsoffset();
                    }
                    Log.d("Result", response.toString());
                    refreshData();
                } else {
                    Toast.makeText(getActivity(), errorCode, Toast.LENGTH_SHORT);
                }
            }
        };
        handler.SearchQuery(mFetchDB, true);
        Log.d("makeSearchRequest", String.valueOf(isLoading) + "= true");
    }

    private void refreshData() {
        showHideRecyclerView();
        if (adapter != null) {
            adapter.setSearchResults(searchResults);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemSelected(SearchPages searchPages) {
        ((SearchActivity)getActivity()).updateDetailPane(ViewPageFragment.newInstance(String.valueOf(searchPages.getPageid())));
    }
}
