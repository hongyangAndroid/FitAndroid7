package com.zhy.fitandroid7;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.zhy.base.fileprovider.FileProvider7;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private ImageView mIvPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvPhoto = (ImageView) findViewById(R.id.id_iv);

        // 6.0原生系统自行打开SDCard权限!
    }

    public void installApk(View view) {
        // 需要自己修改安装包路径
        File file = new File(Environment.getExternalStorageDirectory(),
                "testandroid7-debug.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        FileProvider7.setIntentDataAndType(this,
                intent, "application/vnd.android.package-archive", file, true);
        startActivity(intent);
    }


    private static final int REQUEST_CODE_TAKE_PHOTO = 0x110;
    private String mCurrentPhotoPath;

    public void takePhotoNoCompress(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                    .format(new Date()) + ".png";
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            mCurrentPhotoPath = file.getAbsolutePath();

            Uri fileUri = FileProvider7.getUriForFile(this, file);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_TAKE_PHOTO) {
            mIvPhoto.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
        }
        // else tip?

    }
}
