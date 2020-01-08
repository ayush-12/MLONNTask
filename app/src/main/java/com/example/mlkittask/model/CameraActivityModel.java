package com.example.mlkittask.model;

import android.content.Context;

import com.example.mlkittask.Detectors.CustomFaceDetector;
import com.example.mlkittask.contract.CameraActivityContract;
import com.example.mlkittask.view.CameraActivity;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.face.FaceDetector;

public class CameraActivityModel implements CameraActivityContract.Model {

    private Context context;
    private CameraActivityContract.View mView;
    private CameraActivityContract.Presenter mPresenter;

    public CameraActivityModel(Context context, CameraActivityContract.View view, CameraActivityContract.Presenter presenter){
        this.context = context;
        this.mView = view;
        this.mPresenter=presenter;

    }

    public CameraActivityModel(Context context){
        this.context = context;
    }


    @Override
    public FaceDetector getFaceDetector(CameraActivityContract.Model model,CameraActivityContract.Presenter presenter) {
        CustomFaceDetector customFaceDetector = new CustomFaceDetector(context,model,presenter);
        return customFaceDetector.setUpFaceDetector();
    }

}
