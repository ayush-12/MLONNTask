package com.example.mlkittask.contract;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.face.FaceDetector;

public interface CameraActivityContract {

    interface View{
        void initView();

        void showCaptureButton();
        void hideCaptureButton(String message);

    }

    interface Model{
        FaceDetector getFaceDetector(CameraActivityContract.Model model, CameraActivityContract.Presenter presenter);
    }

    interface Presenter{
        void onClick(android.view.View view);

        FaceDetector getFaceDetector(CameraActivityContract.Presenter presenter);

        void showCaptureButton();
        void hideCaptureButton(String message);


    }
}
