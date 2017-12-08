package com.incon.service.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.incon.service.callbacks.ILocationCallbacks;


public class DeviceLocation {
    private static final String TAG = DeviceLocation.class.getSimpleName();
    private final GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_LOCATION = 2;

    private GoogleApiClient.OnConnectionFailedListener failListener = new
            GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    Log.d(TAG, "onConnectionFailed: ");

                }
            };
    private Context mContext;
    private ILocationCallbacks iLocationCallbacks;
    private GoogleApiClient.ConnectionCallbacks connectionCallbacks = new
            GoogleApiClient.ConnectionCallbacks() {
                @Override
                @SuppressWarnings({"MissingPermission"})//IMP::location based permission only
                public void onConnected(Bundle bundle) {

                    Log.d(TAG, "Location Connected::: ");
//            if (mContext instanceof Activity) {
                    // Get the last known location details
                    if (ActivityCompat.checkSelfPermission(mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(
                            mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode,
                        // String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission.
                        // See the documentation
                        // for ActivityCompat#requestPermissions for more details.
//                    ActivityCompat.requestPermissions((Activity) mContext,
// new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//                    ActivityCompat.requestPermissions((Activity) mContext,
// new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);

                        Log.d(TAG, "checkSelfPermission::: ");


                        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                mGoogleApiClient);

                        // Save the lat/lon of the location obtained.
                        if (mLastLocation != null) {
                            double latitude = mLastLocation.getLatitude();
                            double longitude = mLastLocation.getLongitude();
                            iLocationCallbacks.onLocationListener(new LatLng(latitude, longitude));
                        }
                    } else {
                        iLocationCallbacks.onLocationListener(null);
                    }


            /*} else {
                BKUtilLogger.showDebugLog(BKUtilLogger.BK_RELEASE, "Invalid context");
            }*/
                }

                @Override
                public void onConnectionSuspended(int i) {
                    Log.d(TAG, "Location BKLocation::: onConnectionSuspended ");

                }


            };

    /**
     * BKLocation - Constructor
     * <br><br>
     * Connect to GoogleApiClient
     * to obtain location data
     *
     * @param context            Context
     * @param iLocationCallbacks
     */
    public DeviceLocation(Context context, ILocationCallbacks iLocationCallbacks) {
        mContext = context;
        this.iLocationCallbacks = iLocationCallbacks;
        Log.d(TAG, "Location cityLocation::: ");

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(failListener)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }
}

