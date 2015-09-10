package com.example.hong.createbitmap;

import android.content.Context;
import android.content.res.Resources;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

/**
 * Created by hong on 2015/8/31.
 */
public class BitmapToByte {
    private Resources res;
    private Context context;
    private Bitmap redCannons;

    //Hello
    //Change

    public BitmapToByte(Context context) {
        this.context = context;
        this.res = context.getResources();
        redCannons = BitmapFactory.decodeResource(res, R.drawable.red_cannon);
    }
    public Bitmap getRedCannonBitMap() {
        return this.redCannons;
    }

    public byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 1, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
