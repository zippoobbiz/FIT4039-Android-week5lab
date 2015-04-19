package com.assignment.xiaoduo.week5lab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiaoduo on 4/19/15.
 */
public class DbHelper {

    private static SQLiteDatabase sampleDB = null;
    private static String dbName = "FIT4039";
    private static String reminderTable = "reminderTable";
    private static Context context;

    //retrieve reminders from database
    public static List getReminders(Context context)
    {
        List<Reminder> reminderList = new ArrayList<Reminder>();
        try {

            sampleDB = context.openOrCreateDatabase(dbName,
                    context.MODE_PRIVATE, null);

            Cursor c = sampleDB.rawQuery("SELECT * FROM "
                    + reminderTable, null);
            // If Cursor is valid
            if (c != null) {
                // Move cursor to first row
                if (c.moveToFirst()) {
                    do {
                        int reminderId = c.getInt(c
                                .getColumnIndex("reminderID"));
                        String reminderTitle = c.getString(c
                                .getColumnIndex("title"));
                        String reminderDescription = c.getString(c
                                .getColumnIndex("description"));
                        String reminderDueDate = c.getString(c
                                .getColumnIndex("dueDate"));
                        String reminderCompleted = c.getString(c
                                .getColumnIndex("completed"));
                        if (!reminderTitle.equals("null")
                                && !reminderCompleted.equals("null")
                                && !reminderTitle.equals("")
                                && !reminderCompleted.equals("")) {
                            Reminder r = new Reminder();
                            r.setId(reminderId);
                            r.setTitle(reminderTitle);
                            r.setDescription(reminderDescription);
                            r.setDueDate(new Date(reminderDueDate));
                            r.setCompleted(new Boolean(reminderCompleted));
                            reminderList.add(r);

                        }

                    } while (c.moveToNext()); // Move to next row
                } else {
                    dis("c doesn't have first");
                }
            } else {
                dis("curser is null");
            }

        } catch (SQLiteException se) {
            // se.printStackTrace();
            dis("no such database");
        } finally {
            if (sampleDB != null) {
                sampleDB.close();
            }
        }

        return reminderList;
    }


    public static boolean createDatabaseSuccessfully(Context context, Reminder r)
    {
        try {
            sampleDB = context
                    .openOrCreateDatabase(dbName,
                            context.MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS "
                    + reminderTable
                    + " ("
                    + "reminderID INTEGER PRIMARY KEY, title varchar(20),description varchar(50),dueDate varchar(20), completed varchar(5))");
            String insert = "REPLACE INTO " + reminderTable
                    + " Values (null,'" + r.getTitle()
                    + "','" + r.getDescription()
                    + "','" + r.getDueDate()
                    + "','" + r.isCompleted()
                    + "');";
            sampleDB.execSQL(insert);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }

    public static void dropTable()
    {
        sampleDB = context
                .openOrCreateDatabase(dbName,
                        context.MODE_PRIVATE, null);
        sampleDB.execSQL("DROP TABLE IF EXISTS "
                + dbName + "." + reminderTable);
    }

    private static void dis(String info)
    {
        Log.i("DbHelper", info);
    }
}
