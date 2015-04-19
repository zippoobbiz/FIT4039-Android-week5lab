package com.assignment.xiaoduo.week5lab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class AddReminderActivity extends Activity {

    EditText title_et, description_et;
    DatePicker dueDate_dp;
    Button submit_bt, reset_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        title_et = (EditText)this.findViewById(R.id.title_et);
        description_et = (EditText)this.findViewById(R.id.description_et);
        dueDate_dp = (DatePicker)this.findViewById(R.id.due_date_tp);
        submit_bt = (Button)this.findViewById(R.id.submit_bt);
        reset_bt = (Button)this.findViewById(R.id.reset_bt);
        submit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddReminderActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                Reminder r = new Reminder();
                r.setTitle(title_et.getText().toString());
                r.setDescription(description_et.getText().toString());
                r.setDueDate(new Date(dueDate_dp.getYear(), dueDate_dp.getMonth(), dueDate_dp.getDayOfMonth()));
                r.setCompleted(false);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("reminder", r);
                setResult(RESULT_OK, returnIntent);
                finish();

                Log.i("AddReminderActivity", "submit");

            }

        });
        reset_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title_et.setText("");
                description_et.setText("");
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                dueDate_dp.init(mYear, mMonth, mDay, null);
                Log.i("AddReminderActivity", "reset");
            }

        });
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
        }

        return super.onOptionsItemSelected(item);
    }
}
