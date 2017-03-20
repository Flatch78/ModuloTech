package com.app.krier.modulotechtest.manager.request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by GuillaumeK on 16/03/2017.
 */

public class RequestHttp {
    private static final String TAG = RequestHttp.class.getSimpleName();
    private RequestListener mListener;

    public enum ETypeRequest {
        GET,
        POST,
        PUT
    }

    private class ImageViewParam {
        private String mUrl;
        private ImageView mImageView;

        public ImageViewParam(String url, ImageView imageView) {
            mUrl = url;
            mImageView = imageView;
        }

        public ImageView getImageView() {
            return mImageView;
        }

        public String getUrl() {
            return mUrl;
        }
    }

    private String mStringUrl = null;


    public RequestHttp(RequestListener listener, String url) {
        mListener = listener;
        mStringUrl = url;
    }

    public void requestHttp(ETypeRequest typeRequest) {
        requestHttp(typeRequest, "");
    }

    public void requestHttp(ETypeRequest typeRequest, String path) {
        if (typeRequest == ETypeRequest.GET) {
            requestHttpGet(path);
        } else {
            Log.e(TAG, "Type " + typeRequest.name() + " not supported");
        }
    }

    public void requestHttpImage(ETypeRequest typeRequest, String url, ImageView imageView) {
        if (typeRequest == ETypeRequest.GET) {
            requestHttpGetImage(url, imageView);
        } else {
            Log.e(TAG, "Type " + typeRequest.name() + " not supported");
        }
    }

    private void requestHttpGet(String path) {
        new RequestTask().execute(mStringUrl + path);
    }

    private void requestHttpGetImage(String url, ImageView imageView) {
        ImageViewParam param = new ImageViewParam(url, imageView);
        new RequestTaskImage().execute(param);
    }


    private class RequestTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            String result = "";
            HttpURLConnection urlConnection = null;
            try {
                String stringUrl = urls[0];
                URL url = new URL(stringUrl);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                InputStream in = urlConnection.getInputStream();
                InputStreamReader isw = new InputStreamReader(in);

                int data = isw.read();
                while (data != -1) {
                    char current = (char) data;
                    data = isw.read();
                    result += current;
                }
            } catch (Exception e) {
                this.exception = e;
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return result;
        }

        protected void onPostExecute(String result) {
            if (result != null) {
                mListener.onResponse(result);
            } else {
                mListener.onError(exception != null ? exception.getMessage(): "Error Request loading");
            }
        }
    }

    private class RequestTaskImage extends AsyncTask<ImageViewParam, Void, Bitmap> {

        private Exception exception;
        private ImageViewParam mImageViewParam;

        protected Bitmap doInBackground(ImageViewParam... params) {
            HttpsURLConnection urlConnection = null;
            Bitmap bitmap = null;
            try {
                mImageViewParam = params[0];
                URL url = new URL(mImageViewParam.getUrl());

                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setDoInput(true);
                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                this.exception = e;
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null && mImageViewParam.getImageView() != null) {
                mImageViewParam.getImageView().setImageBitmap(image);
            } else {
                mListener.onError(exception != null ? exception.getMessage(): "Error Image loading");
            }
        }
    }


    public static int calculateSampleSize(BitmapFactory.Options options,
                                          int reqWidth, int reqHeight) {

        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth || height > reqHeight) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }
}
