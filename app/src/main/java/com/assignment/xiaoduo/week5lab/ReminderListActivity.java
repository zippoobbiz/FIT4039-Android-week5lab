package com.assignment.xiaoduo.week5lab;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ReminderListActivity extends Activity {

    List<Reminder> ReminderList;
    ListView reminderList_lv;
    ReminderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);
        reminderList_lv = (ListView) this.findViewById(R.id.reminder_list_lv);
        ReminderList = new ArrayList<Reminder>();
        adapter = new ReminderAdapter(this, ReminderList);
        reminderList_lv.setAdapter(adapter);
        reminderList_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(ReminderListActivity.this, EditReminderActivity.class);
                intent.putExtra("reminder",ReminderList.get(arg2));
                startActivity(intent);
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ReminderList.clear();
        ReminderList.addAll(DbHelper.getReminders(this));
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reminder_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id ==R.id.action_add)
        {
            Intent intent = new Intent(ReminderListActivity.this, AddReminderActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
