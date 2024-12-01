package com.delivery.deliveryapp.model;

import android.webkit.JavascriptInterface;

import com.delivery.deliveryapp.view.activities.DiscoverActivity;
import com.delivery.deliveryapp.view.activities.MoreInfosActivity;

public class WebAppInterface {

    private DiscoverActivity discoverActivity;
    private MoreInfosActivity moreInfosActivity;
    private Address mAddressHolder;

    public WebAppInterface(DiscoverActivity discoverActivity, Address addressHolder) {
        this.discoverActivity = discoverActivity;
        this.mAddressHolder = addressHolder;
    }

    public WebAppInterface(MoreInfosActivity moreInfosActivity,  Address addressHolder) {
        this.moreInfosActivity = moreInfosActivity;
        this.mAddressHolder = addressHolder;
    }

    @JavascriptInterface
    public double getLatitude() {
        return mAddressHolder.getLatitude();
    }

    @JavascriptInterface
    public double getLongitude() {
        return mAddressHolder.getLongitude();
    }

    @JavascriptInterface
    public void onMarkerClick(String discoveryName) {
        // Call the onMarkerClick method in DiscoverActivity
        if (discoverActivity != null) {
            discoverActivity.onMarkerClick(discoveryName);
        }
    }
}
