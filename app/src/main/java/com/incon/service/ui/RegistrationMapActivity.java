package com.incon.service.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.callbacks.ILocationCallbacks;
import com.incon.service.databinding.ActivityRegistrationMapBinding;
import com.incon.service.dto.registration.AddressInfo;
import com.incon.service.utils.AddressFromLatLngAddress;
import com.incon.service.utils.DeviceLocation;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by LENOVO on 9/30/2017.
 */

public class RegistrationMapActivity extends BaseActivity implements OnMapReadyCallback {
    private ActivityRegistrationMapBinding binding;
    public GoogleMap mGoogleMap;
    private Marker marker;
    private String preZipCode;
    private LatLng locationAddress;
    private AddressInfo addressInfo;
    private AddressFromLatLngAddress addressFromLatLngAddress;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_registration_map;
    }

    @Override
    protected void initializePresenter() {
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setAddressActivity(this);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.map_monitor, mapFragment).commit();
        mapFragment.getMapAsync(this);

        addressInfo = new AddressInfo();
        Intent intent = getIntent();
        if (intent != null) {
            String stringExtra = intent.getStringExtra(IntentConstants.LOCATION_COMMA);
            if (!TextUtils.isEmpty(stringExtra)) {
                String[] splitLocation = stringExtra.split(COMMA_SEPARATOR);
                locationAddress = new LatLng(Double.parseDouble(
                        splitLocation[0]), Double.parseDouble(splitLocation[1]));
                binding.buttonOk.setText(getString(R.string.action_ok));
            }
        }
        addZipcodeWatcher();
        addressFromLatLngAddress = new AddressFromLatLngAddress();
    }

    public void onOkClick() {
        String zipCode = binding.edittextPincode.getText().toString();
        if (TextUtils.isEmpty(zipCode)) {
            showErrorMessage(getString(R.string.error_select_location));
            return;
        }

        Intent dataIntent = new Intent();
        StringBuilder addressBuilder = new StringBuilder(zipCode);
        addressBuilder.append(COMMA_SEPARATOR + binding.edittextCity.getText().toString());
        addressBuilder.append(COMMA_SEPARATOR + binding.edittextState.getText().toString());
        addressBuilder.append(COMMA_SEPARATOR + binding.edittextCountry.getText().toString());
        dataIntent.putExtra(IntentConstants.ADDRESS_COMMA, addressBuilder.toString());
        dataIntent.putExtra(IntentConstants.LOCATION_COMMA, locationAddress.latitude
                + COMMA_SEPARATOR + locationAddress.longitude);
        dataIntent.putExtra(IntentConstants.ADDRESS_INFO, addressInfo);
        setResult(Activity.RESULT_OK, dataIntent);
        finish();
    }

    private void addZipcodeWatcher() {
        Observable<TextViewAfterTextChangeEvent> zipCodeChangeObserver =
                RxTextView.afterTextChangeEvents(binding.edittextPincode);
        DisposableObserver<TextViewAfterTextChangeEvent> observer =
                new DisposableObserver<TextViewAfterTextChangeEvent>() {

                    @Override
                    public void onNext(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        String zipCode = textViewAfterTextChangeEvent.editable().toString();

                        if (!TextUtils.isEmpty(preZipCode)) {
                            if (zipCode.contains(preZipCode) && zipCode.length() > 5) {
                                AppUtils.hideSoftKeyboard(RegistrationMapActivity.this,
                                        binding.edittextPincode);
//                                registrationMapPresenter.doPostalCode(zipCode);
                                loadLocationFromZipcode();
                            }
                        }
                        preZipCode = zipCode;
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                };
        zipCodeChangeObserver.subscribe(observer);
    }

    private void loadLocationFromZipcode() {
        addressFromLatLngAddress.getLocationFromAddress(RegistrationMapActivity.this,
                binding.edittextPincode.getText().toString(),
                RequestCodes.LOCATION_LATLNG_FROM_ADDRESS, new LocationHandler());
    }
    private class LocationHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            Bundle bundle = message.getData();
            Address locationAddress = bundle.getParcelable(BundleConstants
                    .LOCATION_ADDRESS);
            try {
                addressInfo.setCity(locationAddress.getLocality());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                addressInfo.setCoordinates(locationAddress.getLatitude() + COMMA_SEPARATOR +
                        locationAddress.getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                addressInfo.setCountry(locationAddress.getCountryName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                addressInfo.setZipCode(locationAddress.getPostalCode());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                addressInfo.setState(locationAddress.getAdminArea());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                addressInfo.setLatitude(locationAddress.getLatitude());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                addressInfo.setLongitude(locationAddress.getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (locationAddress != null) {
                switch (message.what) {
                    case RequestCodes.LOCATION_ADDRESS_FROM_LATLNG:
                        binding.setAddress(locationAddress);
                        break;
                    case RequestCodes.LOCATION_LATLNG_FROM_ADDRESS:
                        if (locationAddress.hasLatitude() && locationAddress.hasLongitude()) {
                            displayMarker(new LatLng(locationAddress.getLatitude(),
                                            locationAddress.getLongitude()),
                                    true);
                        }
                        break;
                    default:
                        //do nothing
                }
            } else {
                showErrorMessage(getString(R.string.error_location));
            }
        }
    }


    ILocationCallbacks iLocationCallbacks = new ILocationCallbacks() {
        @Override
        public void onLocationListener(LatLng latLng) {
            displayMarker(latLng, true);
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mGoogleMap == null) {
            mGoogleMap = googleMap;
            if (locationAddress != null) {
                displayMarker(locationAddress, true);
            } else {
                new DeviceLocation(this, iLocationCallbacks);
            }
            /*UiSettings googlemapSettings = mGoogleMap.getUiSettings();
            googlemapSettings.setZoomControlsEnabled(true);
            googlemapSettings.setMyLocationButtonEnabled(true);
            googlemapSettings.setRotateGesturesEnabled(true);
            googlemapSettings.setMapToolbarEnabled(true);*/


            mGoogleMap.setOnMapLongClickListener(onMapLongClickListener);
        }
    }

    private GoogleMap.OnMapLongClickListener onMapLongClickListener = new
            GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    displayMarker(latLng, false);
                }
            };

    private void displayMarker(LatLng latLng, boolean zoomMap) {

        if (latLng != null) {
            this.locationAddress = latLng;
            if (marker == null) {
                MarkerOptions options = new MarkerOptions()
                        .position(latLng);
                marker = mGoogleMap.addMarker(options);
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                        GoogleMapConstants.DEFAULT_ZOOM_LEVEL));
            } else {
                marker.setPosition(latLng);
            }
            if (zoomMap) {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                        GoogleMapConstants.DEFAULT_ZOOM_LEVEL));
            }
            loadLocationDetailsFromGeocoder();
        }
    }

    private void loadLocationDetailsFromGeocoder() {

        addressFromLatLngAddress.getAddressFromLocation(RegistrationMapActivity.this,
                locationAddress, RequestCodes.LOCATION_ADDRESS_FROM_LATLNG, new LocationHandler());

    }
}
