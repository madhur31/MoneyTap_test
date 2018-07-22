package com.example.madhur.moneytap.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.madhur.moneytap.R;
import com.example.madhur.moneytap.fragment.SearchFragment;

public class SearchActivity extends AppCompatActivity {

    private TextView mToolbarSubTitle;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDetailPane(new SearchFragment());
    }

    public void updateDetailPane(Fragment newFragment) {
        if(newFragment != null) {
//            Fragment lFrag = getSupportFragmentManager().findFragmentById(R.id.activity_detail_container);
//            if (lFrag != null && lFrag.equals(newFragment)) {
//                return;
//            } else {
                FragmentTransaction lFragTrans = getSupportFragmentManager().beginTransaction();
                lFragTrans.replace(R.id.activity_detail_container, newFragment);
                lFragTrans.addToBackStack(newFragment.getClass().toString());
                lFragTrans.commit();
//            }
        }
    }

    public void setToolbar(String title) {
        setTitle(title);
    }
}
