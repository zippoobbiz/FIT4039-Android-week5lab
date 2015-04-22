package com.assignment.xiaoduo.week5lab;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class AddReminderActivity extends Activity {

    EditText title_et, description_et;
    DatePicker dueDate_dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        title_et = (EditText)this.findViewById(R.id.title_et);
        description_et = (EditText)this.findViewById(R.id.description_et);
        dueDate_dp = (DatePicker)this.findViewById(R.id.due_date_tp);
//        submit_bt = (Button)this.findViewById(R.id.submit_bt);
//        reset_bt = (Button)this.findViewById(R.id.reset_bt);
//        submit_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//
//        });
//        reset_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                title_et.setText("");
//                description_et.setText("");
//                Calendar c = Calendar.getInstance();
//                int mYear = c.get(Calendar.YEAR);
//                int mMonth = c.get(Calendar.MONTH);
//                int mDay = c.get(Calendar.DAY_OF_MONTH);
//                dueDate_dp.init(mYear, mMonth, mDay, null);
//                Log.i("AddReminderActivity", "reset");
//            }
//
//        });
        title_et.setText("sample title");
        description_et.setText("sample description");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
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
        }else if(id == R.id.action_save)
        {
            if(title_et.getText().toString().trim().equals(""))
            {
                alert("Please input title.");
                title_et.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(title_et, InputMethodManager.SHOW_IMPLICIT);
            }else if(description_et.getText().toString().trim().equals(""))
            {
                alert("Please input description.");
                description_et.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(description_et, InputMethodManager.SHOW_IMPLICIT);
            }else
            {
                Toast.makeText(AddReminderActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                Reminder r = new Reminder();
                r.setTitle(title_et.getText().toString());
                r.setDescription(description_et.getText().toString());
                r.setDueDate(new Date(dueDate_dp.getYear(), dueDate_dp.getMonth(), dueDate_dp.getDayOfMonth()));
                r.setCompleted(false);
                if(DbHelper.createDatabaseSuccessfully(AddReminderActivity.this,r))
                {
                    finish();
                    Log.i("AddReminderActivity", "insert success");
                }else
                {
                    Log.i("AddReminderActivity", "insert fail, try again");
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }
    public void alert(String text)
    {
        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage(text)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
