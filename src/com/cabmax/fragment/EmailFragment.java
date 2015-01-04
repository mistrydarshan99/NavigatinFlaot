package com.cabmax.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.cabmax.OnTheWay.R;
import com.cabmax.adapter.EmailAdapter;
import com.cabmax.database.DBAdapter;
import com.cabmax.model.EmailModel;

import java.util.List;

public class EmailFragment extends Fragment {

    private DBAdapter database;
    private List<EmailModel> emailList = null;
    private ListView listView;
    private EmailAdapter emailAdapter;
    private EditText edtSearch;

	public EmailFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        database = new DBAdapter(getActivity());
        database.open();
        emailList = database.getEmailList();
        database.close();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.email_start, container, false);
        listView = (ListView)rootView.findViewById(R.id.lstEmail);
        edtSearch = (EditText)rootView.findViewById(R.id.etEmailSearch);

        emailAdapter = new EmailAdapter(emailList,getActivity());
        listView.setAdapter(emailAdapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < before) {
                    // We're deleting char so we need to reset the adapter data
                    emailAdapter.resetData();
                }
                emailAdapter.getFilter().filter(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return rootView;
    }

    private void inboxMail(){
        emailList.clear();
        database.open();
        emailList = database.getEmailList();
        database.close();
        emailAdapter.setEmailDisplay(emailList);
        emailAdapter.notifyDataSetChanged();
    }

    private void sentMail(){
        emailList.clear();
        database.open();
        emailList = database.getSentEmailList();
        database.close();
        emailAdapter.setEmailDisplay(emailList);
        emailAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itmInbox:
                inboxMail();
                return true;
            case R.id.itmSent:
                sentMail();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
