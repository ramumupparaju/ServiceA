package com.incon.service.callbacks;

import com.google.android.gms.maps.model.LatLng;

public interface ILocationCallbacks {
    void onLocationListener(LatLng latLng);
}