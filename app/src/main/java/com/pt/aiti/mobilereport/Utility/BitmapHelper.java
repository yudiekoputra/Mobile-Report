package com.pt.aiti.mobilereport.Utility;

import android.graphics.Bitmap;

public class BitmapHelper {
    private Bitmap bitmap = null;
    private Bitmap bitmap2 = null;
    private Bitmap bitmap3 = null;
    private Bitmap bitmap4 = null;
    private Bitmap bitmap5 = null;
    private Bitmap bitmap6 = null;
    private Bitmap bitmapNull = null;
    private static final BitmapHelper instance = new BitmapHelper();

    public BitmapHelper() {
        this.bitmap = bitmap;
        this.bitmap2 = bitmap2;
        this.bitmap3 = bitmap3;
        this.bitmap4 = bitmap4;
        this.bitmap5 = bitmap5;
        this.bitmap6 = bitmap6;
        this.bitmapNull = bitmapNull;
    }

        public static BitmapHelper getInstance() {
            return instance;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }
        public Bitmap getBitmapNull(){
            return bitmapNull;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public Bitmap getBitmap2() {
            return bitmap2;
        }

        public void setBitmap2(Bitmap bitmap2) {
            this.bitmap2 = bitmap2;
        }

        public Bitmap getBitmap3() {
            return bitmap3;
        }

        public void setBitmap3(Bitmap bitmap3) {
            this.bitmap3 = bitmap3;
        }

        public Bitmap getBitmap4() {
            return bitmap4;
        }

        public void setBitmap4(Bitmap bitmap4) {
            this.bitmap4 = bitmap4;
        }
        public Bitmap getBitmap5() {
            return bitmap5;
        }

        public void setBitmap5(Bitmap bitmap5) {
            this.bitmap5 = bitmap5;
        }
        public Bitmap getBitmap6() {
            return bitmap6;
        }

        public void setBitmap6(Bitmap bitmap6) {
            this.bitmap6 = bitmap6;
        }
    }
