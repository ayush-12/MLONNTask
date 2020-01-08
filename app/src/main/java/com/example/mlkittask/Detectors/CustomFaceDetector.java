package com.example.mlkittask.Detectors;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.example.mlkittask.contract.CameraActivityContract;
import com.example.mlkittask.view.CameraActivity;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

public class CustomFaceDetector {

    public static String TAG="FaceDetector";

    private FaceDetector faceDetector;
    private CameraSource cameraSource;

    private Context context;
    private CameraActivityContract.Model mModel;
    private CameraActivityContract.Presenter mPresenter;

    public CustomFaceDetector(Context context, CameraActivityContract.Model model,
                              CameraActivityContract.Presenter presenter){
        this.context=context;
        this.mModel =model;
        this.mPresenter=presenter;

    }
    
    public FaceDetector setUpFaceDetector(){
        faceDetector = new FaceDetector.Builder(context)
                .setMode(com.google.android.gms.vision.face.FaceDetector.ACCURATE_MODE)
                .setLandmarkType(com.google.android.gms.vision.face.FaceDetector.ALL_LANDMARKS)
                .setClassificationType(com.google.android.gms.vision.face.FaceDetector.ALL_CLASSIFICATIONS).build();

        faceDetector.setProcessor(new Detector.Processor<Face>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Face> detections) {
                final SparseArray<Face> faceSparseArray = detections.getDetectedItems();

                if(faceSparseArray.size()>0){
                    Log.d(TAG, "receiveDetections: face detected");
                    mPresenter.showCaptureButton();
                }
                else {
                    Log.d(TAG, "receiveDetections: face not detected");
                    mPresenter.hideCaptureButton("No face detected");
                }
            }
        });

        return faceDetector;
    }
    

}
