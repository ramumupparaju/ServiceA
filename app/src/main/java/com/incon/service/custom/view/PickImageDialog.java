package com.incon.service.custom.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import com.incon.service.AppConstants;
import com.incon.service.R;
import com.incon.service.ui.fullscreenimageview.FullScreenImageViewActivity;
import com.incon.service.utils.ImageUtils;
import com.incon.service.utils.Logger;

import java.io.File;
import java.io.Serializable;


public class PickImageDialog implements Serializable {

    private static final String TAG = PickImageDialog.class.getSimpleName();
    public PickImageDialogInterface mImageHandlingDelegate;
    private Activity mActivity;
    private static final String TYPE_IMAGE_STR = "image/*";
    private String realPathFromUri;

    public PickImageDialog(Activity activity) {
        this.mActivity = activity;
    }

    public void initDialogLayout() {

        CharSequence[] items = mActivity.getResources().getStringArray(R.array.select_image_list);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mActivity,
                android.R.style.Theme_Material_Dialog_Alert);
        //builder.setTitle(getString(R.string.app_name));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int item) {
                        dialogInterface.dismiss();
                switch (item) {
                    case 0:
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (cameraIntent.resolveActivity(mActivity.getPackageManager()) != null) {
                            File directoryDcim = new File(
                                    Environment.getExternalStoragePublicDirectory(
                                            Environment.DIRECTORY_DCIM) + "/"
                                            + System.currentTimeMillis() + ".jpg");
                            realPathFromUri = directoryDcim.getAbsolutePath();
                            cameraIntent.putExtra(
                                    MediaStore.EXTRA_OUTPUT, Uri.fromFile(directoryDcim));
                            mImageHandlingDelegate.handleIntent(
                                    cameraIntent, AppConstants.RequestCodes.TAKE_PHOTO);
                        }
                        break;
                    case 1:
                        Intent galleryIntent = new Intent(
                                Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.setType(TYPE_IMAGE_STR);
                        mImageHandlingDelegate.handleIntent(
                                galleryIntent, AppConstants.RequestCodes.PICK_FROM_GALLERY);
                        break;
                    case 2:
                        Intent fullScreenIntent = new Intent(mActivity,
                                FullScreenImageViewActivity.class);
                        mImageHandlingDelegate.handleIntent(
                                fullScreenIntent, AppConstants.RequestCodes.SEND_IMAGE_PATH);
                    default:
                        break;
                }
            }
        });
        builder.setCancelable(true);
        builder.create().show();
    }

    public void onActivityResult(final int requestCode, int resultCode, final Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstants.RequestCodes.TAKE_PHOTO:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // compress image
                            realPathFromUri = ImageUtils.compressImage(realPathFromUri);
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Logger.e(TAG, "PickImageDialog :" + realPathFromUri);
                                    mImageHandlingDelegate.displayPickedImage(realPathFromUri,
                                            requestCode);
                                }
                            });
                        }
                    }).start();
                    break;
                case AppConstants.RequestCodes.PICK_FROM_GALLERY:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            realPathFromUri = ImageUtils.getRealPathFromURI(
                                    mActivity, data.getData());
                            // compress image
                            realPathFromUri = ImageUtils.compressImage(realPathFromUri);
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Logger.e(TAG, "PickImageDialog :" + realPathFromUri);
                                    mImageHandlingDelegate.displayPickedImage(realPathFromUri,
                                            requestCode);
                                }
                            });
                        }
                    }).start();
                    break;
                default:
                    break;
            }
        }
    }

    /*private void sendImageUriToActivity(Uri uri) {
        if (uri != null) {
            String realPathFromUri = ImageUtils.getRealPathFromURI(mActivity, uri);
            if (realPathFromUri != null) {
                mImageHandlingDelegate.displayPickedImage(realPathFromUri, requestCode);
            }
        }
    }*/


}
