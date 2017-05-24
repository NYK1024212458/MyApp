package com.kunstudy.myap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        ImageView im = (ImageView) findViewById(R.id.im);
        // 获取里面的 im
        Drawable drawable = im.getDrawable();
        //

        Log.d(DEBUG_TAG, "onCreate: "+drawable.toString());

       /* //  保存到本地
        String imagename = modelname.toLowerCase() + "_photo_" + imagenum;
        Log.i(DEBUG_TAG, "image : " + modelname.toLowerCase() + "_photo_" + imagenum);
        int resID = getResources().getIdentifier(imageName, "drawable", packageName);
        Log.i(DEBUG_TAG, "resID : " + resID);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resID);

        File SpicyDirectory = new File("/sdcard/Images/");
        SpicyDirectory.mkdirs();

        String filename = "/sdcard/Images/" + imagename + ".jpg";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.JPG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
            } catch (IOException e) {
                //
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                //
                e.printStackTrace();
            }
            out = null;
        }*/


    }

}