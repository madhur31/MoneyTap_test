package com.example.madhur.moneytap.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.madhur.moneytap.MyApplication;
import com.example.madhur.moneytap.R;
import com.example.madhur.moneytap.response.SearchPages;

import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<ViewHolder> {


    private List<SearchPages> searchResults;
    private OnItemClickListener listener;

    public SearchListAdapter(List<SearchPages> searchResults, OnItemClickListener listener) {
        this.searchResults = searchResults;
        this.listener = listener;
    }

    public void setSearchResults(List<SearchPages> searchResults) {
        this.searchResults = searchResults;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list, parent, false);
        return new SearchListViewHolder(lView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SearchPages searchPage = searchResults.get(position);
        ((SearchListViewHolder)holder).searchImage.setImageUrl(searchPage.getThumbnail(), MyApplication.getInstance().getImageLoader());
        ((SearchListViewHolder)holder).searchTitle.setText(searchPage.getPageTitle());
        ((SearchListViewHolder)holder).searchDesc.setText(searchPage.getTerms());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSelected(searchPage);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (searchResults != null) {
            return searchResults.size();
        }
        return 0;
    }

    private class SearchListViewHolder extends ViewHolder {

        final NetworkImageView searchImage;
        final TextView searchTitle;
        final TextView searchDesc;

        public SearchListViewHolder(View itemView) {
            super(itemView);
            searchImage = itemView.findViewById(R.id.list_item_image);
            searchTitle = itemView.findViewById(R.id.list_item_title);
            searchDesc = itemView.findViewById(R.id.list_item_desc);
        }
    }

    public interface OnItemClickListener {
        void onItemSelected(SearchPages searchPages);
    }
}
