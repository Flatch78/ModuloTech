package com.app.krier.modulotechtest.fragment.contact;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.app.krier.modulotechtest.R;
import com.app.krier.modulotechtest.fragment.AbstractFragment;
import com.app.krier.modulotechtest.models.RootModel;

/**
 * Created by GuillaumeK on 15/03/2017.
 */
public class ContactsFragment extends AbstractFragment {
    private static final String TAG = ContactsFragment.class.getSimpleName();

    private Context mContext;
    private ContactsFragmentListener mListener;
    private View mMainView;
    private RootModel mRootModel;

    public ContactsFragment() {
    }

    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
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
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_contacts, container, false);
        updateView();
        return mMainView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof ContactsFragmentListener) {
            mListener = (ContactsFragmentListener) context;
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
            fillInContactList();
        }
    }

    private void fillInContactList() {
        ExpandableListView expandableListView = (ExpandableListView) mMainView.findViewById(R.id.list_contacts);
        expandableListView.setAdapter(new ContactAdapter(mContext, mRootModel.contacts));
    }

    public void responseRequest(RootModel rootModel) {
        mRootModel = rootModel;
        updateView();
    }


    @Override
    public int getIdTitle() {
        return R.string.frag_contact_title;
    }
}
