package com.example.mlkittask.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.mlkittask.contract.CameraActivityContract;
import com.example.mlkittask.model.CameraActivityModel;
import com.google.android.gms.vision.face.FaceDetector;

public class CameraActivityPresenter implements CameraActivityContract.Presenter {

    private static String TAG= "CameraPresenter";
    private Context context;
    private CameraActivityContract.View mView;
    private CameraActivityContract.Model mModel;

    public CameraActivityPresenter(Context context, CameraActivityContract.View view){
        this.context = context;
        this.mView = view;
        initPresenter();
    }

    private void initPresenter(){
        mModel= new CameraActivityModel(context);
        mView.initView();
    }
    
    
    @Override
    public void onClick(View view) {
        
    }

    @Override
    public FaceDetector getFaceDetector(CameraActivityContract.Presenter presenter) {
        return mModel.getFaceDetector(mModel,presenter);
    }

    @Override
    public void showCaptureButton() {
        Log.d(TAG, "showCaptureButton: show capture button");
        mView.showCaptureButton();
    }

    @Override
    public void hideCaptureButton(String message) {
        mView.hideCaptureButton(message);
    }



}
