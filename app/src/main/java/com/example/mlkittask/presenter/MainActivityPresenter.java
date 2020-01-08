package com.example.mlkittask.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.mlkittask.R;
import com.example.mlkittask.contract.MainActivityContract;
import com.example.mlkittask.model.MainActivityModel;
import com.example.mlkittask.view.CameraActivity;
import com.example.mlkittask.view.MainActivity;
import com.google.android.gms.vision.CameraSource;

import static com.example.mlkittask.view.MainActivity.TAG;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private Context context;
    private MainActivityContract.View mView;
    private MainActivityContract.Model mModel;

    public MainActivityPresenter(Context context, MainActivityContract.View view){
        this.context = context;
        this.mView = view;

        initPresenter();
    }

    private void initPresenter(){
        mModel= new MainActivityModel(context);
        mView.initView();
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: clicked");
        switch (view.getId()){
            case R.id.face_detector_button:{
                Log.d(TAG, "onClick: face detector pressed");
                startActivity();
                break;
            }
            case R.id.card_detector_button:{
                break;
            }
        }
    }

    private void startActivity(){
        Intent intent = new Intent(context, CameraActivity.class);
        context.startActivity(intent);
    }
}
