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
import com.cabmax.model.EmailModel;

import java.util.ArrayList;
import java.util.List;


public class EmailAdapter extends BaseAdapter implements Filterable  {

    private List<EmailModel> emailDisplay;
    private List<EmailModel> orgListEmail;
    private Activity activity;
    private LayoutInflater inflater = null;
    private Filter contactFilter;

    public EmailAdapter(final List<EmailModel> listEmail, Activity activity){
        this.emailDisplay = listEmail;
        this.orgListEmail = listEmail;
        this.activity = activity;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public List<EmailModel> getEmailDisplay() {
        return emailDisplay;
    }

    public void setEmailDisplay(List<EmailModel> emailDisplay) {
        this.emailDisplay = emailDisplay;
        this.orgListEmail = emailDisplay;
    }

    @Override
    public Filter getFilter() {
        if (contactFilter == null)
            contactFilter = new ContactFilter();

        return contactFilter;
    }

    public class ViewHolder {
        private TextView txtContacts;
    }

    @Override
    public int getCount() {
        return emailDisplay.size() > 0 ? emailDisplay.size() : 0;
    }

    @Override
    public EmailModel getItem(int position) {
        return emailDisplay.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void resetData() {
        emailDisplay = orgListEmail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.email_row, parent, false);
            holder.txtContacts = (TextView) convertView.findViewById(R.id.txtEmailName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EmailModel emailObj = getItem(position);
        holder.txtContacts.setText(emailObj.getEmailName());

        return convertView;
    }

    private class ContactFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
            // No filter implemented we return all the list
                results.values = orgListEmail;
                results.count = orgListEmail.size();
            }
            else {
                // We perform filtering operation
                List<EmailModel> filterEmailList = new ArrayList<EmailModel>();

                for (EmailModel contacts : emailDisplay) {
                    if (contacts.getEmailName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        filterEmailList.add(contacts);
                }

                results.values = filterEmailList;
                results.count = filterEmailList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
//            if (results.count == 0){
//                notifyDataSetInvalidated();
//            }
//            else {
                emailDisplay = (List<EmailModel>) results.values;
                notifyDataSetChanged();
//            }
        }
    }
}
