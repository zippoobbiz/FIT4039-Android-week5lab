package com.assignment.xiaoduo.week5lab;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ReminderListActivity extends Activity {

    List<Reminder> ReminderList;
    ListView reminderList_lv;
    ReminderAdapter adapter;
    int currentReminderID;
    int currentPosition;

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
                intent.putExtra("reminderID",ReminderList.get(arg2).getId());
                startActivity(intent);
            }

        });

        registerForContextMenu(reminderList_lv);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        if (v.getId()==R.id.reminder_list_lv) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            currentReminderID = ReminderList.get(info.position).getId();
            currentPosition = info.position;
            menu.setHeaderTitle("Operations");
            String[] menuItems = {"Delete","Edit"};
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        if(menuItemIndex == 0)
        {
            if(DbHelper.deleteReminderSuccessfully(ReminderListActivity.this, currentReminderID))
            {
                ReminderList.remove(currentPosition);
                adapter.notifyDataSetChanged();
            }
        }else if(menuItemIndex == 1)
        {
            Intent intent = new Intent(ReminderListActivity.this, EditReminderActivity.class);
            intent.putExtra("reminderID",ReminderList.get(currentPosition).getId());
            startActivity(intent);
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ReminderList.clear();
        ReminderList.addAll(DbHelper.getReminders(this));
        Collections.sort(ReminderList, new Comparator<Reminder>() {
            @Override
            public int compare(Reminder reminder1, Reminder reminder2) {

                return reminder1.getDueDate().compareTo(reminder2.getDueDate());
            }
        });
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
