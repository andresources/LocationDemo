package com.locationdemo.ex2LocationServicewithLiveData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import androidx.lifecycle.LiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MyLocationProvider extends LiveData<Location> {
    FusedLocationProviderClient mFusedLocationProviderClient;
    LocationRequest mLocationRequest;
    public MyLocationProvider(Context cnt){
        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(cnt);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
    }

    LocationCallback mLocationCallback=new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            //Location loc=locationResult.getLastLocation();
            Location loc = locationResult.getLocations().get(0);
            setValue(loc);
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    protected void onActive() {
        super.onActive();
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,mLocationCallback,null);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }
}
