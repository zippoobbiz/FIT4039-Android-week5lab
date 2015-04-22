package com.assignment.xiaoduo.week5lab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;


public class EditReminderActivity extends Activity {

    EditText title_et, description_et;
    DatePicker dueDate_dp;
    CheckBox completed_cb;
    Reminder reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);

        Intent intent = getIntent();
        int reminderId =  intent.getIntExtra("reminderID", 0);

        reminder = DbHelper.selectSingleResult(this, reminderId+"");
        title_et = (EditText)this.findViewById(R.id.title_et);
        description_et = (EditText)this.findViewById(R.id.description_et);
        dueDate_dp = (DatePicker)this.findViewById(R.id.due_date_tp);
        completed_cb = (CheckBox) this.findViewById(R.id.completed_cb);
//        submit_bt = (Button)this.findViewById(R.id.submit_bt);
//        submit_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//
//        });

        title_et.setText(reminder.getTitle());
        description_et.setText(reminder.getDescription());
        Date date = reminder.getDueDate();
        dueDate_dp.init(date.getYear(),date.getMonth(), date.getDate(), null);
        completed_cb.setChecked(reminder.isCompleted());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_reminder, menu);
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
        }else if(id == R.id.action_edit)
        {
            Log.i("EditReminderActivity", "Edit Reminder Activity update Clicked");
            Reminder r = reminder;
            r.setTitle(title_et.getText().toString());
            r.setDescription(description_et.getText().toString());
            r.setDueDate(new Date(dueDate_dp.getYear(),dueDate_dp.getMonth(),dueDate_dp.getDayOfMonth()));
            r.setCompleted(completed_cb.isChecked());

            Intent returnIntent = new Intent();
            returnIntent.putExtra("reminder",r);
            setResult(RESULT_OK, returnIntent);
            if(DbHelper.updateReminderSuccessfully(EditReminderActivity.this, r))
            {
                Log.i("EditReminderActivity", "update success");
                finish();
            }else
            {
                Log.i("EditReminderActivity", "failed try again");
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
