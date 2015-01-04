package com.cabmax.adapter;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import com.cabmax.database.DBAdapter;


public class Abhan extends Application {

	private static final String TAG = "Abhan";
	private static final boolean DEBUG = true;
	public static boolean mExternalStorageAvailable = false;
	public static boolean mExternalStorageWriteable = false;
	private DBAdapter dbAdapter = null;

	public static void abhanLog(final int whichType, final String message) {
		if (whichType == 5) {
			Log.e(TAG, message);
		}
		if (DEBUG) {
			if (whichType == 1) {
				Log.v(TAG, message);
			} else if (whichType == 2) {
				Log.d(TAG, message);
			} else if (whichType == 3) {
				Log.i(TAG, message);
			} else if (whichType == 4) {
				Log.w(TAG, message);
			}
		}
	}

	public static void updateExternalStorageState() {

		String state = Environment.getExternalStorageState();

		Log.e("External storage State ", "" + state);

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			Abhan.mExternalStorageAvailable = true;
		} else if (Environment.MEDIA_BAD_REMOVAL.equals(state)) {
			Abhan.mExternalStorageAvailable = false;
		} else if (Environment.MEDIA_CHECKING.equals(state)) {
			Abhan.mExternalStorageAvailable = false;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			Abhan.mExternalStorageAvailable = false;
		} else if (Environment.MEDIA_NOFS.equals(state)) {
			Abhan.mExternalStorageAvailable = false;
		} else if (Environment.MEDIA_REMOVED.equals(state)) {
			Abhan.mExternalStorageAvailable = false;
		} else if (Environment.MEDIA_UNMOUNTABLE.equals(state)) {
			Abhan.mExternalStorageAvailable = false;
		} else if (Environment.MEDIA_UNMOUNTED.equals(state)) {
			Abhan.mExternalStorageAvailable = false;
		}

		Log.e("External storage available ", ""
				+ Abhan.mExternalStorageAvailable);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (dbAdapter == null) {
			dbAdapter = new DBAdapter(getBaseContext());
			dbAdapter.createDatabase();
		}
		updateExternalStorageState();
	}

	public DBAdapter getDatabase() {
		dbAdapter.open();
		return dbAdapter;
	}

	public void closeVCSDatabase() {
		dbAdapter.close();
	}

}
