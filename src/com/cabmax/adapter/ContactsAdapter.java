package com.cabmax.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.cabmax.OnTheWay.R;
import com.cabmax.model.ContactModel;

import java.util.ArrayList;
import java.util.List;


public class ContactsAdapter extends BaseAdapter implements Filterable {

    private List<ContactModel> contactsDisplay;
    private List<ContactModel> orgListContacts;
    private Activity activity;
    private LayoutInflater inflater = null;
    private Filter contactFilter;

    public ContactsAdapter(final List<ContactModel> listContacts, Activity activity){
        this.orgListContacts = listContacts;
        this.contactsDisplay = listContacts;
        this.activity = activity;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Filter getFilter() {
        if (contactFilter == null)
            contactFilter = new ContactFilter();

        return contactFilter;
    }

    @Override
    public int getCount() {
        return contactsDisplay.size() > 0 ? contactsDisplay.size() : 0;
    }

    @Override
    public ContactModel getItem(int position) {
        return contactsDisplay.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void resetData() {
        contactsDisplay = orgListContacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.contact_row, parent, false);
            holder.txtContacts = (TextView) convertView.findViewById(R.id.txtContact);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ContactModel contactObj = getItem(position);
        holder.txtContacts.setText(contactObj.getContactName());

        return convertView;
    }

    public class ViewHolder {
        private TextView txtContacts;
    }

    private class ContactFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
            // No filter implemented we return all the list
                results.values = orgListContacts;
                results.count = orgListContacts.size();
            }
            else {
                // We perform filtering operation
                List<ContactModel> filterContactList = new ArrayList<ContactModel>();

                for (ContactModel contacts : contactsDisplay) {
                    if (contacts.getContactName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        filterContactList.add(contacts);
                }

                results.values = filterContactList;
                results.count = filterContactList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0){
                notifyDataSetInvalidated();
            }
            else {
                contactsDisplay = (List<ContactModel>) results.values;
                notifyDataSetChanged();
            }
        }
    }
}
