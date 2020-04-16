package com.pt.aiti.mobilereport.Teknisi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Utility.BitmapHelper;

public class ZoomImageActivity extends AppCompatActivity {
    private Context context = this;
    ImageView zoomImage;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);

        zoomImage = findViewById(R.id.zoomImage);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Bitmap image = (Bitmap) extras.get("image");
            if (image != null) {
                zoomImage.setImageBitmap(image);
            }
        }
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            zoomImage.setScaleX(mScaleFactor);
            zoomImage.setScaleY(mScaleFactor);
            return true;
        }
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }
}
