package com.example.mlkittask.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mlkittask.Detectors.CustomObjectDetector;
import com.example.mlkittask.MlKitClassesFromGitHub.CameraSourceML;
import com.example.mlkittask.MlKitClassesFromGitHub.CameraSourcePreview;
import com.example.mlkittask.R;
import com.example.mlkittask.contract.CameraActivityContract;
import com.example.mlkittask.model.CameraActivityModel;
import com.example.mlkittask.presenter.CameraActivityPresenter;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.objects.FirebaseVisionObjectDetectorOptions;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity implements CameraActivityContract.View {

    public static String TAG = "CameraActivity";

    private CameraActivityContract.Presenter mPresenter;
    private CameraActivityContract.Model mModel;

    private SurfaceView cameraView;

    private Button captureButton;   // NO CODE ATTACH TO CAPTURE BUTTON

    private TextView messageTextView;

    private CameraSource cameraSource;


    private String message = "";


    private CameraSourceML cameraSourceML = null;
    private CameraSourcePreview cameraSourcePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mPresenter = new CameraActivityPresenter(this, this);
        mModel = new CameraActivityModel(this, this, mPresenter);

        FirebaseApp.initializeApp(this);


        String which = getIntent().getStringExtra("which");

        if (which.equalsIgnoreCase("Face")) {
            cameraSource = new CameraSource.Builder(this, mPresenter.getFaceDetector(mPresenter))
                    .setFacing(CameraSource.CAMERA_FACING_FRONT)
                    .setRequestedFps(24)
                    .setAutoFocusEnabled(true)
                    .setRequestedPreviewSize(1920, 1024)
                    .build();

        } else {
            cameraView.setVisibility(View.INVISIBLE);
            cameraSourceML = new CameraSourceML(this);
            cameraSourceML.setFacing(CameraSource.CAMERA_FACING_BACK);

            FirebaseVisionObjectDetectorOptions objectDetectorOptions =
                    new FirebaseVisionObjectDetectorOptions.Builder()
                            .setDetectorMode(FirebaseVisionObjectDetectorOptions.STREAM_MODE)
                            .enableClassification()
                            .build();

            cameraSourceML.setMachineLearningFrameProcessor(
                    new CustomObjectDetector(this, objectDetectorOptions));

            cameraSourcePreview.start(cameraSourceML);
        }


        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(cameraView.getHolder());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }


        });

    }

    @Override
    public void initView() {

        cameraView = findViewById(R.id.camera_view);
        captureButton = findViewById(R.id.capture_button);
        messageTextView = findViewById(R.id.messgae_text_view);

        cameraSourcePreview = findViewById(R.id.firePreview);

    }

    @Override
    public void showCaptureButton() {
        captureButton.setVisibility(View.VISIBLE);
        messageTextView.setText(message);
        messageTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideCaptureButton(String message) {
        captureButton.setVisibility(View.INVISIBLE);
        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (cameraSource != null) {
            cameraSource.release();
        } else {
            cameraSourceML.release();
        }
    }
}
