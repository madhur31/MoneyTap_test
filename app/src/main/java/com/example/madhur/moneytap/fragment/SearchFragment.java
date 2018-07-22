package com.example.madhur.moneytap.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.madhur.moneytap.DB.WikiSearch;
import com.example.madhur.moneytap.DB.WikiSearchDAO;
import com.example.madhur.moneytap.MyApplication;
import com.example.madhur.moneytap.R;
import com.example.madhur.moneytap.activity.SearchActivity;
import com.example.madhur.moneytap.adapter.RecentSearchAdapter;
import com.example.madhur.moneytap.datahandler.GetSearchResultDataHandler;
import com.example.madhur.moneytap.response.SearchResultResponse;
import com.example.madhur.moneytap.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements RecentSearchAdapter.OnItemClickListener {

    private View mView;
    private AutoCompleteTextView mSearchBox;
    private RecyclerView recyclerView;

    private RecentSearchAdapter adapter;

    private List<WikiSearch> searchList;
    private List<String> suggestions = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    TextView.OnEditorActionListener mActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                WikiDetailsFragment listFragment = WikiDetailsFragment.newInstance(v.getText().toString(), false);
                ((SearchActivity) getActivity()).updateDetailPane(listFragment);
            }
            return false;
        }
    };

    private void makeSearchRequest(final String searchQuery) {
        GetSearchResultDataHandler handler = new GetSearchResultDataHandler(searchQuery, 0) {
            @Override
            public void resultReceivedSearchResult(int resultCode, String errorCode, SearchResultResponse response) {
                if (resultCode == 200) {
                    suggestions = response.getSuggestions();
                    arrayAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.select_dialog_item, suggestions);
                    arrayAdapter.notifyDataSetChanged();
                    mSearchBox.setAdapter(arrayAdapter);
                }
            }
        };
        handler.SearchQuery(false, false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_wiki, container, false);
        initViews();
        getSearch();
        ((SearchActivity) getActivity()).setToolbar("MoneyTap");
        return mView;
    }

    private void getSearch() {
        WikiSearchDAO searchDAO = new WikiSearchDAO(MyApplication.getAppContext());
        searchList = searchDAO.getAll();
        if (adapter != null && searchList != null) {
            adapter.setSearchList(searchList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getSearch();
        if(searchList != null && searchList.size() >0) {
            mView.findViewById(R.id.recent_search).setVisibility(View.VISIBLE);
        }
    }

    private void initViews() {
        mSearchBox = mView.findViewById(R.id.search_box);
        mSearchBox.setOnEditorActionListener(mActionListener);
        recyclerView = mView.findViewById(R.id.recent_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecentSearchAdapter(searchList, this);
        recyclerView.setAdapter(adapter);
        arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, suggestions);
        mSearchBox.setAdapter(arrayAdapter);
        mSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 2) {
                    makeSearchRequest(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onItemSelected(WikiSearch search) {
        WikiDetailsFragment listFragment = WikiDetailsFragment.newInstance(search.getQuery(), true);
        ((SearchActivity) getActivity()).updateDetailPane(listFragment);
    }
}
