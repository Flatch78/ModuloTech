package com.app.krier.modulotechtest.fragment.search;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.app.krier.modulotechtest.R;
import com.app.krier.modulotechtest.fragment.AbstractFragment;
import com.app.krier.modulotechtest.fragment.contact.ContactAdapter;
import com.app.krier.modulotechtest.fragment.contact.ContactsFragmentListener;
import com.app.krier.modulotechtest.models.RootModel;

/**
 * Created by GuillaumeK on 15/03/2017.
 */

public class SearchFragment extends AbstractFragment {
    private static final String TAG = SearchFragment.class.getSimpleName();

    private Context mContext;
    private ContactsFragmentListener mListener;
    private View mMainView;
    private RootModel mRootModel;
    private ContactAdapter mContactAdapter;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
        mMainView = inflater.inflate(R.layout.fragment_search, container, false);
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

            EditText editTextSearch = (EditText) mMainView.findViewById(R.id.edit_text_search);

            editTextSearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    mContactAdapter.getFilter().filter(cs.toString());
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                }
            });

        }
    }

    private void fillInContactList() {
        ExpandableListView expandableListView = (ExpandableListView) mMainView.findViewById(R.id.list_contacts);
        mContactAdapter = new ContactAdapter(mContext, mRootModel.contacts);
        expandableListView.setAdapter(mContactAdapter);
    }


    public void responseRequest(RootModel rootModel) {
        mRootModel = rootModel;
        updateView();
    }


    @Override
    public int getIdTitle() {
        return R.string.frag_search_title;
    }
}
