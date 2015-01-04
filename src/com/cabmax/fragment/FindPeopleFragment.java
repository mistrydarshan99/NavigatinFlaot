package com.cabmax.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import com.cabmax.OnTheWay.R;
import com.cabmax.adapter.ContactsAdapter;
import com.cabmax.database.DBAdapter;
import com.cabmax.model.ContactModel;

import java.util.List;

public class FindPeopleFragment extends Fragment {

    private DBAdapter database;
    private List<ContactModel> contactList = null;
    private ListView listView;
    private ContactsAdapter contactAdapter;
    private EditText edtSearch;

	public FindPeopleFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = new DBAdapter(getActivity());
        database.createDatabase();

        database.open();
        contactList = database.getContacts();
        database.close();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_find_people, container, false);

        listView = (ListView)rootView.findViewById(R.id.lstData);
        edtSearch = (EditText)rootView.findViewById(R.id.etSearch);

        contactAdapter = new ContactsAdapter(contactList,getActivity());
        listView.setAdapter(contactAdapter);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < before) {
                    // We're deleting char so we need to reset the adapter data
                    contactAdapter.resetData();
                }
                contactAdapter.getFilter().filter(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
    }
}
