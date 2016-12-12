package edu.ucsd.billjyc.minilinkedin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import edu.ucsd.billjyc.minilinkedin.model.BasicInfo;
import edu.ucsd.billjyc.minilinkedin.util.ImageUtils;
import edu.ucsd.billjyc.minilinkedin.util.PermissionUtils;

public class BasicInfoEditActivity extends EditBaseActivity<BasicInfo> {
    public static final String KEY_BASIC_INFO = "basic_info";
    private static final int REQ_CODE_PICK_IMAGE = 100;

    @Override
    protected void saveAndExit(@NonNull BasicInfo data) {
        if(data == null) {
            data = new BasicInfo();
        }

        data.name = ((EditText) findViewById(R.id.basic_info_edit_name)).getText().toString();
        data.email = ((EditText) findViewById(R.id.basic_info_edit_email)).getText().toString();
        data.imageUri = (Uri) findViewById(R.id.basic_info_edit_image).getTag();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_BASIC_INFO, data);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            if(imageUri != null) {
                showImage(imageUri);
            }
        }
    }

    @Override
    protected void setUpForCreate() {

    }

    @Override
    protected void setUpForEdit(@NonNull BasicInfo data) {
        ((EditText)findViewById(R.id.basic_info_edit_name)).setText(data.name);
        ((EditText)findViewById(R.id.basic_info_edit_email)).setText(data.email);

        if(data.imageUri != null) {
            showImage(data.imageUri);
        }

        findViewById(R.id.basic_info_edit_image).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("click button", "pick image");
                if(!PermissionUtils.checkPermission(BasicInfoEditActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    PermissionUtils.requestReadExternalStoragePermission(BasicInfoEditActivity.this);
                } else {
                    pickImage();
                }
            }
        });
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select picture"),
                REQ_CODE_PICK_IMAGE);
    }

    private void showImage(Uri imageUri) {
        ImageView imageView = (ImageView) findViewById(R.id.basic_info_edit_image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setTag(imageUri);
        ImageUtils.loadImage(this, imageUri, imageView);
    }

    @Override
    protected BasicInfo initializeData() {
        return getIntent().getParcelableExtra(KEY_BASIC_INFO);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basic_info_edit;
    }
}
