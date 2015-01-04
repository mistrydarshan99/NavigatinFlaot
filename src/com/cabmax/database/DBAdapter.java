package com.cabmax.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.cabmax.adapter.Abhan;
import com.cabmax.model.ContactModel;
import com.cabmax.model.EmailModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBAdapter {
	private final Context mContext;
	private final DataBaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	public DBAdapter(Context context) {
		this.mContext = context;
		mDbHelper = new DataBaseHelper(mContext);
	}

	public DBAdapter createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public DBAdapter open() throws SQLException {
		try {
			mDbHelper.openDataBase();
			mDbHelper.close();
			mDb = mDbHelper.getReadableDatabase();
		} catch (SQLException mSQLException) {
			Abhan.abhanLog(5, mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

    public List<ContactModel> getContacts(){
       List<ContactModel> listContacts = null;
        Cursor cursor = mDb.rawQuery("SELECT DD.varDrName as Name FROM Doctor_Details AS DD ORDER BY DD.varDrName",null);
        if (cursor != null && cursor.getCount() > 0){
            listContacts = new ArrayList<ContactModel>();
            if (cursor.moveToFirst()){
                do {
                    ContactModel contactObj = new ContactModel();
                    contactObj.setContactName(cursor.getString(cursor.getColumnIndex("Name")));
                    listContacts.add(contactObj);
                }while (cursor.moveToNext());
            }
        }
        return listContacts;
    }

    public List<EmailModel> getEmailList(){
        List<EmailModel> listEmail = null;
        Cursor cursor = mDb.rawQuery("SELECT DD.varDrName as Name FROM Doctor_Details AS DD ORDER BY DD.varDrName",null);
        if (cursor != null && cursor.getCount() > 0){
            listEmail = new ArrayList<EmailModel>();
            if (cursor.moveToFirst()){
                do {
                    EmailModel emailObj = new EmailModel();
                    emailObj.setEmailName(cursor.getString(cursor.getColumnIndex("Name")));
                    listEmail.add(emailObj);
                }while (cursor.moveToNext());
            }
        }
        return listEmail;
    }

    public List<EmailModel> getSentEmailList(){
        List<EmailModel> listEmail = null;
        Cursor cursor = mDb.rawQuery("SELECT DD.varDrName as Name FROM Doctor_Details AS DD ORDER BY DD.varDrName limit 2",null);
        if (cursor != null && cursor.getCount() > 0){
            listEmail = new ArrayList<EmailModel>();
            if (cursor.moveToFirst()){
                do {
                    EmailModel emailObj = new EmailModel();
                    emailObj.setEmailName(cursor.getString(cursor.getColumnIndex("Name")));
                    listEmail.add(emailObj);
                }while (cursor.moveToNext());
            }
        }
        return listEmail;
    }
}