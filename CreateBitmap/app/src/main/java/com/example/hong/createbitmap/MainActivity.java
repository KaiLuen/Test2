package com.example.hong.createbitmap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivity extends Activity {

    Button createBitmap;
    BitmapToByte bitmapToByte;
    ImageView imageViewForTestBefore;
    ImageView imageViewForTestAfter;
    Intent useCamera;
    Bitmap bitmapForTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(this, "j5paBuM7D278Xwuy7UTNfClSdgW0Q60xCeRYvpYy", "bq7EZ45qEm0XOqMBP0OjRyMgD4cQ5Vy2TFbP5AIB");

        initView();
        initBitmap();
        setListener();
    }

    private void initBitmap() {
        bitmapToByte = new BitmapToByte(MainActivity.this);
    }

    private void initView() {
        createBitmap = (Button)findViewById(R.id.button_main_create);
        imageViewForTestBefore = (ImageView)findViewById(R.id.imageView_main_before);
        imageViewForTestAfter = (ImageView)findViewById(R.id.imageView_main_after);
    }
    private void setListener() {
        createBitmap.setOnClickListener(toCreate);
    }

    Button.OnClickListener toCreate = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            useCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(useCamera, 0);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent useCamera) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 0) {
                Uri uri = useCamera.getData();
                Log.i("Path:", uri.getPath());
                ContentResolver contentResolver = this.getContentResolver();
                try {
                    bitmapForTest = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                    imageViewForTestBefore.setImageBitmap(bitmapForTest);
                } catch (FileNotFoundException e) {
                    Log.i("Exception", e.getMessage(),e);
                }
                ParseObject bitmapByte = new ParseObject("BitmapByte");
                bitmapByte.put("bitmapByte", bitmapToByte.getBitmapByte(bitmapForTest));
                try {
                    bitmapByte.save();
                } catch (ParseException e) {
                    Log.e("Exception", e.getMessage(), e);
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
