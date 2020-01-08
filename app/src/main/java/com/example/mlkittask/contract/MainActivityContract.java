package com.example.mlkittask.contract;

import android.view.View;

public interface MainActivityContract {

    interface View{
        void initView();

    }

    interface Model{

    }

    interface Presenter{
        void onClick(android.view.View view);

    }
}
