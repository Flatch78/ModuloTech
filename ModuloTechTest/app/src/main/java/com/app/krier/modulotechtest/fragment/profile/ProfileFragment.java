package com.app.krier.modulotechtest.fragment.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.krier.modulotechtest.R;
import com.app.krier.modulotechtest.fragment.AbstractFragment;
import com.app.krier.modulotechtest.models.RootModel;

/**
 * Created by GuillaumeK on 15/03/2017.
 */

public class ProfileFragment extends AbstractFragment {
    private static final String TAG = ProfileFragment.class.getSimpleName();

    private Context mContext;
    private ProfileFragmentListener mListener;
    private View mMainView;
    private RootModel mRootModel;

    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_profile, container, false);
        updateView();
        return mMainView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof ProfileFragmentListener) {
            mListener = (ProfileFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ContactsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void updateView() {
        if (mMainView != null && mRootModel != null) {
            ((TextView) mMainView.findViewById(R.id.text_view_title))
                    .setText(mRootModel.me.name.title);
            ((TextView) mMainView.findViewById(R.id.text_view_first))
                    .setText(mRootModel.me.name.first);
            ((TextView) mMainView.findViewById(R.id.text_view_last))
                    .setText(mRootModel.me.name.last);

            ((TextView) mMainView.findViewById(R.id.text_view_street))
                    .setText(mRootModel.me.location.street);
            ((TextView) mMainView.findViewById(R.id.text_view_city))
                    .setText(mRootModel.me.location.city);
            ((TextView) mMainView.findViewById(R.id.text_view_state))
                    .setText(mRootModel.me.location.state);
            ((TextView) mMainView.findViewById(R.id.text_view_zip))
                    .setText(mRootModel.me.location.zip);

            ((TextView) mMainView.findViewById(R.id.text_view_gender))
                    .setText(mRootModel.me.gender);
            ((TextView) mMainView.findViewById(R.id.text_view_email))
                    .setText(mRootModel.me.email);
            ((TextView) mMainView.findViewById(R.id.text_view_username))
                    .setText(mRootModel.me.username);
            ((TextView) mMainView.findViewById(R.id.text_view_password))
                    .setText(mRootModel.me.password);
            ((TextView) mMainView.findViewById(R.id.text_view_phone))
                    .setText(mRootModel.me.phone);
            ((TextView) mMainView.findViewById(R.id.text_view_cell))
                    .setText(mRootModel.me.cell);
            ((TextView) mMainView.findViewById(R.id.text_view_SSN))
                    .setText(mRootModel.me.SSN);

            mListener.onRequestImage(mRootModel.me.picture.large,
                    (ImageView) mMainView.findViewById(R.id.image_view_picl));

        }
    }


    /* REQUEST */
    public void responseRequest(RootModel rootModel) {
        mRootModel = rootModel;
        updateView();
    }


    /* SETTER / GETTER */
    @Override
    public int getIdTitle() {
        return R.string.frag_profile_title;
    }
}
