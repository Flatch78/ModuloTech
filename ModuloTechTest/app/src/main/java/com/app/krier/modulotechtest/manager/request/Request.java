package com.app.krier.modulotechtest.manager.request;

import android.widget.ImageView;

/**
 * Created by GuillaumeK on 16/03/2017.
 */

public class Request implements RequestListener {
    private static final String TAG = Request.class.getSimpleName();
    private static final String mUrl = "http://www.storage42.com/";
    private static final String mPath = "contacts.json";

    private RequestHttp mRequestHttp = null;

    private RequestListener mListener;

    public static Request instanceRequest(RequestListener listener) {
        return new Request(listener);
    }

    private Request(RequestListener listener) {
        mListener = listener;
        mRequestHttp = new RequestHttp(this, mUrl);
    }

    public boolean makeGetRequest() {
        if (mRequestHttp != null) {
            mRequestHttp.requestHttp(RequestHttp.ETypeRequest.GET, mPath);
           return true;
        }
        return false;
    }

    public boolean makeImageRequest(String url, ImageView imageView) {
        if (mRequestHttp != null) {
            mRequestHttp.requestHttpImage(RequestHttp.ETypeRequest.GET, url, imageView);
           return true;
        }
        return false;
    }

    @Override
    public void onResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    public void onError(String error) {
        mListener.onError(error);
    }
}
