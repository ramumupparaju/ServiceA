package com.incon.service.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.incon.service.R;
import com.incon.service.callbacks.AlertDialogCallback;

import static com.incon.service.AppConstants.DEFAULT_GOOGLE_MAP_ZOOM_LEVEL;

public class AppAlertDialogMap extends Dialog implements View.OnClickListener, OnMapReadyCallback {
    private final Context context;
    //All final attributes
    private final String button2text; // required
    private final LatLng location;
    private final AlertDialogCallback mAlertDialogCallback; // required
    private final String address;

    /**
     * @param builder
     */
    private AppAlertDialogMap(AlertDialogBuilder builder) {
        super(builder.context);
        this.context = builder.context;
        this.button2text = builder.button2Text;
        this.location = builder.location;
        this.address = builder.address;
        this.mAlertDialogCallback = builder.callback;
    }

    public void showDialog() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.dialog_map_app, null);


        MapView mapView = (MapView) contentView.findViewById(R.id.map_monitor);
        mapView.getMapAsync(this);

        TextView addressTv = (TextView) contentView.findViewById(R.id.view_address);
        addressTv.setText(address);
        Button mSecondButtonTextView = (Button) contentView.findViewById(R.id.dialog_second_button);
        if (!TextUtils.isEmpty(button2text)) {
            mSecondButtonTextView.setText(button2text);
        } else {
            mSecondButtonTextView.setVisibility(View.GONE);
        }
        mSecondButtonTextView.setOnClickListener(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        setCancelable(false);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_shadow);
        show();
    }

    @Override
    public void onClick(View view) {
        if (mAlertDialogCallback == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.dialog_first_button:
                mAlertDialogCallback.alertDialogCallback(AlertDialogCallback.OK);
                break;
            case R.id.dialog_second_button:
                mAlertDialogCallback.alertDialogCallback(AlertDialogCallback.CANCEL);
                break;
            default:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location).icon(BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_GREEN));
        googleMap.addMarker(markerOptions);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(location);
        LatLngBounds bounds = builder.build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(
                bounds, DEFAULT_GOOGLE_MAP_ZOOM_LEVEL));
    }

    public static class AlertDialogBuilder {

        private final Context context;
        private final AlertDialogCallback callback;
        private String button2Text;
        private LatLng location;
        private String address;

        public AlertDialogBuilder(Context context, AlertDialogCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        public AlertDialogBuilder location(LatLng location, String address) {
            this.location = location;
            this.address = address;
            return this;
        }

        public AlertDialogBuilder button2Text(String button2Text) {
            this.button2Text = button2Text;
            return this;
        }

        //Return the finally constructed User object
        public AppAlertDialogMap build() {
            AppAlertDialogMap dialog = new AppAlertDialogMap(this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }
    }
}
