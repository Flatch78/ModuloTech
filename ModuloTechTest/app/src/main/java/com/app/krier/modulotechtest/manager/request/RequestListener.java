package com.app.krier.modulotechtest.manager.request;

/**
 * Created by GuillaumeK on 16/03/2017.
 */

public interface RequestListener {
    void onResponse(String response);
    void onError(String error);
}
