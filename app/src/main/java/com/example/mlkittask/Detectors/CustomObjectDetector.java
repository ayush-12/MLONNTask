package com.example.mlkittask.Detectors;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mlkittask.MlKitClassesFromGitHub.FrameMetadata;
import com.example.mlkittask.MlKitClassesFromGitHub.VisionProcessorBase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.objects.FirebaseVisionObject;
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetector;
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetectorOptions;

import java.io.IOException;
import java.util.List;

public class CustomObjectDetector extends VisionProcessorBase<List<FirebaseVisionObject>> {

    private static final String TAG = "ObjectDetectorProcessor";

    private final FirebaseVisionObjectDetector detector;

    public CustomObjectDetector(Context context, FirebaseVisionObjectDetectorOptions options) {
        FirebaseApp.initializeApp(context);
        detector = FirebaseVision.getInstance().getOnDeviceObjectDetector(options);
    }

    @Override
    public void stop() {
        super.stop();
        try {
            detector.close();
        } catch (IOException e) {
            Log.e(TAG, "Exception thrown while trying to close object detector!", e);
        }
    }

    @Override
    protected Task<List<FirebaseVisionObject>> detectInImage(FirebaseVisionImage image) {
        return detector.processImage(image);
    }

    @Override
    protected void onSuccess(
            @Nullable Bitmap originalCameraImage,
            @NonNull List<FirebaseVisionObject> results,
            @NonNull FrameMetadata frameMetadata) {

        if (originalCameraImage != null) {
            Log.d(TAG, "onSuccess: object here ");
        }
        for (FirebaseVisionObject object : results) {
            Log.d(TAG, "onSuccess: Object here "+object.toString());
        }

    }

    @Override
    protected void onFailure(@NonNull Exception e) {
        Log.e(TAG, "Object detection failed!", e);
    }
}
