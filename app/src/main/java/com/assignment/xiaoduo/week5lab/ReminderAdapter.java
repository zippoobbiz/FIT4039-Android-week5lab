package com.assignment.xiaoduo.week5lab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xiaoduo on 4/19/15.
 */
public class ReminderAdapter extends BaseAdapter {

    List<Reminder> ReminderList;
    Context context;

    public ReminderAdapter(Context context, List<Reminder> list){
        ReminderList = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return ReminderList.size();
    }

    @Override
    public Object getItem(int position) {
        return ReminderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_reminder_details, parent, false);
        }
        Reminder reminder = (Reminder)getItem(position);
        TextView item_title_et = (TextView) convertView.findViewById(R.id.item_title_et);
        TextView item_due_date_et = (TextView) convertView.findViewById(R.id.item_due_date_et);
        item_title_et.setText(reminder.getTitle());
        item_due_date_et.setText(reminder.getDueDate().toString());

        return convertView;
    }
}
