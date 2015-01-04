package com.cabmax.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cabmax.OnTheWay.R;
import org.joda.time.DateTime;

import java.util.ArrayList;


public class CalendarAdapter extends BaseAdapter  {

    private Activity activity;
    private LayoutInflater inflater = null;
    private final DateTime jodaTime;
    private ArrayList<Integer> days;
    private int currentMonth, currentYear;

    public CalendarAdapter( Activity activity){
        jodaTime = new DateTime();
        this.activity = activity;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder {
        private TextView txtMonthDay;
    }

    public void setDays(final ArrayList<Integer> days) {
        this.days = days;
    }

    public void setCurrentMonth(final int currentMonth) {
        this.currentMonth = currentMonth;
    }

    public void setCurrentYear(final int currentYear) {
        this.currentYear = currentYear;
    }

    public int getToday() {
        return jodaTime.getDayOfMonth();
    }

    public int getCurrentMonth() {
        return jodaTime.getMonthOfYear();
    }

    public int getCurrentYear() {
        return jodaTime.getYear();
    }

    @Override
    public int getCount() {
        return days.size() > 0 ? days.size() : 0;
    }

    @Override
    public String getItem(int position) {
        return String.valueOf(days.get(position));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.calendar_row, parent, false);
            holder.txtMonthDay = (TextView) convertView.findViewById(R.id.year_row_child_day);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final int printDay = days.get(position);
        if (printDay > 0) {
            if (currentYear == getCurrentYear()
                    && currentMonth == getCurrentMonth()
                    && printDay == getToday()) {
                holder.txtMonthDay.setText(String.valueOf(printDay));
                holder.txtMonthDay.setTextColor(activity.getResources()
                        .getColor(R.color.purple));
            } else {
                holder.txtMonthDay.setText(String.valueOf(printDay));
            }
        }

        return convertView;
    }


}
