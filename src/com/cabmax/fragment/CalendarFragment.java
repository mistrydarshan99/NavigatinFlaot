package com.cabmax.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.cabmax.OnTheWay.R;
import com.cabmax.adapter.CalendarAdapter;
import mirko.android.datetimepicker.date.DatePickerDialog;
import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalendarFragment extends Fragment {

    private ProgressDialog progressDialog = null;
    private GridView gridMonth;
    private CalendarAdapter calAdapter;
    private DateTime jodaTime;
    private ImageButton imgbtnCal;
    private TextView txtSelectedMonth;

    private final Calendar mCalendar = Calendar.getInstance();
    private int jodaMonth, jodaDay, jodaYear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_fragment, container, false);
        gridMonth = (GridView)rootView.findViewById(R.id.gridCalendar);
        imgbtnCal = (ImageButton)rootView.findViewById(R.id.imgbtnCal);
        txtSelectedMonth = (TextView)rootView.findViewById(R.id.txtSelectedMonth);

        calculateCurrentDate();

        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {

                txtSelectedMonth.setText(
                        new StringBuilder().append(pad(day))
                                .append(" ").append(pad(month+1)).append(" ").append(pad(year)));
                txtSelectedMonth.setTextColor(getResources().getColor(R.color.popti));

                jodaMonth = month+1;
                jodaDay = day;
                setCalendarInformation();
            }

        }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        imgbtnCal.setOnClickListener(new View.OnClickListener() {
            private String tag;
            @Override
            public void onClick(View v) {
                datePickerDialog.show(getFragmentManager(), tag);;
            }
        });
        setCalendarInformation();
        return rootView;
    }

    private void calculateCurrentDate(){
        // Current Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy");
        String strDate = sdf.format(mCalendar.getTime());
        String[] parts = strDate.split(":");
        jodaDay = Integer.parseInt(parts[0]);
        jodaMonth = Integer.parseInt(parts[1]);
        jodaYear = Integer.parseInt(parts[2]);
    }

    private void setCalendarInformation() {
            new RefreshTask().execute();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    private class RefreshTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jodaTime = new DateTime(jodaYear, jodaMonth, 1, 12, 0, 0, 000);

            calAdapter = new CalendarAdapter(getActivity());
            calAdapter.setDays(calculateDays());
            calAdapter.setCurrentMonth(jodaTime.getMonthOfYear());
            calAdapter.setCurrentYear(jodaTime.getYear());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog != null && progressDialog.isShowing()) {
                progressDialog.cancel();
            }
            gridMonth.setAdapter(calAdapter);
            calAdapter.notifyDataSetChanged();
            gridMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        final int position, long id) {
                    if(!calAdapter.getItem(position).equals("0")){
                        final int clickedDay = Integer.parseInt(calAdapter.getItem(position));

                    }
                }
            });
        }
    }

    private ArrayList<Integer> calculateDays() {
        int dayCount = 1;
        final int firstDayOfMonth = jodaTime.getDayOfWeek();
        final int totalDays = jodaTime.dayOfMonth().getMaximumValue();
        int sum = firstDayOfMonth + totalDays;
        ArrayList<Integer> days = new ArrayList<Integer>(sum);
        for(int i = 1; i < sum; i++) {
            if(firstDayOfMonth <= i) {
                days.add(dayCount);
                dayCount++;
            } else if(firstDayOfMonth <= 7) {
                days.add(0);
            }
        }
        return days;
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}
