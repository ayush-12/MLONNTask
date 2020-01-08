package com.example.mlkittask.model;

import android.content.Context;

import com.example.mlkittask.contract.MainActivityContract;

public class MainActivityModel implements MainActivityContract.Model {
    private Context context;
    private MainActivityContract.View mView;

    public MainActivityModel(Context context, MainActivityContract.View view){
        this.context = context;
        this.mView = view;
    }

    public MainActivityModel(Context context){
        this.context = context;
    }

}
