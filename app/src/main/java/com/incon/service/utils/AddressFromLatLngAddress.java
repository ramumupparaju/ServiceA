package com.incon.service.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.incon.service.AppConstants;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * LocationAddress uses to to find the location address.
 */
public class AddressFromLatLngAddress {
    private static final String TAG = AddressFromLatLngAddress.class.getName();

    public static void getAddressFromLocation(final Context context, final LatLng latLng, final int
            requestCode, final Handler handler) {


        if (!Geocoder.isPresent()) {
            Message message = Message.obtain();
            message.setTarget(handler);
            message.what = requestCode;
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.BundleConstants.LOCATION_ADDRESS, null);
            message.setData(bundle);
            message.sendToTarget();
            return;
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = null;
                Address addressToSend = null;
                try {
                    geocoder = new Geocoder(context, Locale.getDefault());
                } catch (NullPointerException e) {
                    //DO nothing
                }

                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latLng.latitude, latLng.longitude,
                            AppConstants.GoogleMapConstants.GEOCODER_MAX_ADDRESS_RESULTS);
                    if (addressList != null && addressList.size() > 0) {
                        addressToSend = addressList.get(0);
                    }

                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    message.what = requestCode;
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppConstants.BundleConstants.LOCATION_ADDRESS, addressToSend);
                    message.setData(bundle);
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }

    public static void getLocationFromAddress(final Context context, final String postalCode,
                                              final int requestCode, final Handler handler) {
        if (!Geocoder.isPresent()) {
            Message message = Message.obtain();
            message.setTarget(handler);
            message.what = requestCode;
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstants.BundleConstants.LOCATION_ADDRESS, null);
            message.setData(bundle);
            message.sendToTarget();
            return;
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = null;
                Address addressToSend = null;
                try {
                    geocoder = new Geocoder(context, Locale.getDefault());
                } catch (NullPointerException e) {
                    //DO nothing
                }

                try {
                    List<Address> addressList = geocoder.
                            getFromLocationName(postalCode,
                                    AppConstants.GoogleMapConstants.GEOCODER_MAX_ADDRESS_RESULTS);
                    if (addressList != null && addressList.size() > 0) {
                        addressToSend = addressList.get(0);
                    }

                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    message.what = requestCode;
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppConstants.BundleConstants.LOCATION_ADDRESS, addressToSend);
                    message.setData(bundle);
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}