package com.assignment.moneyexpensemanager.Activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.moneyexpensemanager.BuildConfig;
import com.assignment.moneyexpensemanager.R;
import com.assignment.moneyexpensemanager.Util.GlobalDataService;
import com.assignment.moneyexpensemanager.Util.Params;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class CreateNewRequestActivity extends AppCompatActivity {

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    private static final String IMAGE_DIRECTORY = "/CustomImage";


    View rootLayout;
    Button applyBtn;

    private int revealX;
    private int revealY;
    ImageView profileImage;
    EditText name, email;
    private String TAG = "Xxxxxxx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_request);
        profileImage = findViewById(R.id.profile_image);
        name = findViewById(R.id.customer_name_et);
        email = findViewById(R.id.customer_email_et);
        applyBtn = findViewById(R.id.apply_btn);

        applyBtn.setOnClickListener(applyBtnListener);

        final Intent intent = getIntent();

        rootLayout = findViewById(R.id.root_layout);

        if (savedInstanceState != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            reveal(rootLayout, intent, savedInstanceState);
        } else {

            rootLayout.setVisibility(View.VISIBLE);
        }
        showImage();
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDialog();
            }
        });


    }

    View.OnClickListener applyBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                if(verify()) {
                    showPopup(CreateNewRequestActivity.this, name.getText().toString().trim(), email.getText().toString().trim(), CameraActivity.bitmap);
                }else{
                    GlobalDataService.showToasty(CreateNewRequestActivity.this,"Invalid field",Params.TOASTY_ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private boolean verify() {
        if(name.getText().toString().isEmpty()){
            return false;
        }else if (email.getText().toString().isEmpty()){
            return false;
        }else if(CameraActivity.bitmap==null){
            return false;
        }else{
            return true;
        }
    }

    public void reveal(final View rootLayout, Intent intent, Bundle savedInstanceState) {
        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }
    }


    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(1000);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }


    protected void unRevealActivity() {
        rootLayout.setVisibility(View.INVISIBLE);
        startActivity(new Intent(CreateNewRequestActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();

    }

    @Override
    public void onBackPressed() {
        unRevealActivity();
    }


    private void startDialog() {
        if (ContextCompat.checkSelfPermission(CreateNewRequestActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(CreateNewRequestActivity.this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                ) {
            try {
//                        startcameraIntent();
                startCamera();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            askWriteExternalStoragePermission();
        }
    }

    private void startCamera() {
        Intent intent = new Intent(CreateNewRequestActivity.this, CameraActivity.class);
        startActivity(intent);
    }


    public void askWriteExternalStoragePermission() {
        if (ActivityCompat.checkSelfPermission(CreateNewRequestActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(CreateNewRequestActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(CreateNewRequestActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(CreateNewRequestActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Params.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(CreateNewRequestActivity.this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(CreateNewRequestActivity.this, new String[]{Manifest.permission.CAMERA}, Params.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            } else {
                ActivityCompat.requestPermissions(CreateNewRequestActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Params.MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }
        }
    }


    public void showImage() {
        try {
            RoundedBitmapDrawable circularBitmapDrawable =
                    RoundedBitmapDrawableFactory.create(getResources(), CameraActivity.bitmap);
            circularBitmapDrawable.setCircular(true);
            profileImage.setImageBitmap(CameraActivity.bitmap);
            saveImage(CameraActivity.bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            Glide.with(this)
                    .fromResource()
                    .load(R.drawable.ic_profile)
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .into(profileImage);
        }


    }


    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }


    public void showPopup(final Context context, String nameStr, String emailStr, Bitmap bitmap) {
        final Dialog popupdialog = new Dialog(context);
        popupdialog.setContentView(R.layout.popup_view);
        TextView name = popupdialog.findViewById(R.id.customer_name_et);
        TextView email = popupdialog.findViewById(R.id.customer_email_et);
        ImageView imageView = popupdialog.findViewById(R.id.profile_image);
        Button done = popupdialog.findViewById(R.id.done);

        name.setText(nameStr);
        email.setText(emailStr);


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    popupdialog.cancel();
                    popupdialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            RoundedBitmapDrawable circularBitmapDrawable =
                    RoundedBitmapDrawableFactory.create(getResources(), CameraActivity.bitmap);
            circularBitmapDrawable.setCircular(true);
            imageView.setImageBitmap(CameraActivity.bitmap);
            saveImage(CameraActivity.bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            Glide.with(this)
                    .fromResource()
                    .load(R.drawable.ic_profile)
                    .placeholder(R.drawable.ic_profile)
                    .error(R.drawable.ic_profile)
                    .into(imageView);
        }
        popupdialog.setCanceledOnTouchOutside(true);
        popupdialog.setCancelable(true);
        popupdialog.setTitle("Detail");
        popupdialog.show();

    }
}
