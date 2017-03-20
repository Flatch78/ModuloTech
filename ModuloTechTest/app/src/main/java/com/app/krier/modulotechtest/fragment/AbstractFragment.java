package com.app.krier.modulotechtest.fragment;

import android.app.Fragment;

import com.app.krier.modulotechtest.models.RootModel;

/**
 * Created by GuillaumeK on 15/03/2017.
 */

public abstract class AbstractFragment extends Fragment {
    public abstract int getIdTitle();
    public abstract void responseRequest(RootModel rootModel);
}
