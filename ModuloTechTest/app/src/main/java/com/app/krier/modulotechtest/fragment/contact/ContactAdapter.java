package com.app.krier.modulotechtest.fragment.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.krier.modulotechtest.R;
import com.app.krier.modulotechtest.manager.request.Request;
import com.app.krier.modulotechtest.manager.request.RequestListener;
import com.app.krier.modulotechtest.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuillaumeK on 16/03/2017.
 */

public class ContactAdapter
        extends BaseExpandableListAdapter
        implements RequestListener,
        Filterable {

    private Context mContext;
    private List<ContactModel> mListContacts;
    private List<ContactModel> mTmpListContacts = null;
    private ContactsFilter contactsFilter = null;

    private class ContactViewHolder {
        public TextView title;
        public TextView first;
        public TextView last;
        public TextView street;
        public TextView city;
        public TextView state;
        public TextView zip;
        public TextView gender;
        public TextView email;
        public TextView username;
        public TextView password;
        public TextView phone;
        public TextView cell;
        public TextView SSN;
        public ImageView pictureLarge;
    }

    public ContactAdapter(Context context, List<ContactModel> contacts) {
        mContext = context;
        mListContacts = contacts;
        mTmpListContacts = mListContacts;
    }

    @Override
    public int getGroupCount() {
        return mTmpListContacts.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mTmpListContacts.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_contact_elem,
                    parent, false);
        }
        if (isExpanded) {
            convertView.findViewById(R.id.layout_content_fragment).setVisibility(View.GONE);
            convertView.findViewById(R.id.layout_empty_fragment).setVisibility(View.VISIBLE);
        } else {
            convertView.findViewById(R.id.layout_content_fragment).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.layout_empty_fragment).setVisibility(View.GONE);
            convertView.findViewById(R.id.layout_info).setVisibility(View.GONE);
        }
        updateView(convertView, groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_contact_elem,
                    parent, false);
        }
        convertView.findViewById(R.id.layout_content_fragment).setVisibility(View.VISIBLE);
        convertView.findViewById(R.id.layout_empty_fragment).setVisibility(View.GONE);
        convertView.findViewById(R.id.layout_info).setVisibility(View.VISIBLE);

        updateView(convertView, groupPosition);

        return convertView;
    }

    private void updateView(View convertView, int position) {
        ContactViewHolder viewHolder = (ContactViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ContactViewHolder();

            viewHolder.title = (TextView) convertView.findViewById(R.id.text_view_title);
            viewHolder.first = (TextView) convertView.findViewById(R.id.text_view_first);
            viewHolder.last = (TextView) convertView.findViewById(R.id.text_view_last);

            viewHolder.street = (TextView) convertView.findViewById(R.id.text_view_street);
            viewHolder.city = (TextView) convertView.findViewById(R.id.text_view_city);
            viewHolder.state = (TextView) convertView.findViewById(R.id.text_view_state);
            viewHolder.zip = (TextView) convertView.findViewById(R.id.text_view_zip);

            viewHolder.gender = (TextView) convertView.findViewById(R.id.text_view_gender);
            viewHolder.email = (TextView) convertView.findViewById(R.id.text_view_email);
            viewHolder.username = (TextView) convertView.findViewById(R.id.text_view_username);
//            viewHolder.password = (TextView) convertView.findViewById(R.id.text_view_password);
            viewHolder.phone = (TextView) convertView.findViewById(R.id.text_view_phone);
            viewHolder.cell = (TextView) convertView.findViewById(R.id.text_view_cell);
            viewHolder.SSN = (TextView) convertView.findViewById(R.id.text_view_SSN);
            viewHolder.pictureLarge = (ImageView) convertView.findViewById(R.id.image_view_picl);

            convertView.setTag(viewHolder);

        }
        ContactModel contact = mTmpListContacts.get(position);
        if (contact != null) {
            viewHolder.title.setText(contact.name.title);
            viewHolder.first.setText(contact.name.first);
            viewHolder.last.setText(contact.name.last);

            viewHolder.street.setText(contact.location.street);
            viewHolder.city.setText(contact.location.city);
            viewHolder.state.setText(contact.location.state);
            viewHolder.zip.setText(contact.location.zip);

            viewHolder.gender.setText(contact.gender);
            viewHolder.email.setText(contact.email);
            viewHolder.username.setText(contact.username);
//            viewHolder.password.setText(contact.password);
            viewHolder.phone.setText(contact.phone);
            viewHolder.cell.setText(contact.cell);
            viewHolder.SSN.setText(contact.SSN);

                Request.instanceRequest(this).makeImageRequest(contact.picture.large, viewHolder.pictureLarge);
        }

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public Filter getFilter() {
        if (contactsFilter == null) {
            contactsFilter = new ContactsFilter();
        }
        return contactsFilter;
    }

    /* REQUEST */

    @Override
    public void onResponse(String response) {

    }

    @Override
    public void onError(String error) {

    }


    private class ContactsFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = mListContacts;
                results.count = mListContacts.size();
            } else {
                List<ContactModel> listContact = new ArrayList<ContactModel>();

                for (ContactModel contact : mListContacts) {
                    if (contact.name.first.toUpperCase().startsWith(constraint.toString().toUpperCase())
                            || contact.name.last.toUpperCase().startsWith(constraint.toString().toUpperCase())
                            || contact.name.title.toUpperCase().startsWith(constraint.toString().toUpperCase())
                            || contact.username.toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        listContact.add(contact);
                    }
                }
                results.values = listContact;
                results.count = listContact.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                mTmpListContacts = (List<ContactModel>) results.values;
                notifyDataSetChanged();
            }
        }
    }

}
