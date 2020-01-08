package com.example.mlkittask.MlKitClassesFromGitHub;

import android.graphics.Bitmap;

import com.google.firebase.ml.common.FirebaseMLException;

import java.nio.ByteBuffer;

public interface VisionImageProcessor {

    void process(ByteBuffer data, FrameMetadata frameMetadata)
            throws FirebaseMLException;

    void process(Bitmap bitmap);

    void stop();
}
