package com.app.krier.modulotechtest.fragment.credit;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.krier.modulotechtest.R;
import com.app.krier.modulotechtest.fragment.AbstractFragment;
import com.app.krier.modulotechtest.fragment.contact.ContactsFragmentListener;
import com.app.krier.modulotechtest.models.RootModel;

/**
 * Created by GuillaumeK on 15/03/2017.
 */
public class CreditFragment extends AbstractFragment {
    private static final String TAG = CreditFragment.class.getSimpleName();

    private ContactsFragmentListener mListener;

    public CreditFragment() {
    }

    public static CreditFragment newInstance() {
        CreditFragment fragment = new CreditFragment();
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
        return inflater.inflate(R.layout.fragment_credits, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

    public void responseRequest(RootModel rootModel) {

    }


    @Override
    public int getIdTitle() {
        return R.string.frag_credit_title;
    }
}
