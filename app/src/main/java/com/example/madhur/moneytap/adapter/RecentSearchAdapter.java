package com.example.madhur.moneytap.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.madhur.moneytap.DB.WikiSearch;
import com.example.madhur.moneytap.R;

import java.util.List;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<WikiSearch> searchList;
    OnItemClickListener itemClickListener;

    public RecentSearchAdapter(List<WikiSearch> searchList, OnItemClickListener itemClickListener) {
        this.searchList = searchList;
        this.itemClickListener = itemClickListener;
    }

    public void setSearchList(List<WikiSearch> searchList) {
        this.searchList = searchList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recents, parent, false);
        return new RecentListViewHolder(lView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final WikiSearch search = searchList.get(position);
        ((RecentListViewHolder)holder).searchTitle.setText(search.getQuery());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemSelected(search);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(searchList != null) {
            return searchList.size();
        }
        return 0;
    }

    private class RecentListViewHolder extends RecyclerView.ViewHolder {

        final TextView searchTitle;

        public RecentListViewHolder(View itemView) {
            super(itemView);
            searchTitle = itemView.findViewById(R.id.recent_search);
        }
    }

    public interface OnItemClickListener {
        void onItemSelected(WikiSearch search);
    }
}
