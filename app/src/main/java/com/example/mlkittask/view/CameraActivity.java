package com.example.mlkittask.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mlkittask.R;
import com.example.mlkittask.contract.CameraActivityContract;
import com.example.mlkittask.model.CameraActivityModel;
import com.example.mlkittask.presenter.CameraActivityPresenter;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.face.FaceDetector;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity implements CameraActivityContract.View {

    public static String TAG="CameraActivity";

    private CameraActivityContract.Presenter mPresenter;
    private CameraActivityContract.Model mModel;

    private SurfaceView cameraView;
    private Button captureButton;
    private TextView messageTextView;

    private CameraSource cameraSource;

    private String message="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mPresenter = new CameraActivityPresenter(this,this);
        mModel= new CameraActivityModel(this,this,mPresenter);




        cameraSource = new CameraSource.Builder(this,mPresenter.getFaceDetector(mPresenter) )
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920, 1024)
                .build();

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

        cameraView =  findViewById(R.id.camera_view);
        captureButton = findViewById(R.id.capture_button);
        messageTextView=findViewById(R.id.messgae_text_view);
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

}
