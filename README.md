# FitAndroid7

一行代码完成Android 7 FileProvider适配~

适配FileProvide需要声明provider，编写xml，以及在代码中做版本适配等...

可以抽取一个小库简化这些重复性操作，避免重复声明provider，编写xml，以及在代码中做版本适配...


## 使用

```
compile 'com.zhy.base:fileprovider:1.0.0'
```

通过FileProvider7这个类完成uri的获取即可，例如：

* FileProvider7.getUriForFile
* FileProvider7.setIntentDataAndType
* FileProvider7.setIntentData


### 示例一 拍照

```java
private static final int REQUEST_CODE_TAKE_PHOTO = 0x110;
private String mCurrentPhotoPath;

public void takePhotoNoCompress(View view) {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                .format(new Date()) + ".png";
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        mCurrentPhotoPath = file.getAbsolutePath();
	     // 仅需改变这一行
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
```

### 示例二 安装apk

```java
public void installApk(View view) {
    File file = new File(Environment.getExternalStorageDirectory(),
            "testandroid7-debug.apk");
    Intent intent = new Intent(Intent.ACTION_VIEW);
    // 仅需改变这一行
    FileProvider7.setIntentDataAndType(this,
            intent, "application/vnd.android.package-archive", file, true);
    startActivity(intent);
}
```


