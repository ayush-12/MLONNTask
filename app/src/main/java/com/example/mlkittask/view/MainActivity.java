package com.example.mlkittask.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mlkittask.R;
import com.example.mlkittask.contract.MainActivityContract;
import com.example.mlkittask.model.MainActivityModel;
import com.example.mlkittask.presenter.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    public static String TAG="MainActivity";

    private MainActivityContract.Presenter mPresenter;
    private MainActivityContract.Model mModel;

    private Button faceDetectorButton;
    private Button cardDetectotButton;

    public static final int PERMISSION_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mModel= new MainActivityModel(this,this);
        mPresenter = new MainActivityPresenter(this,this);




        checkForCameraPermission();
    }

    @Override
    public void initView() {

        faceDetectorButton=findViewById(R.id.face_detector_button);
        cardDetectotButton=findViewById(R.id.card_detector_button);

        faceDetectorButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: button clicked");
                mPresenter.onClick(v);
            }
        });

        cardDetectotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onClick(v);
            }
        });
    }

    private void checkForCameraPermission(){
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        else{
            Log.d(TAG, "permission granted already");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION_REQUEST){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "onRequestPermissionsResult: permission granted");
            }
            else{
                Log.d(TAG, "onRequestPermissionsResult: permission denied");
            }
        }

    }

}
