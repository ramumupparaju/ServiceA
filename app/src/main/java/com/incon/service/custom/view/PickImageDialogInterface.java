package com.incon.service.custom.view;

import android.content.Intent;

public interface PickImageDialogInterface {
    void handleIntent(Intent intent, int requestCode);
    void displayPickedImage(String uri, int requestCode);
}